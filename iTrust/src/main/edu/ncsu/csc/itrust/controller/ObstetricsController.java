package edu.ncsu.csc.itrust.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

import java.sql.*;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.controller.flags.Flag;
import edu.ncsu.csc.itrust.controller.flags.FlagMySQL;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "obstetrics_controller")
@SessionScoped
public class ObstetricsController extends iTrustController {
	
	private ObstetricsData[] obList;
	private Pregnancies[] pregList;
	private String lmp = "YYYY-MM-DD";
	private ObstetricsDataMySQL odsql;
	private FlagMySQL fMySQL;
	private PregnanciesMySQL psql;
	private transient final PersonnelLoader personnelLoader;
	private SessionUtils sessionUtils;
	DAOFactory factory;
	

	public ObstetricsController() throws DBException {
		super();
		factory = DAOFactory.getProductionInstance();
		personnelLoader = new PersonnelLoader();
		odsql = new ObstetricsDataMySQL();
		psql = new PregnanciesMySQL();
		fMySQL = new FlagMySQL();
		sessionUtils = SessionUtils.getInstance();
	}
	
	public ObstetricsController(DataSource ds, SessionUtils sessionUtils, DAOFactory dao) {
		super();
		factory = dao;
		personnelLoader = new PersonnelLoader();
		odsql = new ObstetricsDataMySQL(ds);
		psql = new PregnanciesMySQL(ds);
		fMySQL = new FlagMySQL(ds);
		this.sessionUtils = sessionUtils;
	}
	
	public void generateOBList(){
		List<ObstetricsData> retList = new ArrayList<ObstetricsData>();
		Long id = sessionUtils.getCurrentPatientMIDLong();
		if (id != null) {
			try {
				retList = odsql.getVisitsForPatient(id);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		obList = new ObstetricsData[retList.size()];
		obList = retList.toArray(obList);
	}
	
	public void generatePregList(){
		List<Pregnancies> retList = null;
		Long id = sessionUtils.getCurrentPatientMIDLong();
		if(id != null) {
			try {
				retList = psql.getByPatientID(id);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		if(retList != null && retList.size() != 0) {
			pregList = new Pregnancies[retList.size()];
			pregList = retList.toArray(pregList);
		}
	}


	
	public ObstetricsData[] getobList(){
		return this.obList;
	}
	
	public void setobList(ObstetricsData[] obList){
		this.obList = obList;
	}
	
	public Pregnancies[] getpregList(){
		return this.pregList;
	}
	
	public void setpregList(Pregnancies[] pregList){
		this.pregList = pregList;
	}
	
	public String getlmp(){
		return this.lmp;
	}
	
	public void setlmp(String lmp){
		this.lmp = lmp;
	}
	
	public boolean isObstetricsPatient() {
		//Here is where we need to determine if the patient is an obstetrics patient.
		//I'm not 100% sure how to do that at the moment.
		return true;
	}
	
	
	public boolean isOBGYN() {
		boolean ret = false;
		
		PersonnelBean bean = null;

		Long id = sessionUtils.getSessionLoggedInMIDLong();
		if (id != null) {
			Connection conn = null;
			try {
				conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM personnel WHERE MID = ?");
				stmt.setLong(1, id);
				final ResultSet results = stmt.executeQuery();
				if (results.next()) {
					bean = personnelLoader.loadSingle(results);
				}
				results.close();
			} catch (SQLException e) {
				//Do nothing
			}
		}
		
		if (bean != null && bean.getSpecialty().equalsIgnoreCase("OB/GYN"))
			ret = true;
		
		return ret;
	}
	
	public void createInitialObstetrics() {
		LocalDateTime date = null;
		Long id = sessionUtils.getCurrentPatientMIDLong();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
		date = LocalDate.parse(lmp, formatter).atStartOfDay();
		if(date != null){
			ObstetricsData newEntry = new ObstetricsData(id, date, LocalDateTime.now());
			try {
				this.odsql.addObstetricsData(newEntry);
				this.logCreateObstetrics();
			} catch (DBException e) {
				//Do Nothing
			}
			checkMaternalAge(newEntry);
		}
	}
	
	
	private void checkMaternalAge(ObstetricsData newEntry) {
		LocalDate birthday;
		LocalDate edd = newEntry.getEdd().toLocalDate();
		try {
			PatientBean p = factory.getPatientDAO().getPatient(sessionUtils.getCurrentPatientMIDLong());
			birthday = new java.sql.Date(p.getDateOfBirth().getTime()).toLocalDate();
			int years = edd.getYear() - birthday.getYear();
			if(years > 34 || (years == 34 && edd.getDayOfYear() > birthday.getDayOfYear())) {
				Flag f = new Flag(1l, sessionUtils.getCurrentPatientMIDLong(), 1l, "Advanced Maternal Age");
				fMySQL.add(f);
			}
		} catch (Exception e) {
			// Do Nothing
		}
	}

	public boolean logViewObstetrics() {
		boolean logged = true;
		Long id = sessionUtils.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.VIEW_INITIAL_OBSTETRICS_RECORD, sessionUtils.getSessionLoggedInMIDLong(), id, "EDD");
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;
	}
	
	public boolean logCreateObstetrics() {
		boolean logged = true;
		Long id = sessionUtils.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, sessionUtils.getSessionLoggedInMIDLong(), id, "EDD");
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;
	}
}

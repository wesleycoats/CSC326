package edu.ncsu.csc.itrust.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.sql.*;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;

@ManagedBean(name = "obstetrics_controller")
@SessionScoped
public class ObstetricsController extends iTrustController {
	
	private ObstetricsData[] obList;
	private Pregnancies[] pregList;
	private String lmp = "YYYY-MM-DD";
	private ObstetricsDataMySQL odsql = new ObstetricsDataMySQL();
	private PregnanciesMySQL psql = new PregnanciesMySQL();
	private transient final PersonnelLoader personnelLoader;
	
	public void generateOBList(){
		List<ObstetricsData> retList = new ArrayList<ObstetricsData>();
		Long id = getSessionUtils().getCurrentPatientMIDLong();
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
		Long id = getSessionUtils().getCurrentPatientMIDLong();
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

	public ObstetricsController() throws DBException {
		super();
		personnelLoader = new PersonnelLoader();
	}
	
	public boolean isObstetricsPatient() {
		//Here is where we need to determine if the patient is an obstetrics patient.
		//I'm not 100% sure how to do that at the moment.
		return true;
	}
	
	
	public boolean isOBGYN() {
		boolean ret = false;
		
		PersonnelBean bean = null;

		Long id = getSessionUtils().getSessionLoggedInMIDLong();
		if (id != null) {
			DAOFactory factory = DAOFactory.getProductionInstance();
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
				System.out.println("oops");
			}
		}
		
		if (bean != null && bean.getSpecialty().equalsIgnoreCase("OB/GYN"))
			ret = true;
		
		return ret;
	}
	
	public void createInitialObstetrics() {
		LocalDateTime date = null;
		System.out.println("clicked");
		Long id = getSessionUtils().getCurrentPatientMIDLong();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
		date = LocalDate.parse(lmp, formatter).atStartOfDay();
		if(date != null){
			ObstetricsData newEntry = new ObstetricsData(id, date, LocalDateTime.now());
			try {
				this.odsql.addObstetricsData(newEntry);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void logViewObstetrics() {
		Long id = getSessionUtils().getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(DAOFactory.getProductionInstance());
			try {
				logAction.logEvent(TransactionType.VIEW_INITIAL_OBSTETRICS_RECORD, getSessionUtils().getSessionLoggedInMIDLong(), id, "EDD");
			} catch (DBException e) {
				//Couldn't log
			}
		}
	}
	
	public void logCreateObstetrics() {
		Long id = getSessionUtils().getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(DAOFactory.getProductionInstance());
			try {
				logAction.logEvent(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, getSessionUtils().getSessionLoggedInMIDLong(), id, "EDD");
			} catch (DBException e) {
				//Couldn't log
			}
		}
	}
}

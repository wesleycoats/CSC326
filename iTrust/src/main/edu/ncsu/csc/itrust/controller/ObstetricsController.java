package edu.ncsu.csc.itrust.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.obstetricsVisit.PregnancyData;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

@ManagedBean(name = "obstetrics_controller")
@SessionScoped
public class ObstetricsController extends iTrustController {
	
	private ObstetricsData[] obList;
	private PregnancyData[] pregList;
	private String lmp = "YYYY-MM-DD";
	private transient final PersonnelLoader personnelLoader;
	
	public void generateOBList(){
		List<ObstetricsData> retList = new ArrayList<ObstetricsData>();
		Long id = getSessionUtils().getCurrentPatientMIDLong();
		if (id != null) {
			DAOFactory factory = DAOFactory.getProductionInstance();
			Connection conn = null;
			try {
				conn = factory.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("SELECT * FROM obstetrics WHERE patientMID = ?");
				ps.setLong(1, id.longValue());
				ResultSet rs = ps.executeQuery();
				while( rs.next() ){
					long pmid = rs.getLong("patientMID");
					String dateCreated = rs.getString("dateCreated");
					String lmp = rs.getString("lmp");
					String edd = rs.getString("edd");
					ObstetricsData od = new ObstetricsData(pmid, dateCreated, lmp, edd);
					retList.add(od);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		obList = new ObstetricsData[retList.size()];
		obList = retList.toArray(obList);
	}
	
	public void generatePregList(){
		List<PregnancyData> retList = new ArrayList<PregnancyData>();
		Long id = getSessionUtils().getCurrentPatientMIDLong();
		if(id != null) {
			DAOFactory factory = DAOFactory.getProductionInstance();
			Connection conn = null;
			try {
				conn = factory.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("SELECT * FROM pregnancies WHERE patientMID = ?");
				ps.setLong(1, id.longValue());
				ResultSet rs = ps.executeQuery();
				while( rs.next() ){
					long pmid = rs.getLong("patientMID");
					int yearOfConception = rs.getInt("yearOfConception");
					int weeksPregnant = rs.getInt("weeksPregnant");
					double hoursInLabor = rs.getDouble("hoursInLabor");
					double weightGain = rs.getDouble("weightGain");
					String deliveryType = rs.getString("deliveryType");
					int numChildren = rs.getInt("numChildren");
					PregnancyData pd = new PregnancyData(pmid, yearOfConception, weeksPregnant, hoursInLabor, weightGain, deliveryType, numChildren);
					retList.add(pd);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		pregList = new PregnancyData[retList.size()];
		pregList = retList.toArray(pregList);
	}


	
	public ObstetricsData[] getobList(){
		return this.obList;
	}
	
	public void setobList(ObstetricsData[] obList){
		this.obList = obList;
	}
	
	public PregnancyData[] getpregList(){
		return this.pregList;
	}
	
	public void setpregList(PregnancyData[] pregList){
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
		System.out.println("clicked");
		Long id = getSessionUtils().getCurrentPatientMIDLong();
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		java.util.Date date = null;
		try {
			date = dtf.parse(lmp);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(date != null){
			LocalDate dateLocal = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate eddLocal = dateLocal.plusDays(280);
			java.util.Date edd = Date.from(eddLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date sqlDate = new Date(date.getTime());
			Date sqlEddDate = new Date(edd.getTime());
			DAOFactory factory = DAOFactory.getProductionInstance();
			Connection conn = null;
			try {
				conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO obstetrics (patientMID, dateCreated, lmp, edd) VALUES (?, ?, ?, ?)");
				stmt.setLong(1, id.longValue());
				stmt.setDate(2, new Date(new java.util.Date().getTime()));
				stmt.setDate(3, sqlDate);
				stmt.setDate(4, sqlEddDate);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logCreateObstetrics();
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

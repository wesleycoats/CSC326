package edu.ncsu.csc.itrust.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.sql.*;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;

@ManagedBean(name = "obstetrics_controller")
@SessionScoped
public class ObstetricsController extends iTrustController {
	
	private ObstetricsData[] obList;
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
		System.out.println(retList.size());
	}
	

	
	public ObstetricsData[] getobList(){
		return this.obList;
	}
	
	public void setobList(ObstetricsData[] obList){
		this.obList = obList;
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
					System.out.println("hello");
					bean = personnelLoader.loadSingle(results);
				}
				results.close();
			} catch (SQLException e) {
				System.out.println("oops");
			}
		}
		
		if (bean != null && bean.getSpecialty().equalsIgnoreCase("OB/GYN"))
			ret = true;
		
		System.out.println(bean != null);
		return ret;
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
}

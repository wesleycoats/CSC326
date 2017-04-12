package edu.ncsu.csc.itrust.controller.obstetricsReport;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "past_pregnancies")
@SessionScoped
public class PastPregnanciesController extends iTrustController {
	
	private Pregnancies[] pregList;
	private PregnanciesMySQL psql;
	private SessionUtils sessionUtils;
	private DAOFactory factory;

	public PastPregnanciesController() throws DBException {
		super();
		factory = DAOFactory.getProductionInstance();
		psql = new PregnanciesMySQL();
		sessionUtils = SessionUtils.getInstance();

	}
	
	public PastPregnanciesController(DataSource ds, SessionUtils sessionUtils, DAOFactory dao) {
		super();
		factory = dao;
		psql = new PregnanciesMySQL(ds);
		this.sessionUtils = sessionUtils;
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
	
	public Pregnancies[] getpregList(){
		return this.pregList;
	}
	
	public void setpregList(Pregnancies[] pregList){
		this.pregList = pregList;
	}

}

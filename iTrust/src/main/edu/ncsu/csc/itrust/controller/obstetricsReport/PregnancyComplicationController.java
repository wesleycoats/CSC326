package edu.ncsu.csc.itrust.controller.obstetricsReport;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.controller.flags.Flag;
import edu.ncsu.csc.itrust.controller.flags.FlagMySQL;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "preg_complications")
@SessionScoped
public class PregnancyComplicationController extends iTrustController {
	
	private Flag[] flagList;
	private FlagMySQL fsql;
	private SessionUtils sessionUtils;
	private DAOFactory factory;

	public PregnancyComplicationController() throws DBException {
		super();
		factory = DAOFactory.getProductionInstance();
		fsql = new FlagMySQL();
		sessionUtils = SessionUtils.getInstance();

	}
	
	public PregnancyComplicationController(DataSource ds, SessionUtils sessionUtils, DAOFactory dao) {
		super();
		factory = dao;
		fsql = new FlagMySQL(ds);
		this.sessionUtils = sessionUtils;
	}
	
	public void generateFlagList(){
		List<Flag> retList = null;
		Long id = sessionUtils.getCurrentPatientMIDLong();
		if(id != null) {
			try {
				retList = fsql.getByPatientID(id);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		if(retList != null && retList.size() != 0) {
			flagList = new Flag[retList.size()];
			flagList = retList.toArray(flagList);
		}
	}
	
	public Flag[] getflagList(){
		return this.flagList;
	}
	
	public void setflagList(Flag[] flagList){
		this.flagList = flagList;
	}

}

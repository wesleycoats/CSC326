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
import edu.ncsu.csc.itrust.model.pregnancyConditions.PregnancyConditionsMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "preg_complications")
@SessionScoped
public class PregnancyComplicationController extends iTrustController {
	
	private Flag[] flagList;
	private FlagMySQL fsql;
	private SessionUtils sessionUtils;
	private PregnancyConditionsMySQL pcsql;

	public PregnancyComplicationController() throws DBException {
		super();
		fsql = new FlagMySQL();
		sessionUtils = SessionUtils.getInstance();
		pcsql = new PregnancyConditionsMySQL();
	}
	
	public PregnancyComplicationController(DataSource ds, SessionUtils sessionUtils, DAOFactory dao) {
		super();
		fsql = new FlagMySQL(ds);
		pcsql = new PregnancyConditionsMySQL(ds);
		this.sessionUtils = sessionUtils;
	}
	
	public void generateFlagList(){
		Long id = sessionUtils.getCurrentPatientMIDLong();
		List<Flag> retList = null;
		if(id != null) {
			try {
				retList = fsql.getByPatientID(id);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		try {
			List<String> pcList = pcsql.getAllByMID(id);
			for(int i = 0; i < pcList.size(); i++) {
				if(pcList.get(i).equalsIgnoreCase("Mild Hyperemesis Gravidarum") || pcList.get(i).equalsIgnoreCase("Hypothiroidism")) {
					Flag f = new Flag(0l, id, 1l, "Pregnancy relevant pre-existing conditions");
					retList.add(f);
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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

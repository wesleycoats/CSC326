package edu.ncsu.csc.itrust.controller.obstetricsReport;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "drug_allergies")
@SessionScoped
public class DrugAllergiesController extends iTrustController {
	
	private List<AllergyBean> list;
	private AllergyDAO allergy;
	private Long pid;

	public DrugAllergiesController() {
		this.allergy = DAOFactory.getProductionInstance().getAllergyDAO();
		this.pid = (long) 0;
	}

	public List<AllergyBean> getList() {
		if (SessionUtils.getInstance().getSessionPID() != null) {
			this.pid = Long.valueOf(SessionUtils.getInstance().getSessionPID());
		}
		try {
			this.list = allergy.getAllergies(pid);
		} catch (DBException e) {
			//Do Nothing
			System.out.println("Error getting allergies.");
		}
		return list;
	}

	public void setList(List<AllergyBean> list) {
		this.list = list;
	}

	
}

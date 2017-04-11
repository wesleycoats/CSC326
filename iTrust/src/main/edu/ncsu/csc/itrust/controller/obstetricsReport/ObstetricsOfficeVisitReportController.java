package edu.ncsu.csc.itrust.controller.obstetricsReport;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "obstetrics_office_visit_controller")
@SessionScoped
public class ObstetricsOfficeVisitReportController {

	List<ObstetricsVisit> list;
	private ObstetricsVisitMySQL obovSQL;
	private SessionUtils utils;
	
	public ObstetricsOfficeVisitReportController() {
		try {
			this.obovSQL = new ObstetricsVisitMySQL();
		} catch (DBException e) {
			//Do nothing
			System.out.println("There was an error creating the SQL object.");
		}
		this.utils = SessionUtils.getInstance();
	}
	
	public List<ObstetricsVisit> getList() {
		String pid = this.utils.getCurrentPatientMID();
		return list;
	}
}

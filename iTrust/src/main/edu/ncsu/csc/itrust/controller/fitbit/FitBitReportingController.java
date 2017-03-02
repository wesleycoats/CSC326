package edu.ncsu.csc.itrust.controller.fitbit;

import java.sql.SQLException;
import java.util.Date;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.fitBit.FitBitBean;
import edu.ncsu.csc.itrust.model.fitBit.FitBitDAO;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "fitbit_controller")
@SessionScoped
public class FitBitReportingController extends iTrustController {

	private FitBitDAO sql;
	private SessionUtils sessionUtils;
	private Date startDate;
	private Date endDate;
	
	public FitBitReportingController() throws DBException {
		sessionUtils = SessionUtils.getInstance();
		DAOFactory dao = DAOFactory.getProductionInstance();
		sql = dao.getFitBitDAO();
	}
	
	public void setSql(FitBitDAO sql){
	    this.sql = sql;
	}
	
	public FitBitBean getFitBitDataByID(String id) throws SQLException, DBException {
		if (id == null) {
			printFacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot get Fit Bit Data", "Invalid User ID", null);
			return null;
		} else {
			return  sql.getWorkoutByID(id);
		}
	}

	   public Date getStartDate() {
	      return startDate;
	   }

	   public void setStartDate(Date date) {
	      this.startDate = date;
	   }
	
	   public Date getEndDate() {
		      return endDate;
	  }

	   public void setEndDate(Date date) {
		     this.endDate = date;
	   }
		   
	public String submit() {
		return "alert('Please have mercy =)');";
	}
}

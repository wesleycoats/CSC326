package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.time.LocalDateTime;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "child_birth_visit_form")
@ViewScoped
public class ChildBirthVisitForm {
	
	private ChildbirthData data;
	private Long patientMID;
	private Long visitID;
	private Long preferredDelivery;
	private Boolean scheduled;
	private OfficeVisitMySQL ovMySQL;
	
	public ChildBirthVisitForm() {
		try {
			ovMySQL = new OfficeVisitMySQL();
		} catch (DBException e) {
			//Do Nothing
		}
	}
	
	public void submit() {
		//This method is called whenever the submit button is clicked.
		//First we will set all of the values, then we will save them to
		//the database using the mySQL files that have not yet been created.
		//After that we will direct the user to the Baby Info page.
		this.setPreferredDelivery(preferredDelivery);
		this.setScheduled(scheduled);
		
		//The following print statements are used to determine if the data is
		//being obtained correctly from the xhtml page.
		System.out.println("submitted. " + this.getScheduled());
		System.out.println(this.getPreferredDelivery());
	}

	public String getDateString() {
		OfficeVisit ov = null;
		try {
			if(ovMySQL != null) {
				ov = ovMySQL.getByID(visitID);
			}
		} catch (Exception e) {
			//Do Nothing
		}
		
		if (ov != null) {
			return ov.getDate().toLocalDate().toString();
		}
		return "Date not Found.";	
	}
	
	public Long getPatientMID() {
		return patientMID;
	}


	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}


	public Long getVisitID() {
		return visitID;
	}


	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}


	public Long getPreferredDelivery() {
		return preferredDelivery;
	}


	public void setPreferredDelivery(Long preferredDelivery2) {
		this.preferredDelivery = preferredDelivery2;
	}


	public Boolean getScheduled() {
		return scheduled;
	}


	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}


	public void getVisitDate() {
		this.patientMID = Long.parseLong(SessionUtils.getInstance().getSessionPID());
		this.visitID = SessionUtils.getInstance().getCurrentOfficeVisitId();
		
		//After this we will need to get the information from a previously existing child birth
		//office visit. For now I will assume that one does not exist.
		//Below is the code used in ObstetricsOfficeVisitForm.java to get the necessary information.
		//Use this code as a reference.

/*		try {
			List<ObstetricsVisit> list = mySQL.getVisitsForPatient(pid);
			if (list == null || list.size() == 0) {
				this.obstetricsOV.setVisitID(sid);
			}
			else {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getVisitID() == sid) {
						this.obstetricsOV.setVisitID(list.get(i).getVisitID());
						this.obstetricsOV = list.get(i);
						break;
					}
				}
				if (this.obstetricsOV.getVisitID() == (long) -1) {
					this.obstetricsOV.setVisitID(sid);
				}
			}
		} catch (DBException e) {
			//Do Nothing
		}
		
		
		this.date = this.mySQL.getDateOfVisit(SessionUtils.getInstance().getCurrentOfficeVisitId());
		this.lastMenstrualPeriod = sql.getLmpDate(pid);
		this.patientMID = pid;
		
		if (this.obstetricsOV != null) {
			this.setWeight(this.obstetricsOV.getWeight());
			setBloodPressure(this.obstetricsOV.getBloodPressure());
			this.fetalHR = this.obstetricsOV.getFetalHeartRate();
			this.pregnancies = this.obstetricsOV.getPregnancies();
		}
		 
		if (this.date == null) {
			this.date = LocalDateTime.now();
		}
		if (this.lastMenstrualPeriod == null) {
			this.lastMenstrualPeriod = LocalDateTime.now();
		} */
		
//		System.out.println(this.date.toLocalDate().toString() + "     " + this.lastMenstrualPeriod.toLocalDate().toString());
	} 
}

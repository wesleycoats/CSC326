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
	
	private String ovID;
	private Long patientMID;
	private Long visitID;
	private String preferredDelivery;
	private Boolean scheduled;
	private OfficeVisitMySQL ovMySQL;
	private ChildbirthVisitMySQL cbMySQL;
	private ChildbirthVisit cbv;
	private Integer epiduralAnaesthesiaDosage;
	private Integer magnesiumSulfateDosage;
	private Integer nitrousOxideDosage;
	private Integer pethidineDosage;
	private Integer pitocinDosage;
	
	public ChildBirthVisitForm() {
		this.ovID = SessionUtils.getInstance().getCurrentOfficeVisitId().toString();
		this.cbv = new ChildbirthVisit();
		this.getVisitDate();
		try {
			this.cbMySQL = new ChildbirthVisitMySQL();
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
		this.cbv.setPatientMID(patientMID);
		this.cbv.setPreferredDelivery(preferredDelivery);
		this.cbv.setScheduled(scheduled);
		this.cbv.setVisitID(visitID);
		this.cbv.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
		this.cbv.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
		this.cbv.setNitrousOxideDosage(nitrousOxideDosage);
		this.cbv.setPethidineDosage(pethidineDosage);
		this.cbv.setPitocinDosage(pitocinDosage);

		try {
			this.cbMySQL.addChildbirthVisit(cbv);
		} catch (Exception e) {
			//Do Nothing
			System.out.println("Failed to add Child Birth Visit.");
		}
	}

	public Integer getEpiduralAnaesthesiaDosage() {
		return epiduralAnaesthesiaDosage;
	}

	public void setEpiduralAnaesthesiaDosage(Integer epiduralAnaesthesiaDosage) {
		this.epiduralAnaesthesiaDosage = epiduralAnaesthesiaDosage;
	}

	public Integer getMagnesiumSulfateDosage() {
		return magnesiumSulfateDosage;
	}

	public void setMagnesiumSulfateDosage(Integer magnesiumSulfateDosage) {
		this.magnesiumSulfateDosage = magnesiumSulfateDosage;
	}

	public Integer getNitrousOxideDosage() {
		return nitrousOxideDosage;
	}

	public void setNitrousOxideDosage(Integer nitrousOxideDosage) {
		this.nitrousOxideDosage = nitrousOxideDosage;
	}

	public Integer getPethidineDosage() {
		return pethidineDosage;
	}

	public void setPethidineDosage(Integer pethidineDosage) {
		this.pethidineDosage = pethidineDosage;
	}

	public Integer getPitocinDosage() {
		return pitocinDosage;
	}

	public void setPitocinDosage(Integer pitocinDosage) {
		this.pitocinDosage = pitocinDosage;
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
	
	public String getOvID() {
		return ovID;
	}

	public void setOvID(String ovID) {
		this.ovID = ovID;
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


	public String getPreferredDelivery() {
		return preferredDelivery;
	}


	public void setPreferredDelivery(String preferredDelivery) {
		this.preferredDelivery = preferredDelivery;
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

		try {
			List<ChildbirthVisit> list = this.cbMySQL.getVisitsForPatient(patientMID);
			if (list == null || list.size() == 0) {
				this.cbv.setVisitID(visitID);
			}
			else {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getVisitID() == visitID) {
						this.cbv = list.get(i);
						break;
					}
				}
				if (this.cbv.getVisitID() == (long) -1) {
					this.cbv.setVisitID(visitID);
				}
			}
		} catch (Exception e) {
			//Do nothing
		}
		
		if (this.cbv != null) {
			this.epiduralAnaesthesiaDosage = cbv.getEpiduralAnaesthesiaDosage();
			this.magnesiumSulfateDosage = cbv.getMagnesiumSulfateDosage();
			this.nitrousOxideDosage = cbv.getNitrousOxideDosage();
			this.pethidineDosage = cbv.getPethidineDosage();
			this.pitocinDosage = cbv.getPitocinDosage();
			this.preferredDelivery = cbv.getPreferredDelivery();
			this.scheduled = cbv.getScheduled();
		}
	} 
}

package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.util.List;

public class ChildbirthData {
	
	private Long patientMID;
	private Long visitID;
	private String preferredDelivery;
	private Boolean scheduled;
	private List<DrugRecord> administeredDrugs;
	private List<ChildRecord> childrenBorn;
	
	public ChildbirthData(Long patientMID, Long visitID, String preferredDelivery, Boolean scheduled, List<DrugRecord> drugsAdministered, List<ChildRecord> childrenBorn, List<DrugRecord> administeredDrugs){
		
		this.setPatientMID(patientMID);
		this.setVisitID(visitID);
		this.setPreferredDelivery(preferredDelivery);
		this.setScheduled(scheduled);
		this.setAdministeredDrugs(administeredDrugs);
		this.setChildrenBorn(childrenBorn);
		
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

	public List<ChildRecord> getChildrenBorn() {
		return childrenBorn;
	}

	public void setChildrenBorn(List<ChildRecord> childrenBorn) {
		this.childrenBorn = childrenBorn;
	}

	public List<DrugRecord> getAdministeredDrugs() {
		return administeredDrugs;
	}

	public void setAdministeredDrugs(List<DrugRecord> administeredDrugs) {
		this.administeredDrugs = administeredDrugs;
	}

}

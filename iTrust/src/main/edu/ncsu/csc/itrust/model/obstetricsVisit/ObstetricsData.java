package edu.ncsu.csc.itrust.model.obstetricsVisit;

public class ObstetricsData {
	
	private long patientMID;
	private String dateCreated;
	private String lmp;
	private String edd;
	
	public ObstetricsData(long patientMID, String dateCreated, String lmp, String edd){
		this.setPatientMID(patientMID);
		this.setDateCreated(dateCreated);
		this.setLmp(lmp);
		this.setEdd(edd);
	}

	public long getPatientMID() {
		return patientMID;
	}

	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLmp() {
		return lmp;
	}

	public void setLmp(String lmp) {
		this.lmp = lmp;
	}

	public String getEdd() {
		return edd;
	}

	public void setEdd(String edd) {
		this.edd = edd;
	}

}

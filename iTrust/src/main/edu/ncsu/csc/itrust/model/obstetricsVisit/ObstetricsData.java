package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.time.LocalDateTime;

public class ObstetricsData {
	private Long patientMID;
	/** last menstrual period */
	private LocalDateTime lmp;
	/** estimated due date */
	private LocalDateTime edd;
	private LocalDateTime dateCreated;

	/**
	 * Default constructor for OfficeVisit
	 */
	public ObstetricsData() {
	}

	/**
	 * constructor for OfficeVisit for already existing values
	 */
	public ObstetricsData(long patientMID, LocalDateTime lastPeriod, LocalDateTime dateCreated) {
		this.setDateCreated(dateCreated);
		this.setLmp(lastPeriod);
		this.setPatientMID(patientMID);
	}

	/**
	 * @return the patientMID
	 */
	public Long getPatientMID() {
		return patientMID;
	}

	/**
	 * @param patientID
	 *            the patient MID to set
	 */
	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}

	public LocalDateTime getLmp() {
		return lmp;
	}

	public void setLmp(LocalDateTime date) {
		this.lmp = date;
		this.setEdd(date);
	}

	public LocalDateTime getEdd() {
		return edd;
	}
	
	public void setEdd(LocalDateTime lmpDate) {
		this.edd = lmpDate.plusDays(280l);
	}
	
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
}

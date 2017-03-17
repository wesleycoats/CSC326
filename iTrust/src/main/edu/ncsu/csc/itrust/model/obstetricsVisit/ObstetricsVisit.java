/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;

/**
 * @author rvisador
 *
 */
@ManagedBean(name="obstetrics_visit")
public class ObstetricsVisit {
	private Long visitID;
	private Long patientMID;
	/** last menstrual period */
	private LocalDateTime lmp;
	/** estimated due date */
	private LocalDateTime edd;
	private int weeksPregnant;
		
	/**
	 * Default constructor for OfficeVisit
	 */
	public ObstetricsVisit(){
	}
	
	
	/**
	 * @return the patientMID
	 */
	public Long getPatientMID() {
		return patientMID;
	}

	/**
	 * @param patientID the patient MID to set
	 */
	public void setPatientMID(Long patientID) {
		this.patientMID = patientID;
	}

	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}

	public LocalDateTime getLMP() {
		return lmp;
	}

	public void setLMP(LocalDateTime lmp) {
		this.lmp = lmp;
	}

	public LocalDateTime getEDD() {
		return edd;
	}

	public void setEDD(LocalDateTime edd) {
		this.edd = edd;
	}
	
	public int getWeeksPegnant() {
		return weeksPregnant;
	}

	public void setWeeksPregnant(int weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}
}

/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;

import edu.ncsu.csc.itrust.exception.DBException;

/**
 * @author rvisador
 *
 */
@ManagedBean(name = "obstetrics_visit")
public class ObstetricsVisit {
	private Long visitID;
	private Long patientMID;
	/** last menstrual period */
	private Integer weeksPregnant;
	private Float weight;
	private Integer systolicBloodPressure;
	private Integer diastolicBloodPressure;
	private Integer fetalHeartRate;
	private Integer pregnancies;
	private boolean placentaObserved;
	private LocalDateTime date;

	/**
	 * Default constructor for OfficeVisit
	 */
	public ObstetricsVisit() {
	}

	/**
	 * constructor for OfficeVisit for already existing values
	 */
	public ObstetricsVisit(long visitID, long patientMID, float weight, 
			int systolicBloodPressure, int diastolicBloodPressure, int fetalHeartRate, int pregnancies, boolean placentaObserved) {
		this.setVisitID(visitID);
		this.setPatientMID(patientMID);
		this.setWeight(weight);
		this.setSystolicBloodPressure(systolicBloodPressure);
		this.setDiastolicBloodPressure(diastolicBloodPressure);
		this.setFetalHeartRate(fetalHeartRate);
		this.setPregnancies(pregnancies);
		this.setPlacentaObserved(placentaObserved);
		this.setWeeksPregnant();
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
	public void setPatientMID(Long patientID) {
		this.patientMID = patientID;
	}

	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}

	public Integer getWeeksPegnant() {
		return weeksPregnant;
	}

	public void setWeeksPregnant() {
		LocalDateTime ovDate = this.getOfficeVisitDate();
		LocalDateTime lmpDate = this.getLmp();
		
		int yearsDiff = ovDate.getYear() - lmpDate.getYear();
		int daysLMP = lmpDate.getDayOfYear();
		int daysNow = ovDate.getDayOfYear();
		this.weeksPregnant = (yearsDiff * 366 + (daysNow - daysLMP)) / 7;
	}
	
	public void setWeeksPregnant(int weeks) {
		this.weeksPregnant = weeks;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Integer getSystolicBloodPressure() {
		return this.systolicBloodPressure;
	}

	public void setSystolicBloodPressure(int bloodPressure) {
		this.systolicBloodPressure = bloodPressure;
	}

	public Integer getDiastolicBloodPressure() {
		return this.diastolicBloodPressure;
	}

	public void setDiastolicBloodPressure(int bloodPressure) {
		this.diastolicBloodPressure = bloodPressure;
	}

	public Integer getFetalHeartRate() {
		return this.fetalHeartRate;
	}

	public void setFetalHeartRate(int fetalHeartRate) {
		this.fetalHeartRate = fetalHeartRate;
	}
	
	public Integer getPregnancies() {
		return this.pregnancies;
	}

	public void setPregnancies(int pregnancies) {
		this.pregnancies = pregnancies;
	}
	
	public Boolean getPlacentaObserved() {
		return this.placentaObserved;
	}

	public void setPlacentaObserved(boolean placentaObserved) {
		this.placentaObserved = placentaObserved;
	}
	
	public LocalDateTime getDate() {
		System.out.println(date);
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	private LocalDateTime getLmp() {
		LocalDateTime date = null;
		try {
			ObstetricsDataMySQL odsql = new ObstetricsDataMySQL();
			date = odsql.getLmpDate(this.patientMID);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private LocalDateTime getOfficeVisitDate() {
		LocalDateTime officeVisit = null;
		try {
			ObstetricsVisitMySQL ovsql = new ObstetricsVisitMySQL();
			officeVisit = ovsql.getDateOfVisit(visitID);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return officeVisit;
	}

}

package edu.ncsu.csc.itrust.controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;

@ManagedBean(name = "obstetrics_office_visit_form")
@ViewScoped
public class ObstetricsOfficeVisitForm {
	
	private long visitID;
	private long patientMID;
	private long weeksPregnant;
	private LocalDateTime lastMenstrualPeriod;
	private LocalDateTime expectedDeliveryDate;
	private LocalDateTime date;
	private Long apptTypeID;
	private Float weight;
	private String bloodPressure;
	private long fetalHR;
	private String multiple;
	private ObstetricsVisit obstetricsOV;
	
	public ObstetricsOfficeVisitForm() {
		this.obstetricsOV = new ObstetricsVisit();
		this.expectedDeliveryDate = LocalDateTime.now();
	}
	
	public void getVisitDate() {
		//We need to get the date of the visit from the database
		//The line below is a place holder and sets a specific date.
		this.date = LocalDateTime.parse("2017-03-03T10:15:30");
	}

	public long getVisitID() {
		return visitID;
	}

	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}

	public long getPatientMID() {
		return patientMID;
	}

	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}

	public long getWeeksPregnant() {
		return weeksPregnant;
	}

	public void setWeeksPregnant(long weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}

	public String getLastMenstrualPeriod() {
		if (lastMenstrualPeriod != null) {
			return lastMenstrualPeriod.toLocalDate().toString();
		}
		else {
			return "";
		}
	}

	public void setLastMenstrualPeriod(LocalDateTime lastMenstrualPeriod) {
		this.lastMenstrualPeriod = lastMenstrualPeriod;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate.toLocalDate().toString();
		
	}

	public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public LocalDate getDateString() {
		return date.toLocalDate();
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getApptTypeID() {
		return apptTypeID;
	}

	public void setApptTypeID(Long apptTypeID) {
		this.apptTypeID = apptTypeID;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public long getFetalHR() {
		return fetalHR;
	}

	public void setFetalHR(long fetalHR) {
		this.fetalHR = fetalHR;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	
	
}

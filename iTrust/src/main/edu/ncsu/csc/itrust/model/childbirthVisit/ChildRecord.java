package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.time.LocalDateTime;

public class ChildRecord {
	private Long motherMID;
	private Long visitID;
	private Boolean sex;
	private String deliveryType;
	private LocalDateTime dateOfBirth;
	
	public ChildRecord(Boolean sex, String deliveryType, LocalDateTime dateOfBirth,
			long motherMID, long visitID){
		this.sex = sex;
		this.deliveryType = deliveryType;
		this.dateOfBirth = dateOfBirth;
		this.motherMID = motherMID;
		this.visitID = visitID;
	}
	
	public Long getMotherMID() {
		return motherMID;
	}
	
	public void setMotherMID(long motherMID) {
		this.motherMID = motherMID;
	}
	
	public Long getVisitID() {
		return this.visitID;
	}
	
	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}
	
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}

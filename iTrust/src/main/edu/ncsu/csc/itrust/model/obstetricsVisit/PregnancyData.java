package edu.ncsu.csc.itrust.model.obstetricsVisit;

public class PregnancyData {
	
	private long patientMID;
	private int yearOfConception;
	private int weeksPregnant;
	private double hoursInLabor;
	private double weightGain;
	private String deliveryType;
	private int numChildren;
	
	public PregnancyData(long patientMID, int yearOfConception, int weeksPregnant, double hoursInLabor,
			double weightGain, String deliveryType, int numChildren){
		this.setPatientMID(patientMID);
		this.setYearOfConception(yearOfConception);
		this.setWeeksPregnant(weeksPregnant);
		this.setHoursInLabor(hoursInLabor);
		this.setWeightGain(weightGain);
		this.setDeliveryType(deliveryType);
		this.setNumChildren(numChildren);
	}

	public long getPatientMID() {
		return patientMID;
	}

	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}

	public int getYearOfConception() {
		return yearOfConception;
	}

	public void setYearOfConception(int yearOfConception) {
		this.yearOfConception = yearOfConception;
	}

	public int getWeeksPregnant() {
		return weeksPregnant;
	}

	public void setWeeksPregnant(int weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}

	public double getHoursInLabor() {
		return hoursInLabor;
	}

	public void setHoursInLabor(double hoursInLabor) {
		this.hoursInLabor = hoursInLabor;
	}

	public double getWeightGain() {
		return weightGain;
	}

	public void setWeightGain(double weightGain) {
		this.weightGain = weightGain;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

}

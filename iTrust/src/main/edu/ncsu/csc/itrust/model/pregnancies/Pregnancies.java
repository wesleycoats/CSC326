package edu.ncsu.csc.itrust.model.pregnancies;

public class Pregnancies {
	private long MID;
	private String delType = "";
	private int yearOfConception;
	private double hoursInLabor;
	private double weightGain;
	private short numChildren;
	private long pregID;
	
	public Pregnancies() {
		clearFields();
	}
	
	public Pregnancies(long MID, String type, int year, double labor, double weight, short children) {
		this.MID = MID;
		this.delType = type;
		this.yearOfConception = year;
		this.hoursInLabor = labor;
		this.weightGain = weight;
		this.numChildren = children;
		setID();
	}

	private void clearFields() {
		MID = -1;
		delType = "";
		yearOfConception = -1;
		hoursInLabor = -1;
		weightGain = -1;
		numChildren = -1;
		setID();
	}

	/*
	 * Sets a unique pregnancy ID by hashing the patient MID with the number of children,
	 * hours in labor, and year of conception
	 */
	private void setID() {
		// Prime number used for hashing
		int hash = 31;
		pregID = MID + (numChildren * hash) + (yearOfConception + hash) + Math.round(hoursInLabor * hash * hash);
	}

	public long getMID() {
		return MID;
	}

	public void setMID(long mID) {
		MID = mID;
	}

	public String getDelType() {
		return delType;
	}

	public void setDelType(String delType) {
		this.delType = delType;
	}

	public int getYearOfConception() {
		return yearOfConception;
	}

	public void setYearOfConception(int yearOfConception) {
		this.yearOfConception = yearOfConception;
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

	public short getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(short numChildren) {
		this.numChildren = numChildren;
	}

}

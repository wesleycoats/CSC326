package edu.ncsu.csc.itrust.model.pregnancies;

import java.time.LocalDateTime;

/**
 * Represents a prior Pregnancy. Holds all of the information that can be seen by iTrust
 * users.
 * @author bmhogan
 */
public class Pregnancies {
	private long MID;
	private String delType = "";
	private int yearOfConception;
	private double hoursInLabor;
	private double weightGain;
	private int weeksPregnant;
	private short numChildren;
	private long pregID = -1;
	private LocalDateTime edd;
	
	/**
	 * Creates a new Pregnancy object with fields inititalized to -1, and delType initialized to null 
	 */
	public Pregnancies() {
		clearFields();
	}
	
	/**
	 * Creates a new Pregnancy object with the values passed in, then computes the pregID and stores it
	 */
	public Pregnancies(long MID, String type, int year, double labor, double weight, int weeksPregnant, short children) {
		this.MID = MID;
		this.delType = type;
		this.yearOfConception = year;
		this.hoursInLabor = labor;
		this.weightGain = weight;
		this.weeksPregnant = weeksPregnant;
		this.numChildren = children;
		setID();
	}

	public int getWeeksPregnant() {
		return weeksPregnant;
	}

	public void setWeeksPregnant(int weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}
	public LocalDateTime getEdd() {
		return edd;
	}
	
	public void setEdd(LocalDateTime edd) {
		this.edd = edd;
	}
	
	private void clearFields() {
		MID = -1;
		delType = null;
		yearOfConception = -1;
		hoursInLabor = -1;
		weightGain = -1;
		numChildren = -1;
		pregID = -1;
	}

	/*
	 * Sets a unique pregnancy ID by hashing the patient MID with the number of children,
	 * hours in labor, and year of conception.
	 */
	private void setID() {
		// Prime number used for hashing
		int hash = 31;
		pregID = MID + (numChildren * hash) + (yearOfConception + hash) + Math.round(hoursInLabor * hash * hash);
	}

	public long getPatientMID() {
		return MID;
	}

	public void setPatientMID(long mID) {
		MID = mID;
		setID();
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
	
	public long getPregID() {
		return pregID;
	}

}

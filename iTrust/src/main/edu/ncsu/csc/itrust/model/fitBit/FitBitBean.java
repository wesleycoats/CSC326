package edu.ncsu.csc.itrust.model.fitBit;

import java.io.Serializable;
import java.sql.Date;


public class FitBitBean implements Serializable {

	private static final long serialVersionUID = 3153556127498031641L;
	private String id;
	private Long patient;
	private Integer cal_burned = 0;
	private Integer steps = 0;
	private Integer floors = 0;
	private Double distance = 0.0;
	private Integer min_seden = 0;
	private Integer min_light_active = 0;
	private Integer min_fair_active = 0;
	private Integer min_very_active = 0;
	private Integer activity_cal = 0;
	private Date date;
	
	/**
	 * @return the patient
	 */
	public long getPatient() {
		return patient;
	}
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(long patient) {
		this.patient = patient;
	}
	
	/**
	 * @return the calories burned
	 */
	public int getCalBurned() {
		return cal_burned;
	}

	/**
	 * @param cal_burned the price to set
	 */
	public void setCalBurned(int cal_burned) {
		this.cal_burned = cal_burned;
	}

	/**
	 * @return the the number of steps taken
	 */
	public int getSteps() {
		return steps;
	}
	
	/**
	 * @param steps the apptID to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}
	/**
	 * 
	 * @return the number of floors taken
	 */
	public int getFloors() {
		return floors;
	}
	
	/**
	 * @param floors the number of floors taken
	 */
	public void setFloors(int floors) {
		this.floors = floors;
	}
	
	/**
	 * @return the hcp
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param hcp the hcp to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	/**
	 * @return the number of minutes the patient was sedentary
	 */
	public int getMinSeden() {
		return min_seden;
	}

	/**
	 * @param min_light_active the number of minutes the patient was sedentary
	 */
	public void setMinSeden(int min_seden) {
		this.min_seden = min_seden;
	}
	
	/**
	 * @return the number of minutes the patient was lightly active
	 */
	public int getMinLightActive() {
		return min_light_active;
	}

	/**
	 * @param min_light_active the number of minutes the patient was lightly active
	 */
	public void setMinLightActive(int min_light_active) {
		this.min_light_active = min_light_active;
	}

	/**
	 * @return the number of minutes the patient was fairly active
	 */
	public int getMinFairActive() {
		return min_fair_active;
	}

	/**
	 * @param min_fair_active the number of minutes the patient was fairly active
	 */
	public void setMinFairActive(int min_fair_active) {
		this.min_fair_active = min_fair_active;
	}
	
	/**
	 * @return the number of minutes the patient was very active
	 */
	public int getMinVeryActive() {
		return min_very_active;
	}

	/**
	 * @param min_light_active the number of minutes the patient was very active
	 */
	public void setMinVeryActive(int min_very_active) {
		this.min_very_active = min_very_active;
	}
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return the amount of calories burned during activity
	 */
	public int getActivityCal() {
		return activity_cal;
	}

	/**
	 * @param activity_cal the amount of calories burned during activity
	 */
	public void setActivityCal(int activity_cal) {
		this.activity_cal = activity_cal;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return the current id of the workout
	 */
	public String getID() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return 0; // any arbitrary constant will do
	}
	
	/**
	 * Returns true if both id's are equal. Probably needs more advance field by field checking.
	 */
	@Override public boolean equals(Object other) {
	   
	    if ( this == other ){
	    	return true;
	    }

	    if ( !(other instanceof FitBitBean) ){
	    	return false;
	    }
	    
	    FitBitBean otherAppt = (FitBitBean)other;
		return otherAppt.getID() == getID();
	    
	}
}

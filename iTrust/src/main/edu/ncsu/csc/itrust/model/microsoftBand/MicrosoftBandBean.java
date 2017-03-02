package edu.ncsu.csc.itrust.model.microsoftBand;

import java.io.Serializable;
import java.sql.Date;


public class MicrosoftBandBean implements Serializable {
	private static final long serialVersionUID = 875572869747903342L;
	private String id;
	private Long patient;
	private Integer calories = 0;
	private Integer steps = 0;
	private Integer floors = 0;
	private Float distance = 0f;
	private Integer HR_lowest = 0;
	private Integer HR_average = 0;
	private Integer HR_highest = 0;
	private Integer min_UV_exposure = 0;
	private Integer activity_hours = 0;
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
	public int getCalories() {
		return calories;
	}

	/**
	 * @param cal_burned the price to set
	 */
	public void setCalories(int calories) {
		this.calories = calories;
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
	public float getDistance() {
		return distance;
	}
	/**
	 * @param hcp the hcp to set
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	/**
	 * @return the number of minutes the patient was sedentary
	 */
	public int getHRLowest() {
		return HR_lowest;
	}

	/**
	 * @param min_light_active the number of minutes the patient was sedentary
	 */
	public void setHRLowest(int HR_lowest) {
		this.HR_lowest = HR_lowest;
	}
	
	/**
	 * @return the number of minutes the patient was lightly active
	 */
	public int getHRAverage() {
		return HR_average;
	}

	/**
	 * @param min_light_active the number of minutes the patient was lightly active
	 */
	public void setHRAverage(int HR_average) {
		this.HR_average = HR_average;
	}

	/**
	 * @return the number of minutes the patient was fairly active
	 */
	public int getHRHighest() {
		return HR_highest;
	}

	/**
	 * @param min_fair_active the number of minutes the patient was fairly active
	 */
	public void setHRHighest(int HR_highest) {
		this.HR_highest = HR_highest;
	}
	
	/**
	 * @return the number of minutes the patient was very active
	 */
	public int getMinUVExposure() {
		return min_UV_exposure;
	}

	/**
	 * @param min_light_active the number of minutes the patient was very active
	 */
	public void setMinUVExposure(int min_UV_exposure) {
		this.min_UV_exposure = min_UV_exposure;
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
	public int getActivityHours() {
		return activity_hours;
	}

	/**
	 * @param activity_cal the amount of calories burned during activity
	 */
	public void setActivityHours(int activity_hours) {
		this.activity_hours = activity_hours;
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

	    if ( !(other instanceof MicrosoftBandBean) ){
	    	return false;
	    }
	    
	    MicrosoftBandBean otherAppt = (MicrosoftBandBean)other;
		return otherAppt.getID() == getID();
	    
	}
}

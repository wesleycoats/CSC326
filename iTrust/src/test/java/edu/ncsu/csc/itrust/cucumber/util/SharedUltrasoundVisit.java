package edu.ncsu.csc.itrust.cucumber.util;

import edu.ncsu.csc.itrust.model.ultrasound.Ultrasound;
import edu.ncsu.csc.itrust.model.ultrasound.UltrasoundMySQL;
import edu.ncsu.csc.itrust.model.ultrasound.UltrasoundValidator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class SharedUltrasoundVisit {
	private Ultrasound us;
	private UltrasoundMySQL usMySQL;
	private UltrasoundValidator usValidator;
	private boolean wasAddSuccessful;
	private boolean hasError;
	private String errorMessage = null;
	
	public SharedUltrasoundVisit() throws Exception {
		hasError = false;
		wasAddSuccessful = false;
		usMySQL = new UltrasoundMySQL(((TestDAOFactory)TestDAOFactory.getTestInstance()).getDataSource());
		usValidator = new UltrasoundValidator();
	}

	public Ultrasound getUs() {
		return us;
	}

	public void setUs(Ultrasound us) {
		this.us = us;
	}

	public UltrasoundMySQL getUsMySQL() {
		return usMySQL;
	}

	public void setUsMySQL(UltrasoundMySQL usMySQL) {
		this.usMySQL = usMySQL;
	}

	public boolean isWasAddSuccessful() {
		return wasAddSuccessful;
	}

	public void setWasAddSuccessful(boolean wasAddSuccessful) {
		this.wasAddSuccessful = wasAddSuccessful;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public Boolean getWasAddSuccessful() {
		return wasAddSuccessful;
	}
	
	public void addUltrasound() {
		try {
			errorMessage = null;
			usValidator.validate(us);
		} catch (Exception e) {
			hasError = true;
			wasAddSuccessful = false;
			errorMessage = e.getMessage();
		}
	}
	
	public void addUltrasoundToDatabase() {
		try {
			errorMessage = null;
			usMySQL.addUltrasound(us);
			wasAddSuccessful = true;
		} catch (Exception e) {
			hasError = true;
			wasAddSuccessful = false;
			errorMessage = e.getMessage();
		}
	}

}

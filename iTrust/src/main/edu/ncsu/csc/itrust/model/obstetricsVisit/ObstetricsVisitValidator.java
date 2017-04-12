package edu.ncsu.csc.itrust.model.obstetricsVisit;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class ObstetricsVisitValidator extends POJOValidator<ObstetricsVisit> {

	/**
	 * Default constructor for ObstetricsVisitValidator. 
	 */
	public ObstetricsVisitValidator() {
	}

	/**
	 * Used to Validate an obstetrics visit object. If the validation does not
	 * succeed, a {@link FormValidationException} is thrown. only performs
	 * checks on the values stored in the object (e.g. Patient MID) Does NOT
	 * validate the format of the visit date and other attributes that are NOT
	 * stored in the object itself
	 * 
	 * @param obj
	 *            the obstetrics visit to be validated
	 */
	@Override
	public void validate(ObstetricsVisit obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		Long patientMID = obj.getPatientMID();
		
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add obstetrics visit information: invalid patient MID");
			throw new FormValidationException(errorList);
		}
		
		errorList.addIfNotNull(checkFormat("Patient MID", patientMID, ValidationFormat.NPMID, false));
		
		if (obj.getVisitID() != null) {
			if (obj.getVisitID() <= 0) {
				errorList.addIfNotNull("Visit ID: Negative Visit ID");
			}
		} else {
			errorList.addIfNotNull("Visit ID: Null Visit ID");
		}

		if (obj.getWeeksPegnant() < 0) {
			errorList.addIfNotNull("Weeks Pregnant: Invalid Weeks Pregnant");
		} else if (obj.getWeeksPegnant() > 52) {
			errorList.addIfNotNull("Weeks Pregnant: You set a world record! Patient should not be pregnant for over a year.");
		}
		
		if (obj.getWeight() < 0) {
			errorList.addIfNotNull("Patient weight cannot be negative");
		}
		if (obj.getSystolicBloodPressure() < 0)
			errorList.addIfNotNull("Patient Blood Pressure cannot be negative");
		if (obj.getDiastolicBloodPressure() < 0)
			errorList.addIfNotNull("Patient Blood Pressure cannot be negative");
		if (obj.getFetalHeartRate() < 0)
			errorList.addIfNotNull("Fetal Heart Rate cannot be negative");
		if (obj.getPregnancies() < 0)
			errorList.addIfNotNull("Patient cannot be having fewer than 0 children");
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
}

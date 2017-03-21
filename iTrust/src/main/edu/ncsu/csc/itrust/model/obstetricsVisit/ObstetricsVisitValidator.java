package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.time.LocalDateTime;

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
		
		if(obj.getLMP().isAfter(LocalDateTime.now())) {
			errorList.addIfNotNull("lmp: Last Period cannot be in the future");
		}
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
}

package edu.ncsu.csc.itrust.model.childbirthVisit;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class ChildbirthVisitValidator extends POJOValidator<ChildbirthVisit> {

	/**
	 * Used to Validate a childbirth visit object. If the validation does not
	 * succeed, a {@link FormValidationException} is thrown. only performs
	 * checks on the values stored in the object (e.g. Patient MID) Does NOT
	 * validate the format of the visit date and other attributes that are NOT
	 * stored in the object itself
	 * 
	 * @param obj
	 *            the childbirth visit to be validated
	 */
	@Override
	public void validate(ChildbirthVisit obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		Long patientMID = obj.getPatientMID();
		
		if (patientMID == null) {
			errorList.addIfNotNull("Cannot add childbirth visit information: invalid patient MID");
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

		if (obj.getPreferredDelivery() == null) {
			errorList.addIfNotNull("Preferred Delivery: Preferred Delivery is Null");
		}

		
		if (obj.getScheduled() == null) {
			errorList.addIfNotNull("Scheduled: Is Null");
		}
		if (obj.getPitocinDosage() < 0)
			errorList.addIfNotNull("Pitocin Dosage cannot be negative");
		if (obj.getNitrousOxideDosage() < 0)
			errorList.addIfNotNull("Nitrous Oxide Dosage cannot be negative");
		if (obj.getPethidineDosage() < 0)
			errorList.addIfNotNull("Pethidine Dosage cannot be negative");
		if (obj.getEpiduralAnaesthesiaDosage() < 0)
			errorList.addIfNotNull("Epidural Anaesthesia Dosage cannot be negative");
		if (obj.getMagnesiumSulfateDosage() < 0)
			errorList.addIfNotNull("Magnesium Sulfate Dosage cannot be negative");
		if (obj.getRhGlobulinDosage() < 0)
			errorList.addIfNotNull("RH Immune Globulin cannot be negative");
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
		
	}

}

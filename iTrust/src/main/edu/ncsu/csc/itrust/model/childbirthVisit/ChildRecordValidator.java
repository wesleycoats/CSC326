package edu.ncsu.csc.itrust.model.childbirthVisit;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;
import edu.ncsu.csc.itrust.model.ValidationFormat;

public class ChildRecordValidator extends POJOValidator<ChildRecord> {

	@Override
	public void validate(ChildRecord obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		Long motherID = obj.getMotherMID();
		
		if (motherID == null) {
			errorList.addIfNotNull("Cannot add child record information: invalid mother MID");
			throw new FormValidationException(errorList);
		}
		
		errorList.addIfNotNull(checkFormat("Mother MID", motherID, ValidationFormat.NPMID, false));
		
		if (obj.getVisitID() != null) {
			if (obj.getVisitID() <= 0) {
				errorList.addIfNotNull("Visit ID: Negative Visit ID");
			}
		} else {
			errorList.addIfNotNull("Visit ID: Null Visit ID");
		}

		if (obj.getDeliveryType() == null) {
			errorList.addIfNotNull("Delivery Type: Delivery Type is Null");
		}

		
		if (obj.getSex() == null) {
			errorList.addIfNotNull("Sex: Is Null");
		}
		if (obj.getDateOfBirth() == null){
			errorList.addIfNotNull("DOB: Is Null");
		}
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
		
	}

}

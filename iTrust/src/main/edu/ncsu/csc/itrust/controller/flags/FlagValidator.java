package edu.ncsu.csc.itrust.controller.flags;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;

public class FlagValidator extends POJOValidator<Flag>{

	private static final String TYPE1 = "High Blood Pressure";
	private static final String TYPE2 = "Advanced Maternal Age";
	private static final String TYPE3 = "Maternal Allerges";
	private static final String TYPE4 = "Low-Lying Placenta";
	private static final String TYPE5 = "Genetic Miscarriage";
	private static final String TYPE6 = "Abnormal FHR";
	private static final String TYPE7 = "Twins";
	private static final String TYPE8 = "Abnormal Weight Change";
	private static final String TYPE9 = "Negative Blood Type";
	private static final String TYPE10 = "Pregnancy relevant pre-existing conditions";
	
	private static final int IDHASH = 31;

	public FlagValidator() {
	}

	
	public void validate(Flag f) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		long FID = f.getFid();
		long MID = f.getMid();
		long pregID = f.getPregID();
		String flagType = f.getFlagType();
		
		// Make sure that the MID is not -1
		if ( MID == -1 ) {
			errorList.addIfNotNull("Cannot validate Flag: no patient MID");
			throw new FormValidationException(errorList);
		}
		
		if ( MID < 1 ) {
			errorList.addIfNotNull("Invalid patient MID");
		}
		// Make sure that the FID is not -1
		if ( FID == -1 ) {
			errorList.addIfNotNull("Cannot validate Flag: no FID");
			throw new FormValidationException(errorList);
		}
		
		if ( FID < 1 ) {
			errorList.addIfNotNull("Invalid patient FID");
		}
		
		// Check delType is valid
		if ( !flagType.equals(TYPE1) && !flagType.equals(TYPE2) && !flagType.equals(TYPE3) && !flagType.equals(TYPE4) && !flagType.equals(TYPE5) && !flagType.equals(TYPE6) && !flagType.equals(TYPE7) && !flagType.equals(TYPE9) && !flagType.equals(TYPE10)){
			errorList.addIfNotNull("Invalid delivery type");
		}
		
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}


		
	}

}

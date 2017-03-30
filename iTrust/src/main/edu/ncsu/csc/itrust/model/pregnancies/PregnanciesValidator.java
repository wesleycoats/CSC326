package edu.ncsu.csc.itrust.model.pregnancies;

import java.time.Year;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;

/**
 * Validates a Pregnancy object. Makes sure that the MID is not -1, ensures that the number of children
 * is not < 0, makes sure that the hours in labor were not < 0, makes sure that the delivery type is
 * valid, makes sure that the year of conception is not in the future nor more than 150 years in the past,
 * and finally checks the PregnancyID against what it should be, using the same hash and algorithm used
 * in Pregnancies.
 * @author bmhogan
 */
public class PregnanciesValidator extends POJOValidator<Pregnancies> {
	private static final String TYPE1 = "vaginal delivery";
	private static final String type2 = "vaginal delivery vacuum assist";
	private static final String TYPE3 = "vaginal delivery forceps assist";
	private static final String TYPE4 = "caesarean section";
	private static final String TYPE5 = "miscarriage";
	
	/** Should be equal to the hash used in Pregnancies */
	private static final int IDHASH = 31;
	
	
	/** Default constructor */
	public PregnanciesValidator() {
	}

	/**
	 * Used to Validate a pregnancy object. If the validation does not
	 * succeed, a {@link FormValidationException} is thrown. only performs
	 * checks on the values stored in the object (e.g. Patient MID)
	 * 
	 * @param obj the Pregnancies object to be validated
	 */
	@Override
	public void validate(Pregnancies obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		long MID = obj.getPatientMID();
		String delType = obj.getDelType();
		int yearOC = obj.getYearOfConception();
		double hoursInLabor = obj.getHoursInLabor();
		short numChildren = obj.getNumChildren();
		int weeksPregnant = obj.getWeeksPregnant();
		long ID = obj.getPregID();
		
		// Make sure that the MID is not -1
		if ( MID == -1 ) {
			errorList.addIfNotNull("Cannot validate Pregnancy: no patient MID");
			throw new FormValidationException(errorList);
		}
		
		if ( MID < 1 ) {
			errorList.addIfNotNull("Invalid patient MID");
		}
		
		// Check that hoursInLabor and numChildren are not negative
		if ( hoursInLabor < 0 ) {
			errorList.addIfNotNull("Hours in labor cannot be negative");
		}
		if ( numChildren < 0 ) {
			errorList.addIfNotNull("Number of children cannot be negative");
		}
		if ( weeksPregnant < 0 ) {
			errorList.addIfNotNull("Number of weeks pregnant cannot be negative");
		}
		
		// Check delType is valid
		if ( !delType.equals(TYPE1) && !delType.equals(type2) && !delType.equals(TYPE3) && !delType.equals(TYPE4) && !delType.equals(TYPE5) ) {
			errorList.addIfNotNull("Invalid delivery type");
		}

		// Check year of conception. Just make sure its not in the future, and that it isn't more than 150 years ago. 
		int curYear = Year.now().getValue();
		if ( yearOC < (curYear - 150) || yearOC > curYear ) {
			errorList.addIfNotNull("Year of conception is invalid. Cannot be in the future or more than 150 years in the past");
		}
		
		// Now check the ID of the pregnancy object. It should be equal to
		// MID + (numChildren * hash) + (yearOfConception + hash) + Math.round(hoursInLabor * hash * hash);
		long correctID = MID + (numChildren * IDHASH) + (yearOC + IDHASH) + Math.round(hoursInLabor * IDHASH * IDHASH);
		if ( ID != correctID ) {
			errorList.addIfNotNull("Pregnancy ID is incorrect");
		}
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
}

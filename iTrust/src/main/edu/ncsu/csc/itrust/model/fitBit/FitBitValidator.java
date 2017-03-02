package edu.ncsu.csc.itrust.model.fitBit;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.validate.BeanValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;

/**
 * Validates a fitbit bean
 * 
 *  
 * 
 */
public class FitBitValidator extends BeanValidator<FitBitBean> {
	/**
	 * The default constructor.
	 */
	public FitBitValidator() {
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(FitBitBean fb) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Patient", fb.getPatient(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Date", fb.getDate().toString(), ValidationFormat.DATE2, false));
		errorList.addIfNotNull(checkFormat("Calories Burned", fb.getCalBurned(), ValidationFormat.FB_DATA, false));
		errorList.addIfNotNull(checkFormat("Steps", fb.getSteps(), ValidationFormat.FB_DATA, false));
		errorList.addIfNotNull(checkFormat("Floors", fb.getFloors(), ValidationFormat.FB_DATA, false));
		errorList.addIfNotNull(checkFormat("Distance", fb.getDistance(), ValidationFormat.FB_DISTANCE, false));
		errorList.addIfNotNull(checkFormat("Minutes Lightly Active", fb.getMinLightActive(), ValidationFormat.FB_DATA, false));
		errorList.addIfNotNull(checkFormat("Minutes Fairly Active", fb.getMinFairActive(), ValidationFormat.FB_DATA, false));
		errorList.addIfNotNull(checkFormat("Minutes Very Active", fb.getMinVeryActive(), ValidationFormat.FB_DATA, false));
		errorList.addIfNotNull(checkFormat("Activity Calories", fb.getActivityCal(), ValidationFormat.FB_DATA, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}

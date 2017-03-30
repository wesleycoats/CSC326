package edu.ncsu.csc.itrust.model.ultrasound;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.POJOValidator;

/**
 * Validates the input on an Ultrasound object. Makes sure the MID was assigned, none of the values are negative
 * and that the ultrasound image filepath is in teh right location and has a valid extension
 * @author bmhogan
 *
 */
public class UltrasoundValidator extends POJOValidator<Ultrasound>  {

	@Override
	public void validate(Ultrasound obj) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (obj.getMID() == -1)
			errorList.addIfNotNull("Patient MID has not been assigned to this Ultrasound");
		else if (obj.getMID() < 0)
			errorList.addIfNotNull("Patient has an invalid MID");
		
		if (obj.getCRL() <= 0)
			errorList.addIfNotNull("Crown Rump Length cannot be negative");
		if (obj.getBPD() <= 0)
			errorList.addIfNotNull("Biparietal Diameter cannot be negative");
		if (obj.getHC() <= 0)
			errorList.addIfNotNull("Head Circumference cannot be negative");
		if (obj.getFL() <= 0)
			errorList.addIfNotNull("Femur Length cannot be negative");
		if (obj.getOFD() <= 0)
			errorList.addIfNotNull("Occipitofrontal Diameter cannot be negative");
		if (obj.getAC() <= 0)
			errorList.addIfNotNull("Abdominal Circumference cannot be negative");
		if (obj.getHL() <= 0)
			errorList.addIfNotNull("Humerus Length cannnot be negative");
		if (obj.getEFW() <= 0)
			errorList.addIfNotNull("Estimated Fetal Weight cannot be negative");
		
		// If an image was uploaded, make sure it has a valid filepath and extension
//		if (obj.getFile() != null) {
//			String path = obj.getFilePath();
//			if (!path.endsWith(".jpg") && !path.endsWith(".pdf") && !path.endsWith(".png"))
//				errorList.addIfNotNull("Invalid file extension on the ultrasound image");
//			if (!path.startsWith("iTrust/image/ultrasound/"))
//				errorList.addIfNotNull("Invalid file location for ultrasound image");
//		}
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
}

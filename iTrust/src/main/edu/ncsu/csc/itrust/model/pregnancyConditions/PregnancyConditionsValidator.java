package edu.ncsu.csc.itrust.model.pregnancyConditions;

import java.util.ArrayList;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * A class that can validate a successful entry to the pregnancyConditions table
 * @author bmhogan
 */
public class PregnancyConditionsValidator {
	private ArrayList<String> CODE_LIST; 
	/**
	 *  Default constructor. Initializes CODE_LIST and adds the values to it
	 */
	public PregnancyConditionsValidator() {
		CODE_LIST = new ArrayList<String>();
		// Add all of the codes that we are checking for according to the wiki
		CODE_LIST.add("E032");
		CODE_LIST.add("O210");
		CODE_LIST.add("E110");
		CODE_LIST.add("E1136");
		CODE_LIST.add("E114");
		CODE_LIST.add("E118");
		CODE_LIST.add("C719");
		CODE_LIST.add("C50");
		CODE_LIST.add("C43");
		CODE_LIST.add("C40");
		CODE_LIST.add("A501");
		CODE_LIST.add("A6000");
		CODE_LIST.add("B20");
		CODE_LIST.add("A5602");

		CODE_LIST.add("0338-1021-41");
		CODE_LIST.add("17856-0007-1");
		CODE_LIST.add("00904-2407");
		CODE_LIST.add("0440-7026-30");
		CODE_LIST.add("67877-1191");
		CODE_LIST.add("08109-6");
	}

	public void validate(long MID, String code) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (MID < 1) {
			errorList.addIfNotNull("Invalid Patient MID");
		}

		boolean validCode = false;
		for (String s : CODE_LIST) {
			if (s.equals(code))
				validCode = true;
		}
		if (!validCode)
			errorList.addIfNotNull("Invalid ND/ICD-10 code");

		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}
}

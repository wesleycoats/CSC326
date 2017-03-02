package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandBean;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandDAO;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandValidator;

/**
 * Edits the designated FitBit workout Used by hcp/FitBitCalendar.jsp
 * 
 */
public class EditMicrosoftDataAction {
	private MicrosoftBandDAO fitbitDAO;
	private MicrosoftBandValidator validator = new MicrosoftBandValidator();

	public EditMicrosoftDataAction(DAOFactory factory) {
		this.fitbitDAO = factory.getMicrosoftBandDAO();
	}

	/**
	 * Takes information from the fitbitForm param and updates the patient workout
	 * 
	 * @param fitbitForm
	 *            FitBitBean with new information
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void updateInformation(MicrosoftBandBean microsoftForm) throws ITrustException,
			FormValidationException {
		validator.validate(microsoftForm);
		fitbitDAO.editWorkout(microsoftForm);
	}
	
}

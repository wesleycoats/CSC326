package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.fitBit.FitBitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.fitBit.FitBitDAO;
import edu.ncsu.csc.itrust.model.fitBit.FitBitValidator;

/**
 * Edits the designated FitBit workout Used by hcp/FitBitCalendar.jsp
 * 
 */
public class EditFitBitDataAction {
	private FitBitDAO fitbitDAO;
	private FitBitValidator validator = new FitBitValidator();

	public EditFitBitDataAction(DAOFactory factory) {
		this.fitbitDAO = factory.getFitBitDAO();
	}

	/**
	 * Takes information from the fitbitForm param and updates the patient workout
	 * 
	 * @param fitbitForm
	 *            FitBitBean with new information
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void updateInformation(FitBitBean fitbitForm) throws ITrustException,
			FormValidationException {
		validator.validate(fitbitForm);
		fitbitDAO.editWorkout(fitbitForm);
	}
	
}

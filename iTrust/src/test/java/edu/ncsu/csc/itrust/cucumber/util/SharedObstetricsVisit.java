package edu.ncsu.csc.itrust.cucumber.util;

import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitValidator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * A sharedObstetricsVisit file used for cucumber testing for UC94
 * @author bmhogan
 */
public class SharedObstetricsVisit {
	private ObstetricsVisit obs;
	private ObstetricsVisitMySQL obsMySQL;
	private ObstetricsVisitValidator obsValidator;
	private OfficeVisitController ovc;
	private boolean wasAddSuccessful;
	private boolean hasError;
	private String errorMessage = null;
	
	public SharedObstetricsVisit() throws Exception {
		hasError = false;
		wasAddSuccessful = false;
		ovc = new OfficeVisitController(((TestDAOFactory)TestDAOFactory.getTestInstance()).getDataSource());
		obsMySQL = new ObstetricsVisitMySQL(((TestDAOFactory)TestDAOFactory.getTestInstance()).getDataSource());
		obsValidator = new ObstetricsVisitValidator();
		obs = new ObstetricsVisit();
	}
	public OfficeVisitController getOfficeVisitController() {
		return ovc;
	}
	public ObstetricsVisit getObstetricsVisit() {
		return obs;
	}
	public void setObstetricsVisit(ObstetricsVisit obstetricsVisit) {
		this.obs = obstetricsVisit;
	}
	public boolean wasAddSuccessful() {
		return wasAddSuccessful;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
		if (!hasError)
			errorMessage = null;
	}
	public boolean hasError() {
		return hasError;
	}
	
	public Boolean getWasAddSuccessful() {
		return wasAddSuccessful;
	}
	
	public void addVisit() {
		try {
			errorMessage = null;
			obsValidator.validate(obs);
		} catch (Exception e) {
			setHasError(true);
			errorMessage = e.getMessage();
		}
	}
	
	public void addVisitToDatabase() {
		try {
			errorMessage = null;
			obsMySQL.addObstetricsVisit(obs);
			wasAddSuccessful = true;
		} catch (Exception e) {
			setHasError(true);
			errorMessage = e.getMessage();
		}
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setPatientMID(long l) {
		obs.setPatientMID(l);
	}
	public void setVisitID(Long currentOfficeVisitId) {
		obs.setVisitID(currentOfficeVisitId);
	}
	public void setValues(int weeksPregnant, float patientWeight, int bp, int fetalHR, short children,
			boolean p) {
		obs.setWeeksPregnant(weeksPregnant);
		obs.setWeight(patientWeight);
		obs.setBloodPressure(bp);
		obs.setFetalHeartRate(fetalHR);
		obs.setPregnancies(children);
		obs.setPlacentaObserved(p);
	}
}

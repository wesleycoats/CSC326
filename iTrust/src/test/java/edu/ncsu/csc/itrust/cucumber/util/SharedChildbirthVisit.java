package edu.ncsu.csc.itrust.cucumber.util;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildBirthVisitForm;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisit;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitMySQL;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitValidator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class SharedChildbirthVisit {

	private ChildbirthVisit cbv;
	private ChildbirthVisitMySQL cbvMySQL;
	private ChildbirthVisitValidator cbvValidator;
	private ChildBirthVisitForm cbvf;
	private boolean wasAddSuccessful;
	private boolean hasError;
	private String errorMessage = null;
	
	public SharedChildbirthVisit() throws Exception {
		hasError = false;
		wasAddSuccessful = false;
		cbvf = new ChildBirthVisitForm();
		cbvMySQL = new ChildbirthVisitMySQL(((TestDAOFactory)TestDAOFactory.getTestInstance()).getDataSource());
		cbvValidator = new ChildbirthVisitValidator();
		cbv = new ChildbirthVisit();
	}
	public ChildBirthVisitForm getChildBirthVisitForm() {
		return cbvf;
	}
	public ChildbirthVisit getChildbirthVisit() {
		return cbv;
	}
	public void setChildbirthVisit(ChildbirthVisit childbirthVisit) {
		this.cbv = childbirthVisit;
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
			cbvValidator.validate(cbv);
		} catch (Exception e) {
			setHasError(true);
			errorMessage = e.getMessage();
		}
	}
	
	public void addVisitToDatabase() {
		try {
			errorMessage = null;
			cbvMySQL.addChildbirthVisit(cbv);
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
		cbv.setPatientMID(l);
	}
	public void setVisitID(Long currentOfficeVisitId) {
		cbv.setVisitID(currentOfficeVisitId);
	}
	public void setValues(int epiduralAnaesthesiaDosage, int magnesiumSulfateDosage, int nitrousOxideDosage, int pethidineDosage, int pitocinDosage,
			String preferredDelivery, boolean scheduled) {		
		cbv.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
		cbv.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
		cbv.setNitrousOxideDosage(nitrousOxideDosage);
		cbv.setPethidineDosage(pethidineDosage);
		cbv.setPitocinDosage(pitocinDosage);
		cbv.setPreferredDelivery(preferredDelivery);
		cbv.setScheduled(scheduled);
	}	
	
	
}

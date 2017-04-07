package edu.ncsu.csc.itrust.controller.laborReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "drug_allergies_form")
@SessionScoped
public class DrugAllergiesForm {
	// True if the Patient has an allergy to the drug
	private boolean penicillin;
	private boolean sulfa;
	private boolean tetracycline;
	private boolean codeine;
	private boolean nsaids;

}

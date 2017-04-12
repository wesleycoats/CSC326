package edu.ncsu.csc.itrust.controller.laborReport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "pregnancy_complications_form")
@SessionScoped
public class PregComplicationsForm {
	// These are set to true if the patient has the specified complication
	private boolean rhFlag;
	private boolean HBP;
	private boolean advancedAge;
	private boolean relevantConditions;
	private boolean relevantAllergies;
	private boolean lowLyingPlacenta;
	private boolean geneticPotentialMiscarriage;
	private boolean AFHR;
	private boolean multipleChildren;
	private boolean atypicalWeightChange;
	private boolean hyperemesisGravidarum;
	private boolean hypothyroidism;

}

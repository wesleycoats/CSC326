package edu.ncsu.csc.itrust.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.ncsu.csc.itrust.exception.DBException;

@ManagedBean(name = "obstetrics_controller")
@SessionScoped
public class ObstetricsController extends iTrustController {

	public ObstetricsController() throws DBException {
		super();
	}
	
	public boolean isObstetricsPatient() {
		//Here is where we need to determine if the patient is an obstetrics patient.
		//I'm not 100% sure how to do that at the moment.
		return true;
	}
}

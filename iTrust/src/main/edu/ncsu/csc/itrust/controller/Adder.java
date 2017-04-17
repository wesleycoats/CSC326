package edu.ncsu.csc.itrust.controller;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;

import edu.ncsu.csc.itrust.model.medicalProcedure.MedicalProcedure;
import edu.ncsu.csc.itrust.model.medicalProcedure.MedicalProcedureMySQL;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.prescription.Prescription;
import edu.ncsu.csc.itrust.model.prescription.PrescriptionMySQL;

/**
 * Has a "common" add method for PrescriptionController and MedicalProcedureController
 * @author bmhogan
 */
public class Adder extends iTrustController {
	private static final String INVALID_PRESCRIPTION = "Invalid Prescription";
	private static final String INVALID_MEDICAL_PROCEDURE = "Invalid Medical Procedure";
	
	public void add(Object o, Object mySQL, boolean medProc) {
		if (medProc) {
			MedicalProcedure mp = (MedicalProcedure) o;
			MedicalProcedureMySQL sql = (MedicalProcedureMySQL) mySQL;
			try {
	            if (sql.add(mp)) {
	                printFacesMessage(FacesMessage.SEVERITY_INFO, "Medical Procedure successfully created",
	                        "Medical Procedure successfully created", null);
	                Long ovid = getSessionUtils().getCurrentOfficeVisitId();
	                logTransaction(TransactionType.PROCEDURE_ADD, ovid == null ? null : ovid.toString());
	            } else {
	                throw new Exception();
	            }
	        } catch (SQLException e) {
	            printFacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_MEDICAL_PROCEDURE, e.getMessage(), null);
	        } catch (Exception e) {
	            printFacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_MEDICAL_PROCEDURE, INVALID_MEDICAL_PROCEDURE, null);
	        }
		} else {
			Prescription prescription = (Prescription) o;
			PrescriptionMySQL sql = (PrescriptionMySQL) mySQL;
			try {
				if (sql.add(prescription)) {
					printFacesMessage(FacesMessage.SEVERITY_INFO, "Prescription is successfully created",
							"Prescription is successfully created", null);
					logTransaction(TransactionType.PRESCRIPTION_ADD, getSessionUtils().getCurrentOfficeVisitId().toString());
				} else {
					throw new Exception();
				}
			} catch (SQLException e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_PRESCRIPTION, e.getMessage(), null);
			} catch (Exception e) {
				printFacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_PRESCRIPTION, INVALID_PRESCRIPTION, null);
			}
		}
	}
}
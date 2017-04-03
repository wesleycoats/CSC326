package edu.ncsu.csc.itrust.model.childbirthVisit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.webutils.SessionUtils;



@ManagedBean(name = "child_record_form")
@ViewScoped
public class ChildRecordForm {
	private String firstName;
	private String lastName;
	private String email;
	
	private Boolean sex;
	private String deliveryType;
	private LocalDateTime dateTimeOfBirth;
	private LocalDate dateOfBirth;
	private LocalTime timeOfBirth;
	
	private ChildRecord childRecord;
	private PatientBean newBaby;
	private PatientBean parent;
	private PatientDAO patientDAO;
	private AddPatientAction addBaby;
	
	public ChildRecordForm(){
		DAOFactory factory = DAOFactory.getProductionInstance();
		this.sex = true;
		this.deliveryType = "";
		this.dateTimeOfBirth = LocalDateTime.now();
		this.dateOfBirth = dateTimeOfBirth.toLocalDate();
		this.timeOfBirth = dateTimeOfBirth.toLocalTime();
		this.firstName = "";
		this.lastName = "";
		
		try {
			this.addBaby = new AddPatientAction(factory, SessionUtils.getInstance().getSessionLoggedInMIDLong());
			this.patientDAO = factory.getPatientDAO();
			this.parent = patientDAO.getPatient(SessionUtils.getInstance().getCurrentPatientMIDLong().longValue());
			email = parent.getEmail();
			if(email == null) email = "";
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSex() {
		if(sex) {
			return "Male";
		} else {
			return "Female";
		}
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public LocalDateTime getDateTimeOfBirth() {
		return dateTimeOfBirth;
	}
	public void setDateTimeOfBirth(LocalDateTime dateTimeOfBirth) {
		this.dateTimeOfBirth = dateTimeOfBirth;
	}
	public String getDateOfBirth() {
		return dateOfBirth.toString();
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getTimeOfBirth() {
		String time = timeOfBirth.toString();
		if(time.length() > 4) {
			time = time.substring(0, 5);
		}
		return time;
	}
	public void setTimeOfBirth(LocalTime timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}
	
	public void submit() {
		childRecord = new ChildRecord(sex, deliveryType, dateTimeOfBirth);
		newBaby = new PatientBean();
		newBaby.setFirstName(firstName);
		newBaby.setLastName(lastName);
		newBaby.setEmail(email);
		newBaby.setMotherMID("" + parent.getMID());
		try {
			long loggedIn = SessionUtils.getInstance().getSessionLoggedInMIDLong();
			addBaby.addDependentPatient(newBaby, loggedIn, loggedIn);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ITrustException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

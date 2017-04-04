package edu.ncsu.csc.itrust.model.childbirthVisit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;

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
	private String dateOfBirth;
	private String timeOfBirth;
	
	private ChildRecord childRecord;
	private ChildRecordMySQL childsql;
	private PatientBean newBaby;
	private Long motherMID;
	private Long officeID;
	private PatientDAO patientDAO;
	private AddPatientAction addBaby;
	
	private DAOFactory factory;
	private SessionUtils session;
	private String messege = "";
	
	public ChildRecordForm(DAOFactory dao, SessionUtils session, DataSource ds) {
		this.factory = dao;
		this.session = session;
		this.childsql = new ChildRecordMySQL(ds);
		setup();
	}
	
	public ChildRecordForm() throws DBException{
		this.factory = DAOFactory.getProductionInstance();
		this.session = SessionUtils.getInstance();
		this.childsql = new ChildRecordMySQL();
		setup();
	}
	
	private void setup() {
		this.sex = true;
		this.deliveryType = "";
		this.dateTimeOfBirth = LocalDateTime.now();
		this.dateOfBirth = dateTimeOfBirth.toLocalDate().toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
		this.timeOfBirth = dateTimeOfBirth.format(formatter);
		this.firstName = "";
		this.lastName = "";
		this.officeID = session.getCurrentOfficeVisitId();
		try {
			this.addBaby = new AddPatientAction(factory, session.getSessionLoggedInMIDLong());
			this.patientDAO = factory.getPatientDAO();
			PatientBean parent = patientDAO.getPatient(session.getCurrentPatientMIDLong().longValue());
			if(parent != null) {
				email = parent.getEmail();
				this.motherMID = parent.getMID();
			}
			if(email == null) email = "";
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public String getMessege() {
		return messege;
	}
	public void setMessege(String mess) {
		this.messege = mess;
	}
	public Long getMotherMID() {
		return motherMID;
	}
	public void setMotherMID(Long motherMID) {
		this.motherMID = motherMID;
	}
	public Long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(Long officeID) {
		this.officeID = officeID;
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
	
	public Boolean getSex() {
		return sex;
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
	public void setDateTimeOfBirth() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(dateOfBirth, formatter);
			formatter = DateTimeFormatter.ofPattern("hh:mm a");
			LocalTime time = LocalTime.parse(timeOfBirth, formatter);
			this.dateTimeOfBirth = LocalDateTime.of(date, time);
		} catch (DateTimeParseException e) {
			this.dateTimeOfBirth = null;
		}
	}
	public String getDateOfBirth() {
		return dateOfBirth.toString();
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getTimeOfBirth() {
		return timeOfBirth;
	}
	public void setTimeOfBirth(String timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}
	
	public void submit() {
		this.setDateTimeOfBirth();
		childRecord = new ChildRecord(sex, deliveryType, dateTimeOfBirth, 
				motherMID, officeID);
		newBaby = new PatientBean();
		newBaby.setFirstName(firstName);
		newBaby.setLastName(lastName);
		newBaby.setEmail(email);
		newBaby.setMotherMID("" + motherMID);
		try {
			long loggedIn = motherMID;
			addBaby.addDependentPatient(newBaby, loggedIn, loggedIn);
		} catch (FormValidationException e) {
			messege = "Input not Valid.";
		} catch (DBException e) {
			messege = "Database is unhappy";
		} catch (ITrustException e) {
			messege = "oops! Something went wrong.";
		}
	
		try {
			childsql.addChildRedcord(childRecord);
			messege = "Baby successfully added.";
		} catch (DBException e) {
			messege = "Input not Valid.";
		}
	}
}

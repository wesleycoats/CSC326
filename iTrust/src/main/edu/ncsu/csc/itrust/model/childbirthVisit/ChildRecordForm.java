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
import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;
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
	private ObstetricsDataMySQL obsql;
	private ObstetricsVisitMySQL obvsql;
	private PregnanciesMySQL psql;
	private PatientBean newBaby;
	private Long motherMID;
	private Long officeID;
	private Integer hoursInLabor;
	private PatientDAO patientDAO;
	private AddPatientAction addBaby;
	
	private DAOFactory factory;
	private SessionUtils session;
	private String message = "";
	
	public ChildRecordForm(DAOFactory dao, SessionUtils session, DataSource ds) {
		this.factory = dao;
		this.session = session;
		this.childsql = new ChildRecordMySQL(ds);
		this.obsql = new ObstetricsDataMySQL(ds);
		this.obvsql = new ObstetricsVisitMySQL(ds);
		this.psql = new PregnanciesMySQL(ds);
		setup();
	}
	
	public ChildRecordForm() throws DBException{
		this.factory = DAOFactory.getProductionInstance();
		this.session = SessionUtils.getInstance();
		this.childsql = new ChildRecordMySQL();
		this.obsql = new ObstetricsDataMySQL();
		this.obvsql = new ObstetricsVisitMySQL();
		this.psql = new PregnanciesMySQL();
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
		return message;
	}
	public void setMessege(String mess) {
		this.message = mess;
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
	public Integer getHoursInLabor() {
		return hoursInLabor;
	}
	public void setHoursInLabor(Integer hoursInLabor) {
		this.hoursInLabor = hoursInLabor;
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
			Long babyID = addBaby.addDependentPatient(newBaby, loggedIn, loggedIn);
			logBabyBorn(session);
			logCreateBabyRecord(session, babyID);
		} catch (FormValidationException e) {
			message = "Input not Valid.";
		} catch (DBException e) {
			message = "Database is unhappy";
		} catch (ITrustException e) {
			message = "oops! Something went wrong.";
		}
	
		try {
			childsql.addChildRecord(childRecord);
			message = "Baby successfully added.";
		} catch (DBException e) {
			message = "Input not Valid.";
		}
		createPregnancy();
	}
	
	private void createPregnancy() {
		Pregnancies p = new Pregnancies();
		try {
			ObstetricsData obd = obsql.getVisitsForPatient(motherMID).get(0);
			LocalDate edd = obd.getEdd().toLocalDate();
			p.setEdd(edd.atStartOfDay());
			LocalDateTime lmp = obd.getLmp();
			p.setWeeksPregnant(this.setWeeksPregnant(lmp, dateTimeOfBirth));
			int lmp_year = lmp.getYear();
			p.setYearOfConception(lmp_year);
		} catch (DBException | NullPointerException e) {
			// No initial obstetrics visit
		}
		try {
			ObstetricsVisit ov = obvsql.getVisitsForPatient(motherMID).get(0);
			p.setWeightGain(ov.getWeight());
			p.setNumChildren(ov.getPregnancies().shortValue());
		} catch (DBException | NullPointerException e) {
			//No obstetrics visits in system
		}
		p.setDelType(deliveryType.toLowerCase());
		p.setHoursInLabor(hoursInLabor);
		p.setPatientMID(motherMID);
		
		try {
			psql.add(p);
		} catch (DBException e) {
			//Did it work
		}
	}
	
	private int setWeeksPregnant(LocalDateTime lmp, LocalDateTime delivery) {
		int yearsDiff = delivery.getYear() - lmp.getYear();
		int daysLMP = lmp.getDayOfYear();
		int daysNow = delivery.getDayOfYear();
		return (yearsDiff * 366 + (daysNow - daysLMP)) / 7;
	}
	
	public boolean logBabyBorn(SessionUtils session){
		
		boolean logged = true;
		Long id = session.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.BABY_BORN, session.getSessionLoggedInMIDLong(), id, "");
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;

	}

	public boolean logCreateBabyRecord(SessionUtils session, Long babyID) {
		
		boolean logged = true;
		Long id = session.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.CREATE_BABY_RECORD, session.getSessionLoggedInMIDLong(), id, babyID.toString());
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;
	}
}

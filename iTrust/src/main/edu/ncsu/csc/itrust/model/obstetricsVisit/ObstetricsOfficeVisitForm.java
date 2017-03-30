package edu.ncsu.csc.itrust.model.obstetricsVisit;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "obstetrics_office_visit_form")
@ViewScoped
public class ObstetricsOfficeVisitForm {
	
	private long visitID;
	private long patientMID;
	private long weeksPregnant;
	private LocalDateTime lastMenstrualPeriod;
	private LocalDateTime expectedDeliveryDate;
	private LocalDateTime date;
	private Long apptTypeID;
	private float weight;
	private int bloodPressure;
	private int fetalHR;
	private int pregnancies;
	private ObstetricsVisitMySQL mySQL;
	private ObstetricsVisit obstetricsOV;
	private ObstetricsDataMySQL sql;
	
	
	public Integer getPregnancies() {
		return pregnancies;
	}

	public void setPregnancies(Integer pregnancies) {
		this.pregnancies = pregnancies;
	}
	
	public ObstetricsOfficeVisitForm() {
		this.obstetricsOV = new ObstetricsVisit();
		this.expectedDeliveryDate = LocalDateTime.now();
		this.patientMID = SessionUtils.getInstance().getCurrentPatientMIDLong().longValue();
		try {
			this.mySQL = new ObstetricsVisitMySQL();
		} catch (DBException e1) {
			//Do nothing
		}
		
		try {
			this.sql = new ObstetricsDataMySQL();
			this.lastMenstrualPeriod = this.sql.getLmpDate(patientMID);
		} catch (DBException e) {
			//Do nothing
		}
	}
	
	public ObstetricsOfficeVisitForm(long patientMID) {
		this.obstetricsOV = new ObstetricsVisit();
		this.expectedDeliveryDate = LocalDateTime.now();
		this.patientMID = patientMID;
		try {
			this.mySQL = new ObstetricsVisitMySQL();
		} catch (DBException e1) {
			//Do nothing
		}
		
		try {
			this.sql = new ObstetricsDataMySQL();
			this.lastMenstrualPeriod = this.sql.getLmpDate(patientMID);
		} catch (DBException e) {
			//Do nothing
		}
	}
	
	public void getVisitDate() {
		Long pid = Long.parseLong(SessionUtils.getInstance().getSessionPID());
		this.date = this.mySQL.getDateOfVisit(pid);
		this.lastMenstrualPeriod = sql.getLmpDate(pid);
		
		if (this.date == null) {
			this.date = LocalDateTime.now();
		}
		if (this.lastMenstrualPeriod == null) {
			this.lastMenstrualPeriod = LocalDateTime.now();
		}
		
//		System.out.println(this.date.toLocalDate().toString() + "     " + this.lastMenstrualPeriod.toLocalDate().toString());
	}


	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}

	public Long getPatientMID() {
		return patientMID;
	}

	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}

	public Long getWeeksPregnant() {
		return weeksPregnant;
	}

	public void setWeeksPregnant(Long weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}

	public String getLastMenstrualPeriodString() {
		if (lastMenstrualPeriod != null) {
			return lastMenstrualPeriod.toLocalDate().toString();
		}
		else {
			return "";
		}
	}

	public LocalDateTime getLastMenstrualPeriod() {
		return lastMenstrualPeriod;
	}
	
	public void setLastMenstrualPeriod(LocalDateTime lastMenstrualPeriod) {
		this.lastMenstrualPeriod = lastMenstrualPeriod;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate.toLocalDate().toString();
		
	}

	public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public LocalDate getDateString() {
		return date.toLocalDate();
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getApptTypeID() {
		return apptTypeID;
	}

	public void setApptTypeID(Long apptTypeID) {
		this.apptTypeID = apptTypeID;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Integer getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(Integer bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public Integer getFetalHR() {
		return fetalHR;
	}

	public void setFetalHR(Integer fetalHR) {
		this.fetalHR = fetalHR;
	}


	
	public void save() {
//		try( InputStream input = imageFile.getInputStream() ) {
//			Files.copy(input, new File("C:/Users/Jacob/git/csc326-203-Project-01/iTrust/images/", imageFile.getSubmittedFileName()).toPath());			System.out.println(imageFile.getSubmittedFileName());
//			System.out.println(imageFile.getSubmittedFileName());
//			imageFile.write("/iTrust/images/" + imageFile.getSubmittedFileName());
//		} catch (IOException e){
//			e.printStackTrace();
//			System.out.println(e.getMessage());
			//not sure what to say, something like "file does not exist or couldn't be uploaded
//		}
	}
	
	public void submit() {
		this.obstetricsOV.setWeight(weight);
		this.obstetricsOV.setBloodPressure(bloodPressure);
		this.obstetricsOV.setFetalHeartRate(fetalHR);
		this.obstetricsOV.setPregnancies(pregnancies);
		
		try {
			this.mySQL.addObstetricsVisit(obstetricsOV);
		} catch (DBException e) {
			//Do Nothing
		}
	}
}

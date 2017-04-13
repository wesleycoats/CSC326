package edu.ncsu.csc.itrust.model.obstetricsVisit;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import edu.ncsu.csc.itrust.controller.flags.Flag;
import edu.ncsu.csc.itrust.controller.flags.FlagMySQL;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.model.ultrasound.Ultrasound;
import edu.ncsu.csc.itrust.model.ultrasound.UltrasoundMySQL;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "obstetrics_office_visit_form")
@SessionScoped
public class ObstetricsOfficeVisitForm {
	
	private long visitID;
	private long patientMID;
	private long weeksPregnant;
	private LocalDateTime lastMenstrualPeriod;
	private LocalDateTime expectedDeliveryDate;
	private LocalDateTime date;
	private Long apptTypeID;
	private Float weight;
	private Integer systolicBloodPressure;
	private Integer diastolicBloodPressure;
	private Integer fetalHR;
	private Integer pregnancies;
	private ObstetricsVisitMySQL mySQL;
	private FlagMySQL fMySQL;
	private ObstetricsVisit obstetricsOV;
	private ObstetricsDataMySQL sql;
	
	/** Ultrasound fields */
	private UltrasoundMySQL usSQL;
	private Ultrasound ultrasound;
	private double crownRumpLen;
	private double biparietalDia;
	private double headCirc;
	private double femurLen;
	private double occipotoFrontalDia;
	private double abdomincalCirc;
	private double humerusLen;
	private double estimatedFetalWeight;
	private Part uploadedFile;
	private Boolean found;
	private Boolean placenta;
	
	public Boolean getPlacenta() {
		return placenta;
	}

	public void setPlacenta(Boolean placenta) {
		this.placenta = placenta;
	}
	
	public Integer getPregnancies() {
		return pregnancies;
	}

	public void setPregnancies(Integer pregnancies) {
		this.pregnancies = pregnancies;
	}
	
	public ObstetricsOfficeVisitForm() {
		this.placenta = false;
		this.weight = (float) 0;
		this.pregnancies = 0;
		this.systolicBloodPressure = 0;
		this.diastolicBloodPressure = 0;
		this.fetalHR = 0;
		this.obstetricsOV = new ObstetricsVisit();
		this.expectedDeliveryDate = LocalDateTime.now();
		this.patientMID = SessionUtils.getInstance().getCurrentPatientMIDLong().longValue();
		try {
			this.mySQL = new ObstetricsVisitMySQL();
			this.usSQL = new UltrasoundMySQL();
			this.fMySQL = new FlagMySQL();
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
			this.fMySQL = new FlagMySQL();
			this.sql = new ObstetricsDataMySQL();
			this.lastMenstrualPeriod = this.sql.getLmpDate(patientMID);
		} catch (DBException e) {
			//Do nothing
		}
	}
	
	public void getVisitDate() {
		Long pid = Long.parseLong(SessionUtils.getInstance().getSessionPID());
		Long sid = SessionUtils.getInstance().getCurrentOfficeVisitId();
		this.obstetricsOV.setVisitID((long) -1);
		this.obstetricsOV.setPatientMID(pid);
		try {
			List<ObstetricsVisit> list = mySQL.getVisitsForPatient(pid);
			if (list == null || list.size() == 0) {
				this.obstetricsOV.setVisitID(sid);
			}
			else {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getVisitID().equals(sid)) {
						this.obstetricsOV.setVisitID(list.get(i).getVisitID());
						this.obstetricsOV = list.get(i);
						this.found = true;
						break;
					}
				}
				Long check = (long) -1;
				if (this.obstetricsOV.getVisitID().equals(check)) {
					this.obstetricsOV.setVisitID(sid);
				}
			}
		} catch (DBException e) {
			//Do Nothing
		}
		
		
		this.date = this.mySQL.getDateOfVisit(SessionUtils.getInstance().getCurrentOfficeVisitId());
		this.lastMenstrualPeriod = sql.getLmpDate(pid);
		this.patientMID = pid;
		
		if (this.obstetricsOV != null) {
			this.setWeight(this.obstetricsOV.getWeight());
			setSystolicBloodPressure(this.obstetricsOV.getSystolicBloodPressure());
			setDiastolicBloodPressure(this.obstetricsOV.getDiastolicBloodPressure());
			this.fetalHR = this.obstetricsOV.getFetalHeartRate();
			this.pregnancies = this.obstetricsOV.getPregnancies();
			this.obstetricsOV.setDate(date);
		}
		 
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

	public Integer getSystolicBloodPressure() {
		return this.systolicBloodPressure;
	}

	public void setSystolicBloodPressure(Integer bloodPressure) {
		this.systolicBloodPressure = bloodPressure;
	}

	public Integer getDiastolicBloodPressure() {
		return this.diastolicBloodPressure;
	}

	public void setDiastolicBloodPressure(Integer bloodPressure) {
		this.diastolicBloodPressure = bloodPressure;
	}

	public Integer getFetalHR() {
		return fetalHR;
	}

	public void setFetalHR(Integer fetalHR) {
		this.fetalHR = fetalHR;
	}


	
	public void save() {
/*		System.out.println(uploadedFile);
		System.out.println(crownRumpLen);
		System.out.println(biparietalDia);
		System.out.println(headCirc);
		System.out.println(femurLen);
		System.out.println(occipotoFrontalDia);
		System.out.println(abdomincalCirc);
		System.out.println(humerusLen);
		System.out.println(estimatedFetalWeight);
		*/
		if(uploadedFile != null){
			try( InputStream input = uploadedFile.getInputStream() ) {
				//TODO GET REAL VALUES TO UPDATE HERE ARE TEST VALUES
				Ultrasound us = new Ultrasound(this.patientMID, this.date, 1.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0);
				us.setFile(IOUtils.toByteArray(input));
				try {
					this.usSQL.addUltrasound(us);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e){
				e.printStackTrace();
				System.out.println(e.getMessage());
				//			not sure what to say, something like "file does not exist or couldn't be uploaded
			}
		}
	}
	
	public void submit() {
		this.obstetricsOV.setDate(date);
		this.obstetricsOV.setWeight(weight);
		this.obstetricsOV.setSystolicBloodPressure(systolicBloodPressure);
		this.obstetricsOV.setDiastolicBloodPressure(diastolicBloodPressure);
		this.obstetricsOV.setFetalHeartRate(fetalHR);
		this.obstetricsOV.setPregnancies(pregnancies);
		this.obstetricsOV.setWeeksPregnant();
		this.obstetricsOV.setPlacentaObserved(placenta);
		
		if(placenta){
			Flag f = new Flag(1l, patientMID, 1l, "Low-Lying Placenta");
			try {
				fMySQL.add(f);
			} catch (DBException e) {
				// Do nothing
			}
		}
		if(systolicBloodPressure >= 140 || diastolicBloodPressure >= 90) {
			Flag f = new Flag(1l, patientMID, 1l, "High Blood Pressure");
			try {
				fMySQL.add(f);
			} catch (DBException e) {
				// Do nothing
			}
		}
		if(fetalHR < 120 || fetalHR > 160) {
			Flag f = new Flag(1l, patientMID, 1l, "Abnormal FHR");
			try {
				fMySQL.add(f);
			} catch (DBException e) {
				// Do nothing
			}
		}
		
		if(weight < 15 || weight > 35) {
			Flag f = new Flag(1l, patientMID, 1l, "Abnormal Weight Change");
			try {
				fMySQL.add(f);
			} catch (DBException e) {
				// Do nothing
			}
		}
		
		if(pregnancies > 1) {
			Flag f = new Flag(1l, patientMID, 1l, "Twins");
			try {
				fMySQL.add(f);
			} catch (DBException e) {
				// Do nothing
			}
		}
		
		save();
		
		try {
			this.mySQL.addObstetricsVisit(obstetricsOV);
		} catch (DBException e) {
			//Do Nothing
		}
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public double getcrownRumpLen() {
		return crownRumpLen;
	}

	public void setcrownRumpLen(double cRL) {
		crownRumpLen = cRL;
	}

	public double getbiparietalDia() {
		return biparietalDia;
	}

	public void setbiparietalDia(double bPD) {
		biparietalDia = bPD;
	}

	public double getheadCirc() {
		return headCirc;
	}

	public void setheadCirc(double hC) {
		headCirc = hC;
	}

	public double getfemurLen() {
		return femurLen;
	}

	public void setfemurLen(double fL) {
		femurLen = fL;
	}

	public double getoccipotoFrontalDia() {
		return occipotoFrontalDia;
	}

	public void setoccipotoFrontalDia(double oFD) {
		occipotoFrontalDia = oFD;
	}

	public double getabdomincalCirc() {
		return abdomincalCirc;
	}

	public void setabdomincalCirc(double aC) {
		abdomincalCirc = aC;
	}

	public double gethumerusLen() {
		return humerusLen;
	}

	public void sethumerusLen(double hL) {
		humerusLen = hL;
	}

	public double getestimatedFetalWeight() {
		return estimatedFetalWeight;
	}

	public void setestimatedFetalWeight(double eFW) {
		estimatedFetalWeight = eFW;
	}
}

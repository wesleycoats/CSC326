import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

@ManagedBean(name = "obstetrics_office_visit_form")
@ViewScoped
public class ObstetricsOfficeVisitForm {
	
	private Long visitID;
	private Long patientMID;
	private LocalDateTime lastMenstrualPeriod;
	private LocalDateTime expectedDeliveryDate;
	private LocalDateTime date;
	private Long apptTypeID;
	private Float weight;
	private String bloodPressure;
	private long fetalHR;
	private String multiple;
	private Part imageFile;
	private String filePath;
	
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
	public LocalDateTime getLastMenstrualPeriod() {
		return lastMenstrualPeriod;
	}
	public void setLastMenstrualPeriod(LocalDateTime lastMenstrualPeriod) {
		this.lastMenstrualPeriod = lastMenstrualPeriod;
	}
	public LocalDateTime getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public LocalDateTime getDate() {
		return date;
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
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public long getFetalHR() {
		return fetalHR;
	}
	public void setFetalHR(long fetalHR) {
		this.fetalHR = fetalHR;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public Part getImageFile() {
		return imageFile;
	}
	public void setImageFile(Part imageFile) {
		this.imageFile = imageFile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void save() {
		try( InputStream input = imageFile.getInputStream() ) {
//			Files.copy(input, new File("C:/Users/Jacob/git/csc326-203-Project-01/iTrust/images/", imageFile.getSubmittedFileName()).toPath());			System.out.println(imageFile.getSubmittedFileName());
			System.out.println(imageFile.getSubmittedFileName());
//			imageFile.write("/iTrust/images/" + imageFile.getSubmittedFileName());
		} catch (IOException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			//not sure what to say, something like "file does not exist or couldn't be uploaded
		}
	}
	
	public void submit() {
		
	}
	
	
}

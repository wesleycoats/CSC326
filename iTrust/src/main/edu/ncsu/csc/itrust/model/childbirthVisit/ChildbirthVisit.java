/**
 * 
 */
package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;

import edu.ncsu.csc.itrust.exception.DBException;


@ManagedBean(name = "childbirth_visit")
public class ChildbirthVisit {
	private Long visitID;
	private Long patientMID;
	private String preferredDelivery;
	private Boolean scheduled;
	private Integer pitocinDosage;
	private Integer nitrousOxideDosage;
	private Integer pethidineDosage;
	private Integer epiduralAnaesthesiaDosage;
	private Integer magnesiumSulfateDosage;
	private Integer rhGlobulinDosage;

	/**
	 * Default constructor for OfficeVisit
	 */
	public ChildbirthVisit() {
	}

	/**
	 * constructor for OfficeVisit for already existing values
	 */
	public ChildbirthVisit(long visitID, long patientMID, String preferredDelivery, boolean scheduled){
		
		this.visitID = visitID;
		this.patientMID = patientMID;
		this.preferredDelivery = preferredDelivery;
		this.scheduled = scheduled;
		
	}

	/**
	 * @return the patientMID
	 */
	public Long getPatientMID() {
		return patientMID;
	}

	/**
	 * @param patientID
	 *            the patient MID to set
	 */
	public void setPatientMID(Long patientID) {
		this.patientMID = patientID;
	}

	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}
	
	public String getPreferredDelivery(){
		return preferredDelivery;
	}
	
	public void setPreferredDelivery(String preferredDelivery){
		this.preferredDelivery = preferredDelivery;
	}
	
	public Boolean getScheduled(){
		return this.scheduled;
	}
	
	public void setScheduled(Boolean scheduled){
		this.scheduled = scheduled;
	}

	
	public Integer getPitocinDosage() {
		return pitocinDosage;
	}

	public void setPitocinDosage(Integer pitocinDosage) {
		this.pitocinDosage = pitocinDosage;
	}

	public Integer getNitrousOxideDosage() {
		return nitrousOxideDosage;
	}

	public void setNitrousOxideDosage(Integer nitrousOxideDosage) {
		this.nitrousOxideDosage = nitrousOxideDosage;
	}

	public Integer getPethidineDosage() {
		return pethidineDosage;
	}

	public void setPethidineDosage(Integer pethidineDosage) {
		this.pethidineDosage = pethidineDosage;
	}

	public Integer getEpiduralAnaesthesiaDosage() {
		return epiduralAnaesthesiaDosage;
	}

	public void setEpiduralAnaesthesiaDosage(Integer epiduralAnaesthesiaDosage) {
		this.epiduralAnaesthesiaDosage = epiduralAnaesthesiaDosage;
	}

	public Integer getMagnesiumSulfateDosage() {
		return magnesiumSulfateDosage;
	}

	public void setMagnesiumSulfateDosage(Integer magnesiumSulfateDosage) {
		this.magnesiumSulfateDosage = magnesiumSulfateDosage;
	}

	private LocalDateTime getOfficeVisitDate() {
		LocalDateTime officeVisit = null;
		try {
			ChildbirthVisitMySQL cbsql = new ChildbirthVisitMySQL();
			officeVisit = cbsql.getDateOfVisit(visitID);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return officeVisit;
	}

	public Integer getRhGlobulinDosage() {
		return rhGlobulinDosage;
	}

	public void setRhGlobulinDosage(Integer rhGlobulinDosage) {
		this.rhGlobulinDosage = rhGlobulinDosage;
	}

}

package edu.ncsu.csc.itrust.model.ultrasound;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains the relevant information for an Ultrasound visit, alongwith the local pathname to where
 * an image of the Ultrasound was stored.
 * @author bmhogan
 */
public class Ultrasound {
	private long MID; 
	private LocalDateTime date;
	private double CRL;
	private double BPD;
	private double HC;
	private double FL;
	private double OFD;
	private double AC;
	private double HL;
	private double EFW;
	private String imageFilePath;
	
	public Ultrasound() {
		clearFields();
	}
	
	public Ultrasound(long MID, LocalDateTime date, double CRL, double BPD, double HC, double FL, double OFD, double AC, double HL, double EFW) {
		this.MID = MID;
		this.date = date;
		this.CRL = CRL;
		this.BPD = BPD;
		this.HC = HC;
		this.FL = FL;
		this.OFD = OFD;
		this.AC = AC;
		this.HL = HL;
		this.EFW = EFW;
		imageFilePath = "";
	}
	
	private void clearFields() {
		MID = -1;
		date = null;
		CRL = Double.MIN_NORMAL;
		BPD = Double.MIN_NORMAL;
		HC = Double.MIN_NORMAL;
		FL = Double.MIN_NORMAL;
		OFD = Double.MIN_NORMAL;
		AC = Double.MIN_NORMAL;
		HL = Double.MIN_NORMAL;
		EFW = Double.MIN_NORMAL;
		imageFilePath = "";
	}
	
	public long getMID() {
		return MID;
	}

	public void setMID(long mID) {
		MID = mID;
	}

	public void setFilePath(String filePath) {
		this.imageFilePath = filePath;
	}
	
	public String getFilePath() {
		return imageFilePath;
	}

	public double getCRL() {
		return CRL;
	}

	public void setCRL(double cRL) {
		CRL = cRL;
	}

	public double getBPD() {
		return BPD;
	}

	public void setBPD(double bPD) {
		BPD = bPD;
	}

	public double getHC() {
		return HC;
	}

	public void setHC(double hC) {
		HC = hC;
	}

	public double getFL() {
		return FL;
	}

	public void setFL(double fL) {
		FL = fL;
	}

	public double getOFD() {
		return OFD;
	}

	public void setOFD(double oFD) {
		OFD = oFD;
	}

	public double getAC() {
		return AC;
	}

	public void setAC(double aC) {
		AC = aC;
	}

	public double getHL() {
		return HL;
	}

	public void setHL(double hL) {
		HL = hL;
	}

	public double getEFW() {
		return EFW;
	}

	public void setEFW(double eFW) {
		EFW = eFW;
	}
	
	public void setDate(LocalDateTime d) {
		date = d;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 * Returns a String representation of the Date, in the format dd/MM/yyyy
	 * @return
	 */
	public String getDateString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(format);
	}
}

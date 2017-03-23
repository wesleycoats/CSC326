package edu.ncsu.csc.itrust.model.ultrasound;

/**
 * Contains the relevant information for an Ultrasound visit, alongwith the local pathname to where
 * an image of the Ultrasound was stored.
 * @author bmhogan
 */
public class Ultrasound {
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
	
	public Ultrasound(double CRL, double BPD, double HC, double FL, double OFD, double AC, double HL, double EFW) {
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
	
	
}

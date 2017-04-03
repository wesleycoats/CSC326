package childbirthVisit;

public class DrugRecord {
	
	private Long drugRecordID;
	private String drugType;
	private Integer dosage;
	
	public DrugRecord(Long ID, String drugType, Integer dosage){
		this.setDrugRecordID(ID);
		this.setDrugType(drugType);
		this.setDosage(dosage);
	}

	public Long getDrugRecordID() {
		return drugRecordID;
	}

	public void setDrugRecordID(Long drugRecordID) {
		this.drugRecordID = drugRecordID;
	}

	public String getDrugType() {
		return drugType;
	}

	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}

	public Integer getDosage() {
		return dosage;
	}

	public void setDosage(Integer dosage) {
		this.dosage = dosage;
	}

}

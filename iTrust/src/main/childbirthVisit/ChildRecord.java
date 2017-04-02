package childbirthVisit;

import java.time.LocalDateTime;

public class ChildRecord {
	
	private Boolean sex;
	private String deliveryType;
	private LocalDateTime dateOfBirth;
	
	public ChildRecord(Boolean sex, String deliveryType, LocalDateTime dateOfBirth){
		this.sex = sex;
		this.deliveryType = deliveryType;
		this.dateOfBirth = dateOfBirth;
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
	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}

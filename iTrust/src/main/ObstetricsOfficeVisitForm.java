import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "obstetrics_office_visit_form")
@ViewScoped
public class ObstetricsOfficeVisitForm {
	
	private Long visitID;
	private Long patientMID;
	private LocalDateTime lastMenstralPeriod;
	private LocalDateTime expectedDeliveryDate;
	private LocalDateTime date;
	private Long apptTypeID;
	private Float weight;
	private String bloodPressure;
	private long fetalHR;
	private String multiple;
	
	
}

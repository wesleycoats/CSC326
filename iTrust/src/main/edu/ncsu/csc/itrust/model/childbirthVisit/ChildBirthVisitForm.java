package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

@ManagedBean(name = "child_birth_visit_form")
@ViewScoped
public class ChildBirthVisitForm {
	
	private String ovID;
	private Long patientMID;
	private Long visitID;
	private String preferredDelivery;
	private Boolean scheduled;
	private OfficeVisitMySQL ovMySQL;
	private ChildbirthVisitMySQL cbMySQL;
	private ChildbirthVisit cbv;
	private Integer RHImmuneGlobulin;
	private Integer epiduralAnaesthesiaDosage;
	private Integer magnesiumSulfateDosage;
	private Integer nitrousOxideDosage;
	private Integer pethidineDosage;
	private Integer pitocinDosage;
	private Boolean found;
	private SessionUtils utils;
	private DAOFactory factory;
	
	public ChildBirthVisitForm() {
		this.utils = SessionUtils.getInstance();
		setup();
	}
	
	/**
	 * for testing
	 * @param utils
	 */
	public ChildBirthVisitForm(SessionUtils utils){
		
		this.utils = utils;
		setup();
	}

	
	private void setup() {
		this.factory = DAOFactory.getProductionInstance();
		this.ovID = utils.getCurrentOfficeVisitId().toString();
		this.cbv = new ChildbirthVisit();
		this.found = false;
		try {
			this.cbMySQL = new ChildbirthVisitMySQL();
			ovMySQL = new OfficeVisitMySQL();
		} catch (DBException e) {
			//Do Nothing
		}
		this.getVisitDate();

		
	}

	public void submit() {
		//This method is called whenever the submit button is clicked.
		//First we will set all of the values, then we will save them to
		//the database using the mySQL files that have not yet been created.
		//After that we will direct the user to the Baby Info page.
		this.cbv.setPatientMID(patientMID);
		this.cbv.setPreferredDelivery(preferredDelivery);
		this.cbv.setScheduled(scheduled);
		this.cbv.setVisitID(visitID);
		this.cbv.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
		this.cbv.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
		this.cbv.setNitrousOxideDosage(nitrousOxideDosage);
		this.cbv.setPethidineDosage(pethidineDosage);
		this.cbv.setPitocinDosage(pitocinDosage);
		this.cbv.setRhGlobulinDosage(RHImmuneGlobulin);

		if (found) {
			try {
				this.cbMySQL.updateChildbirthVisit(cbv);
				logUpdateChildbirthVisit(utils);
				if(this.cbv.getEpiduralAnaesthesiaDosage() > 0 || this.cbv.getMagnesiumSulfateDosage() > 0 || this.cbv.getNitrousOxideDosage() > 0 || this.cbv.getPethidineDosage() > 0 || this.cbv.getPitocinDosage() > 0 || this.cbv.getRhGlobulinDosage() > 0) logAddChildbirthDrugs(utils);
			} catch (Exception e) {
				//Do Nothing
			}
		}
		else {
			try {
				this.cbMySQL.addChildbirthVisit(cbv);
				logCreateChildbirthVisit(utils);
				if(this.cbv.getEpiduralAnaesthesiaDosage() > 0 || this.cbv.getMagnesiumSulfateDosage() > 0 || this.cbv.getNitrousOxideDosage() > 0 || this.cbv.getPethidineDosage() > 0 || this.cbv.getPitocinDosage() > 0 || this.cbv.getRhGlobulinDosage() > 0) logAddChildbirthDrugs(utils);
			} catch (Exception e) {
				//Do Nothing
			}
		}
	}
	
	public boolean logUpdateChildbirthVisit(SessionUtils utils) {

		boolean logged = true;
		Long id = utils.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.EDIT_CHILDBIRTH_VISIT, utils.getSessionLoggedInMIDLong(), id, "");
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;
		
	}

	public boolean logCreateChildbirthVisit(SessionUtils utils) {

		boolean logged = true;
		Long id = utils.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.CREATE_CHILDBIRTH_VISIT, utils.getSessionLoggedInMIDLong(), id, "");
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;
			
	}
	
	public boolean logAddChildbirthDrugs(SessionUtils utils) {

		boolean logged = true;
		Long id = utils.getCurrentPatientMIDLong();
		if (id != null) {
			EventLoggingAction logAction = new EventLoggingAction(factory);
			try {
				logAction.logEvent(TransactionType.ADD_CHILDBIRTH_DRUGS, utils.getSessionLoggedInMIDLong(), id, "");
			} catch (DBException e) {
				logged = false;
			}
		} else {
			logged = false;
		}
		return logged;
			
	}


	public Integer getRHImmuneGlobulin() {
		return RHImmuneGlobulin;
	}

	public void setRHImmuneGlobulin(Integer RHImmuneGlobulin) {
		this.RHImmuneGlobulin = RHImmuneGlobulin;
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

	public Integer getPitocinDosage() {
		return pitocinDosage;
	}

	public void setPitocinDosage(Integer pitocinDosage) {
		this.pitocinDosage = pitocinDosage;
	}

	public String getDateString() {
		OfficeVisit ov = null;
		try {
			if(ovMySQL != null) {
				ov = ovMySQL.getByID(visitID);
			}
		} catch (Exception e) {
			//Do Nothing
		}
		
		if (ov != null) {
			return ov.getDate().toLocalDate().toString();
		}
		return "Date not Found.";	
	}
	
	public String getOvID() {
		return ovID;
	}

	public void setOvID(String ovID) {
		this.ovID = ovID;
	}
	
	public Long getPatientMID() {
		return patientMID;
	}


	public void setPatientMID(Long patientMID) {
		this.patientMID = patientMID;
	}


	public Long getVisitID() {
		return visitID;
	}


	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}


	public String getPreferredDelivery() {
		return preferredDelivery;
	}


	public void setPreferredDelivery(String preferredDelivery) {
		this.preferredDelivery = preferredDelivery;
	}


	public Boolean getScheduled() {
		return scheduled;
	}


	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}


	public void getVisitDate() {
		String id = utils.getSessionPID();
		if(id != null) this.patientMID = Long.parseLong(id);
		this.visitID = SessionUtils.getInstance().getCurrentOfficeVisitId();
		
		//After this we will need to get the information from a previously existing child birth
		//office visit. 
		
		try {
			List<ChildbirthVisit> list = Collections.EMPTY_LIST;
			list = this.cbMySQL.getVisitsForPatient(patientMID);
			if (list == null || list.size() == 0) {
				this.cbv.setVisitID(visitID);
			}
			else {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getVisitID().equals(visitID)) {
						this.cbv = list.get(i);
						this.found = true;
						break;
					}
				}
				Long check = (long) -1;
				if (this.cbv.getVisitID().equals(check)) {
					this.cbv.setVisitID(visitID);
				}
			}
		} catch (Exception e) {
			//Do nothing
		}
		
		if (this.cbv != null) {
			this.epiduralAnaesthesiaDosage = cbv.getEpiduralAnaesthesiaDosage();
			this.magnesiumSulfateDosage = cbv.getMagnesiumSulfateDosage();
			this.nitrousOxideDosage = cbv.getNitrousOxideDosage();
			this.pethidineDosage = cbv.getPethidineDosage();
			this.pitocinDosage = cbv.getPitocinDosage();
			this.preferredDelivery = cbv.getPreferredDelivery();
			this.scheduled = cbv.getScheduled();
			this.RHImmuneGlobulin = cbv.getRhGlobulinDosage();
		}
	} 
}

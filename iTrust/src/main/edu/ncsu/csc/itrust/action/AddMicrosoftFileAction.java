package edu.ncsu.csc.itrust.action;

import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import edu.ncsu.csc.itrust.CSVParser;
import edu.ncsu.csc.itrust.exception.AddMicrosoftBandFileException;
import edu.ncsu.csc.itrust.exception.CSVFormatException;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandBean;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandDAO;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandValidator;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * Used for Upload Patient File page (uploadPatientFile.jsp).
 */
public class AddMicrosoftFileAction {
	
	/**
	 * Holds the accumulated list of errors from the CSVParser and this class
	 */
	private ErrorList errors;
	/**
	 * Holds the CSV header from the CSVParser
	 */
	private ArrayList<String> CSVHeader;
	/**
	 * Holds the CSV data from the CSVParser
	 */
	private ArrayList<ArrayList<String>> CSVData;
	/**
	 * Holds the list of MicrosoftBandBean for passing back to the UI
	 */
	private ArrayList<MicrosoftBandBean> workouts = new ArrayList<MicrosoftBandBean>();
	
	/**
	 * List of fields required to be in the CSV
	 */
	private String[] requiredFields = {"Date", "Steps", "Calories", "HR_Lowest", "HR_Highest", "HR_Average",
								"Total_Miles_Moved", "Active_Hours", "Floors_Climbed", "UV_Exposure_Minutes"};
	
	/**
	 * Array to map the required field lists above to the uploaded CSV header list (which may be in any order)
	 */
	private Integer requiredFieldsMapping[] = new Integer[10];

	/**
	 * PatientDAO used to add patients to the DB
	 */
	private MicrosoftBandDAO microsoftDAO;
	/**
	 * MID of the Patient chosen by the MID
	 */
	private long patientID;

	/**
	 * Accepts the DAO factory and the CSV stream from the view and parses it.
	 * 
	 * @param factory The DAO factory
	 * @param loggedInMID The MID of the HCP
	 * @param CSVStream The CSV stream uploaded by the user
	 * @throws CSVFormatException
	 */
	public AddMicrosoftFileAction(InputStream CSVStream, DAOFactory factory, long patientID) throws CSVFormatException, AddMicrosoftBandFileException {
		if(factory!=null){
			this.microsoftDAO = factory.getMicrosoftBandDAO();
			this.patientID = patientID;
		}
		CSVParser parser = new CSVParser(CSVStream);
		CSVHeader = parser.getHeader();
		CSVData = parser.getData();
		errors = parser.getErrors();
		buildMappings(CSVHeader);
		try{
			createPatients();
		}catch(DBException e){
			throw new AddMicrosoftBandFileException("Database error while adding new workout!");
		}
	}
	
	/**
	 * Gets the patient list
	 * 
	 * @return ArrayList<PatientBean> The patients from the parsed file
	 */
	public ArrayList<MicrosoftBandBean> getPatients(){
		return workouts;
	}
	
	/**
	 * Gets the error list
	 * 
	 * @return ErrorList All errors encountered while parsing
	 */
	public ErrorList getErrors(){
		return errors;
	}
	
	/**
	 * Builds the mappings between the local arrays and the CSV file
	 * Also checks for missing required, duplicate, and invalid fields
	 * 
	 * @param CSVHeader
	 * @throws AddMicrosoftBandFileException 
	 */
	private void buildMappings(ArrayList<String> CSVHeader) throws AddMicrosoftBandFileException{
		boolean valid;
		for(int i=0; i<CSVHeader.size(); i++){
			valid=false;
			for(int j=0; j<requiredFields.length; j++){
				if(CSVHeader.get(i).equalsIgnoreCase(requiredFields[j])){
					if(requiredFieldsMapping[j]==null){
						valid=true;
						requiredFieldsMapping[j]=i;
					}else{
						throw new AddMicrosoftBandFileException("Duplicate field \""+CSVHeader.get(i)+"\"!");
					}
				}
			}
			
			if(valid == false){
				throw new AddMicrosoftBandFileException("Field \""+CSVHeader.get(i)+"\" is invalid!");
			}
		}
		for(int i=0; i<requiredFieldsMapping.length; i++){
			if(requiredFieldsMapping[i]==null){
				throw new AddMicrosoftBandFileException("Required field \""+requiredFields[i]+"\" is missing!");
			}
		}
	}
	
	/**
	 * Creates the patients and adds them to the DB
	 * 
	 * @throws DBException
	 */
	private void createPatients() throws DBException {
		for(int i = 0; i < CSVData.size(); i++){
			MicrosoftBandBean temp = new MicrosoftBandBean();
			
			temp.setPatient(patientID);
			String date = CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("Date")]);
			date = date.trim();
			
			try {
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				format.setLenient(false);
				java.util.Date d1;
				d1 = format.parse(date);
				Date d2 = new java.sql.Date(d1.getTime());
				temp.setDate(d2);
				temp.setID(patientID + d2.toString());
				
			
				temp.setSteps(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("Steps")])));
				temp.setCalories(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("Calories")])));
				temp.setHRLowest(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("HR_Lowest")])));
				temp.setHRHighest(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("HR_Highest")])));
				temp.setHRAverage(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("HR_Average")])));
				temp.setDistance(Float.parseFloat(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("Total_Miles_Moved")])));
				temp.setActivityHours(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("Active_Hours")])));
				temp.setFloors(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("Floors_Climbed")])));
				temp.setMinUVExposure(Integer.parseInt(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("UV_Exposure_Minutes")])));
				
				new MicrosoftBandValidator().validate(temp);
				
				MicrosoftBandBean old = microsoftDAO.getWorkoutByID(temp.getID());
				if(old == null) {
					microsoftDAO.addNewWorkout(temp);
				} else {
					microsoftDAO.editWorkout(temp);
				}
				workouts.add(temp);
			} catch (ParseException e1) {
				errors.addIfNotNull("Could Not Parse String on line: " + i);
			} catch (NumberFormatException e) {
				errors.addIfNotNull("Missing Entry/Format Exception on line: " + i);
			} catch(FormValidationException e){
				for(int j=0; j<e.getErrorList().size(); j++){
					System.out.println(e.getErrorList().get(j));
				}
				errors.addIfNotNull("Input validation failed for workout on Date: " + temp.getDate() + " for Patient: " + temp.getPatient()+"\"!");
			} catch (ITrustException e) {
				e.printStackTrace();
			}
		}
	}
}

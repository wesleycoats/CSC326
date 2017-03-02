package edu.ncsu.csc.itrust.unit.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.ncsu.csc.itrust.action.AddMicrosoftFileAction;
import edu.ncsu.csc.itrust.exception.AddMicrosoftBandFileException;
import edu.ncsu.csc.itrust.exception.CSVFormatException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import junit.framework.TestCase;

/**
 * Tests adding a patient file
 *
 */
@SuppressWarnings("unused")
public class AddMicrosoftFileActionTest extends TestCase {
	DAOFactory prodDAO = TestDAOFactory.getTestInstance();
	MicrosoftBandDAO authDAO = prodDAO.getMicrosoftBandDAO();
	String fileDirectory = "testing-files/sample_microsoftupload/";
	static final long ID = 80L;

	@Override
	protected void setUp() throws Exception {

	}

	/**
	 * Test when you have valid data
	 * 
	 * @throws CSVFormatException
	 * @throws AddPatientFileException
	 * @throws FileNotFoundException
	 */
	public void testValidData() throws CSVFormatException, AddMicrosoftBandFileException, FileNotFoundException {
		InputStream testFile = new FileInputStream(fileDirectory + "MS_Band_Valid.csv");
		AddMicrosoftFileAction apfa = new AddMicrosoftFileAction(testFile, prodDAO, ID);
//		assertEquals(4, apfa.getPatients().size());
		assertFalse(apfa.getErrors().hasErrors());
	}

	/**
	 * Tests with invalid data
	 * 
	 * @throws CSVFormatException
	 * @throws AddPatientFileException
	 * @throws FileNotFoundException
	 */
	public void testInvalidData() throws CSVFormatException, AddMicrosoftBandFileException, FileNotFoundException {
		InputStream testFile = new FileInputStream(fileDirectory + "MS_Band_Data.csv");
		AddMicrosoftFileAction apfa = new AddMicrosoftFileAction(testFile, prodDAO, ID);
//		assertEquals(4, apfa.getPatients().size());
		assertTrue(apfa.getErrors().hasErrors());
	}
}

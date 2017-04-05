package edu.ncsu.csc.itrust.unit.model.childbirth;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.childbirthVisit.DrugRecord;

/**
 * Tests the functionality of the DrugRecord class.
 * @author bmhogan
 */
public class DrugRecordTest {
	private long testID = 11L;
	private String testType = "Test_Drug";
	private int testDosage = 115;

	@Test
	public void testDrugRecord() {
		DrugRecord dr = new DrugRecord(testID, testType, testDosage);
		Assert.assertEquals(testID, dr.getDrugRecordID().longValue());
		Assert.assertEquals(testType, dr.getDrugType());
		Assert.assertEquals(testDosage, dr.getDosage().intValue());
		
		Assert.assertEquals(Long.valueOf(testID), dr.getDrugRecordID());
		Assert.assertEquals(Integer.valueOf(testDosage), dr.getDosage());
		
		// Now set the values and make sure everything works
		long testID_2 = 22L;
		String testType_2 = "Other_Drug";
		int testDosage_2 = 54;
		
		dr.setDrugRecordID(testID_2);
		dr.setDrugType(testType_2);
		dr.setDosage(testDosage_2);
		
		Assert.assertEquals(testID_2, dr.getDrugRecordID().longValue());
		Assert.assertEquals(testType_2, dr.getDrugType());
		Assert.assertEquals(testDosage_2, dr.getDosage().intValue());
		
		Assert.assertEquals(Long.valueOf(testID_2), dr.getDrugRecordID());
		Assert.assertEquals(Integer.valueOf(testDosage_2), dr.getDosage());
	}

}

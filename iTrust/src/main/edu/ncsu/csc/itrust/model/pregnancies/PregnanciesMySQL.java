package edu.ncsu.csc.itrust.model.pregnancies;

public class PregnanciesMySQL {
	
	
	
	private class PregnanciesLoader {
		/** Lab Procedure table name */
		private static final String PREGNANCIES_TABLE_NAME = "pregnancies";
		
		/** Database column names */
		private static final String PATIENT_MID = "patientMID";
		private static final String CONCEPTION_YEAR = "yearOfConception";
		private static final String WEEKS_PREG = "weeksPregnant";
		private static final String HOURS_IN_LABOR = "hoursInLabor";
		private static final String WEIGHT_GAIN = "weightGain";
		private static final String TYPE = "deliveryType";
		private static final String NUM_CHILDREN = "numChildren";
		private static final String PREG_ID = "pregnancyID";
		
		private static final String INSERT = "INSERT INTO " + PREGNANCIES_TABLE_NAME + " (" 
				+ PATIENT_MID + ", "
				+ CONCEPTION_YEAR + ", "
				+ WEEKS_PREG + ", "
				+ HOURS_IN_LABOR + ", "
				+ WEIGHT_GAIN + ", "
				+ TYPE + ", "
				+ NUM_CHILDREN + ", "
				+ PREG_ID +	") VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		private static final String UPDATE = "UPDATE " + PREGNANCIES_TABLE_NAME + " SET " 
				+ PATIENT_MID + "=?, "
				+ CONCEPTION_YEAR + "=?, "
				+ WEEKS_PREG + "=?, "
				+ HOURS_IN_LABOR + "=?, "
				+ WEIGHT_GAIN + "=?, "
				+ TYPE + "=?, "
				+ NUM_CHILDREN + "=?, WHERE" + PREG_ID + " =?";
		
		public static final String SELECT_BY_PATIENT_MID = "SELECT * from " + PREGNANCIES_TABLE_NAME + " WHERE "
				+ PATIENT_MID + "=?;";
		
		
	}
}

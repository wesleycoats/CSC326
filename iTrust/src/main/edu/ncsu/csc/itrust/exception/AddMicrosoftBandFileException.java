package edu.ncsu.csc.itrust.exception;

/**
 * This exception is thrown to indicate any type of error which occurs while
 * parsing a CSV file.
 */
public class AddMicrosoftBandFileException extends Exception {
	/**
	 * Unique identifier for the exception
	 */
	private static final long serialVersionUID = 4038127839098754686L;
	/**
	 * The error message for the exception
	 */
	String message;
	
	/**
	 * Constructor initializing the error message string
	 * 
	 * @param string The error message string
	 */
	public AddMicrosoftBandFileException(String string) {
		message=string;
	}
	
	/**
	 * Returns the exception's error message
	 * 
	 * @return The error message from the exception
	 */
	@Override
	public String getMessage(){
		return message;
	}
	
}

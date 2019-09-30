package com.pub.dao.exception;

/**
 * Exception is thrown request is invalid
 * 
 */

public class InvalidRequestException extends RuntimeException {

	/**
	 * Serial Version UID
	 */

	private static final long serialVersionUID = 4782749985193530748L;

	/**
	 * Default constructor
	 */
	public InvalidRequestException() {
		super();
	}

	/**
	 * Constructor used set exception message and cause
	 * 
	 * @param message - Exception Message
	 * @param cause   - Cause of exception
	 */
	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor used set exception message
	 * 
	 * @param message - Exception Message
	 */
	public InvalidRequestException(String message) {
		super(message);
	}

	/**
	 * Constructor used set exception cause
	 * 
	 * @param cause - Cause of exception
	 */
	public InvalidRequestException(Throwable cause) {
		super(cause);
	}

}

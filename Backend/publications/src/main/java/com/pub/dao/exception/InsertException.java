package com.pub.dao.exception;

/**
 * Exception is thrown if entity insertion fails
 *
 */
public class InsertException extends RuntimeException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2922632954743908608L;

	/**
	 * Default constructor
	 */
	public InsertException() {
		super();
	}

	/**
	 * Constructor used set exception message
	 *
	 * @param message - Exception Message
	 */
	public InsertException(final String message) {
		super(message);
	}

	/**
	 * Constructor used set exception message and cause
	 *
	 * @param message - Exception Message
	 * @param cause   - Cause of exception
	 */
	public InsertException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor used set exception cause
	 *
	 * @param cause - Cause of exception
	 */
	public InsertException(final Throwable cause) {
		super(cause);
	}

}

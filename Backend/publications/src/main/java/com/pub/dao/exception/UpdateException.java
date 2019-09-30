package com.pub.dao.exception;

/**
 * Exception is thrown if entity update fails
 *
 */
public class UpdateException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 35711792306634245L;

	/**
	 * Default constructor
	 */
	public UpdateException() {
		super();
	}

	/**
	 * Constructor used set exception message
	 *
	 * @param message - Exception Message
	 */
	public UpdateException(final String message) {
		super(message);
	}

	/**
	 * Constructor used set exception message and cause
	 *
	 * @param message - Exception Message
	 * @param cause   - Cause of exception
	 */
	public UpdateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor used set exception cause
	 *
	 * @param cause - Cause of exception
	 */
	public UpdateException(final Throwable cause) {
		super(cause);
	}

}

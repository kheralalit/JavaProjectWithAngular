package com.pub.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The class holds the details of the rest api response
 *
 * @author Shraddha Jadhav
 *
 */
@JsonInclude(Include.NON_NULL)
public class Response {
	/**
	 * Message details
	 */
	private String message;
	/**
	 * The result of the sent tin response
	 */
	private Object result;
	/**
	 * Type of the message whether it is success or error
	 */
	private String type;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}

	public String getType() {
		return type;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	public void setResult(final Object result) {
		this.result = result;
	}

	public void setType(final String type) {
		this.type = type;
	}

}

package com.pub.rest.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pub.dao.exception.FindException;
import com.pub.dao.exception.InsertException;
import com.pub.dao.exception.InvalidRequestException;
import com.pub.dao.exception.UpdateException;
import com.pub.rest.model.Response;
import com.pub.rest.util.Constant;

@ControllerAdvice
public class PublicationExceptionHandler
		extends ResponseEntityExceptionHandler {

	/**
	 * Get the {@link ResponseEntity} instance which has {@link Response} as
	 * type from given message.
	 *
	 * @param message - Error message to sent to UI
	 * @return
	 */
	private ResponseEntity<Response> getResponse(final String message) {
		final Response error = new Response();
		error.setMessage(message);
		error.setType(Constant.ERROR);
		return ResponseEntity.ok(error);
	}

	/**
	 * This is the generic exception handler
	 *
	 * @param ex      - Instance of {@link Exception}
	 * @param request - Instance of {@link WebRequest}
	 * @return Instance of {@link ResponseEntity} which has {@link Response} as
	 *         type
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Response> handleAllExceptions(
			final Exception ex, final WebRequest request) {
		return getResponse(ex.getMessage());
	}

	/**
	 * This is the FindException handler
	 *
	 * @param ex      - Instance of {@link FindException}
	 * @param request - Instance of {@link WebRequest}
	 * @return Instance of {@link ResponseEntity} which has {@link Response} as
	 *         type
	 */

	@ExceptionHandler(FindException.class)
	public final ResponseEntity<Response> handleFindException(
			final FindException ex, final WebRequest request) {
		return getResponse(ex.getMessage());
	}

	/**
	 * This is the StorageException handler
	 *
	 * @param ex      - Instance of {@link StorageException}
	 * @param request - Instance of {@link WebRequest}
	 * @return Instance of {@link ResponseEntity} which has {@link Response} as
	 *         type
	 */

	@ExceptionHandler(InsertException.class)
	public final ResponseEntity<Response> handleInsertException(
			final InsertException ex, final WebRequest request) {
		return getResponse(ex.getMessage());
	}

	/**
	 * This is the InvalidRequestException handler
	 *
	 * @param ex      - Instance of {@link InvalidRequestException}
	 * @param request - Instance of {@link WebRequest}
	 * @return Instance of {@link ResponseEntity} which has {@link Response} as
	 *         type
	 */
	@ExceptionHandler(InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Response> handleInvalidRequestException(
			final InvalidRequestException ex, final WebRequest request) {
		return getResponse(ex.getMessage());
	}

	/**
	 * This is the MethodArgumentNotValidException handler
	 *
	 * @param ex      - Instance of {@link MethodArgumentNotValidException}
	 * @param request - Instance of {@link WebRequest}
	 * @return Instance of {@link ResponseEntity} which has {@link Response} as
	 *         type
	 */
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		final List<String> details = new ArrayList<>();
		for (final ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		final Response error = new Response();
		error.setMessage(String.join(", ", details));
		error.setType(Constant.ERROR);
		return ResponseEntity.ok(error);
	}

	/**
	 * This is the FindException handler
	 *
	 * @param ex      - Instance of {@link FindException}
	 * @param request - Instance of {@link WebRequest}
	 * @return Instance of {@link ResponseEntity} which has {@link Response} as
	 *         type
	 */

	@ExceptionHandler(UpdateException.class)
	public final ResponseEntity<Response> handleUpdateException(
			final UpdateException ex, final WebRequest request) {
		return getResponse(ex.getMessage());
	}

}


/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.roche.assignment.commerce.backend.service.ConflictingIncomingDataException;

/**
 * A Controller Advice to handle a {@link ConflictingIncomingDataException} and send a meaningful http status to the
 * rest api caller. These exceptions are raised by the service layer when the incoming data conflicts with the current
 * state of the database.
 *
 * There's an argument for saying this should be 400 instead of 409.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
@ControllerAdvice
public class ConflictingIncomingDataAdvice {
	/**
	 * This method handles a {@link ConflictingIncomingDataException} and returns an HTTP 409 code to the client along
	 * with an exception message
	 *
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(ConflictingIncomingDataException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String handleInvalidDataException(final ConflictingIncomingDataException ex) {
		return ex.getMessage();
	}
}

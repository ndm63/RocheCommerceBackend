/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.roche.assignment.commerce.backend.service.InvalidIncomingDataException;

/**
 * A Controller Advice to handle a {@link InvalidIncomingDataException} and send a meaningful http status to the rest api
 * caller. These exceptions are raised by the service layer, when the incoming data is invalid, for example, a missing
 * mandatory field.
 * 
 * There's an argument for saying this should be 422 instead of 400.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
@ControllerAdvice
public class InvalidIncomingDataAdvice {
	/**
	 * This method handles a {@link InvalidIncomingDataException} and returns an HTTP 400 code to the client along with an
	 * exception message
	 *
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(InvalidIncomingDataException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handleInvalidDataException(final InvalidIncomingDataException ex) {
		return ex.getMessage();
	}
}

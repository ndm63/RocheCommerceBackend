/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A Controller Advice to handle a {@link PropertyValueException} and send a meaningful http status to the rest api
 * caller. These exceptions are raised by Hibernate when it can't persist an entity due to, for example, a missing
 * mandatory field.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
@ControllerAdvice
public class PropertyValueAdvice {
	/**
	 * This method handles a {@link PropertyValueException} and returns an HTTP 400 code to the client along with an
	 * exception message
	 *
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(PropertyValueException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handleInvalidDataException(final PropertyValueException ex) {
		return ex.getMessage();
	}
}

/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.collect.ImmutableMap;
import com.roche.assignment.commerce.backend.service.ProductNotFoundException;

/**
 * A Controller Advice to handle a {@link ProductNotFoundException} and send a meaningful http status to the rest api
 * caller. These exceptions are raised by the service layer when the incoming data conflicts with the current state of
 * the database.
 *
 * There's an argument for saying this should be 400 instead of 409.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
@RestControllerAdvice
public class ProductNotFoundAdvice {
	/**
	 * This method handles a {@link ProductNotFoundException} and returns an HTTP 409 code to the client along with an
	 * exception message
	 *
	 * @param ex The exception to be handled.
	 * @return The body with the error message details
	 */
	@ResponseBody
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	Map<Object, Object> handleInvalidDataException(final ProductNotFoundException ex) {
		return ImmutableMap.builder().put("status", HttpStatus.NOT_FOUND).put("message", ex.getMessage()).build();
	}
}

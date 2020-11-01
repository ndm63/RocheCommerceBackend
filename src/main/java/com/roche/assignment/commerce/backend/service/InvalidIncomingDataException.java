/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

/**
 * Define a service layer exception for invalid incoming data. This is because the incoming data is not valid with
 * respect to the domain model. For example, a mandatory field might be missing.
 * 
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 *
 */
public class InvalidIncomingDataException extends ServiceLayerException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param string The error message that can be presented to clients.
	 */
	public InvalidIncomingDataException(final String message) {
		super(message);
	}

}

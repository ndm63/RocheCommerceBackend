/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

/**
 * Define a service layer exception for conflicting incoming data. This can be because the new data is a duplicate of
 * existing data, even if the existing data has been [soft] deleted. I.e. we can't re-add a previously deleted item.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
public class ConflictingIncomingDataException extends ServiceLayerException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param string The error message that can be presented to clients.
	 */
	public ConflictingIncomingDataException(final String message) {
		super(message);
	}
}

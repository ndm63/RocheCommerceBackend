/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

/**
 * Common abstract base for service layer exceptions to make it easier to add them to the service interfaces.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
public abstract class ServiceLayerException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected ServiceLayerException(final String message) {
		super(message);
	}
}

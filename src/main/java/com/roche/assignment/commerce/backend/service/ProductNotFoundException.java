/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

/**
 * Service layer exception when a product is not found (or has been deleted).
 * 
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param sku
	 */
	public ProductNotFoundException(final String sku) {
		super(String.format("Product with sku '%s' not found", sku));
	}
}

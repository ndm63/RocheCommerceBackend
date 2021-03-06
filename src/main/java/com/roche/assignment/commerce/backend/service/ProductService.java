/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

import java.util.List;

import com.roche.assignment.commerce.backend.dto.ProductDTO;

/**
 * Define the product service. This is overkill because we're just going to have the implementation here as well.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
public interface ProductService {

	/**
	 * Add a new product.
	 *
	 * @param newProduct The product to be added
	 * @return The same product after being added.
	 * @throws ServiceLayerException
	 */
	ProductDTO newProduct(ProductDTO newProduct) throws ServiceLayerException;

	/**
	 * Obtain all products (that haven't been deleted)
	 *
	 * @return The list of products
	 */
	List<ProductDTO> all();

	/**
	 * Update an existing product
	 *
	 * @param sku     The sku of the product to be updated.
	 *
	 * @param product Product to update
	 * @return The updated product
	 */
	ProductDTO update(String sku, ProductDTO product);

	/**
	 * Delete a product. This is a soft delete. so it just gets marked as deleted. Like an update.
	 *
	 * @param sku The sku of the product to be deleted.
	 */
	void delete(String sku);
}

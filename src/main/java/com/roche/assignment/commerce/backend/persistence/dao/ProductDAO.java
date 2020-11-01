/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.persistence.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.roche.assignment.commerce.backend.persistence.model.Product;

/**
 * DAO for the ProductDTO entity.
 *
 * @author Neill McQuillin (created by)
 * @since 22 July 2019 (creation date)
 */
public interface ProductDAO extends JpaRepository<Product, Long> {
	public Optional<Product> findBySku(@Param("sku") final String sku);
}

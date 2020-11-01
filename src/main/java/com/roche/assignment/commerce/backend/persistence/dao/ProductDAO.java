/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.roche.assignment.commerce.backend.persistence.model.Product;

/**
 * DAO for the Product entity.
 *
 * @author Neill McQuillin (created by)
 * @since 22 July 2019 (creation date)
 */
public interface ProductDAO extends JpaRepository<Product, Long> {
	//enable
}

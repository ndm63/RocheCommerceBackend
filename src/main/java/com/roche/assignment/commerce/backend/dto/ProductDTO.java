/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Product DTO.
 *
 * @author Neill McQuillin (created by)
 * @since 22 July 2019 (creation date)
 */
@Builder
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String sku;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private BigDecimal price;
}

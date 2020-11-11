/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Neill McQuillin (created by)
 * @since 11 November 2020 (creation date)
 */
@RequiredArgsConstructor
@Getter
public class OrderItem {
	private final Product product;
	private final Integer quantity;
	private final BigDecimal price;
}

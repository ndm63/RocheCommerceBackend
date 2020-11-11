/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.math.BigDecimal;
import java.util.Collection;

public class BasketTotalCalculator {
	public BigDecimal calculatePrice(final Collection<OrderItem> basket) {
		return basket.stream().map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
				.reduce(BigDecimal.valueOf(0f), BigDecimal::add);
	}
}

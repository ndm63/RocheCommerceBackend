/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.Collection;

public class BasketTotalCalculator {
	public float calculatePrice(final Collection<OrderItem> basket) {
		return basket.stream().map(i -> i.getQuantity() * i.getProduct().getPrice()).reduce(0f, (a, b) -> a + b);
	}
}

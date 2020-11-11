/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.Collection;
import java.util.regex.Pattern;

public class BasketTotalCalculator {
	public float calculatePrice(final Collection<OrderItem> basket) {
		return basket.stream().map(i -> i.getQuantity() * i.getProduct().getPrice()).reduce(0f, (a, b) -> a + b);
	}

	public boolean isValidPromoCode(final String promocode) {
		return Pattern.matches("^\\*.*\\*$", promocode);
	}

	public String getItemDetails(final Product item) {
		return "This is " + item.getName() + ", with description: " + item.getDescription() + " with ingredients: "
				+ item.getIngredients();
	}

}

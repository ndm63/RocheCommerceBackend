/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.Map;
import java.util.regex.Pattern;

public class BasketTotalCalculator {
	public float calculatePrice(Map<Integer, Product> quantityToItemMap) {
		return quantityToItemMap.entrySet().stream().map(e -> e.getKey() * e.getValue().getPrice()).reduce(0f,
				(a, b) -> a + b);
	}

	public boolean isValidPromoCode(String promocode) {
		return Pattern.matches("^\\*.*\\*$", promocode);
	}

	public String getItemDetails(Product item) {
		return "This is " + item.getName() + ", with description: " + item.getDescription() + " with ingredients: "
				+ item.getIngredients();
	}

}
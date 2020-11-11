/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Product {
	private final Float price;
	private final String name;
	private final String description;
	private final List<String> ingredients;
	

	public String getItemDetails(final Product item) {
		return "This is " + item.getName() + ", with description: " + item.getDescription() + " with ingredients: "
				+ item.getIngredients();
	}
}
/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.List;

class Item {
	private final Float price;
	private final String name;
	private final String description;

	private final List<String> ingredients;

	public Item(Float price, String name, String description, List<String> ingredients) {
		this.price = price;
		this.name = name;
		this.description = description;
		this.ingredients = ingredients;
	}

	public Float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getIngredients() {
		return ingredients;
	}
}
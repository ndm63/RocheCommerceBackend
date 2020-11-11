/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

/**
 * Test methods in Product
 *
 * @author Neill McQuillin (created by)
 * @since 11 November 2020 (creation date)
 */
public class ProductTest {
	@Test
	void testGetItemDetails_valid_textCorrect() {
		final Product objectUnderTest = new Product("apple", "delicious green apple",
				Lists.newArrayList("skin", "pips", "flesh"));

		final String output = objectUnderTest.getProductDetails(objectUnderTest);

		Assertions.assertThat(output).isEqualTo(
				"This is apple, with description: delicious green apple with ingredients: [skin, pips, flesh]");
	}
}

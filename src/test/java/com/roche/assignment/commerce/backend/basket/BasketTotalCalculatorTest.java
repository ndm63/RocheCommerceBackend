/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.collect.Lists;

/**
 * Test the basket total price calculator
 *
 * @author Neill McQuillin (created by)
 * @since 11 November 2020 (creation date)
 */
public class BasketTotalCalculatorTest {
	@Test
	void testCalculatePrice_5oranges5apples_totalCorrect() {
		final Product apple = Mockito.mock(Product.class);
		final Product orange = Mockito.mock(Product.class);

		Mockito.when(apple.getPrice()).thenReturn(0.34f);
		Mockito.when(orange.getPrice()).thenReturn(0.12f);

		final OrderItem apples = new OrderItem(apple, 5);
		final OrderItem oranges = new OrderItem(orange, 5);
		final Collection<OrderItem> input = Lists.newArrayList(apples, oranges);

		final BasketTotalCalculator objectUnderTest = new BasketTotalCalculator();

		final float output = objectUnderTest.calculatePrice(input);

		Assertions.assertThat(output).isEqualTo(2.3f);
	}
}

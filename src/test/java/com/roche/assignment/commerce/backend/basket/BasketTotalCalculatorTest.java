/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.math.BigDecimal;
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

		final OrderItem apples = new OrderItem(apple, 5, BigDecimal.valueOf(0.34));
		final OrderItem oranges = new OrderItem(orange, 5, BigDecimal.valueOf(0.12));
		final Collection<OrderItem> input = Lists.newArrayList(apples, oranges);

		final BasketTotalCalculator objectUnderTest = new BasketTotalCalculator();

		final BigDecimal output = objectUnderTest.calculatePrice(input);

		Assertions.assertThat(output.compareTo(BigDecimal.valueOf(2.30))).isEqualTo(0);
	}
}

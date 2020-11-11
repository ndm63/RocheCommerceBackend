/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.function.Predicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the promo code validator
 *
 * @author Neill McQuillin (created by)
 * @since 11 November 2020 (creation date)
 */
public class PromoCodeValidatorTest {
	@Test
	void testTest_validRealistic_true() {
		final Predicate<String> objectUnderTest = new PromoCodeValidator();

		final boolean output = objectUnderTest.test("*PC-12345-*");

		Assertions.assertThat(output).isTrue();
	}

	@Test
	void testTest_validDegenerate_true() {
		final Predicate<String> objectUnderTest = new PromoCodeValidator();

		final boolean output = objectUnderTest.test("*PC*");

		Assertions.assertThat(output).isTrue();
	}

	@Test
	void testTest_invalidStart_false() {
		final Predicate<String> objectUnderTest = new PromoCodeValidator();

		final boolean output = objectUnderTest.test("PC-12345-*");

		Assertions.assertThat(output).isFalse();
	}

	@Test
	void testTest_invalidEnd_false() {
		final Predicate<String> objectUnderTest = new PromoCodeValidator();

		final boolean output = objectUnderTest.test("*PC-12345-");

		Assertions.assertThat(output).isFalse();
	}
}

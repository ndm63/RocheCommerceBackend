/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.basket;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Predicate for determining whether a promo code is valid
 *
 * @author Neill McQuillin (created by)
 * @since 11 November 2020 (creation date)
 */
public class PromoCodeValidator implements Predicate<String> {
	@Override
	public boolean test(final String promocode) {
		return Pattern.matches("^\\*PC.*\\*$", promocode);
	}
}

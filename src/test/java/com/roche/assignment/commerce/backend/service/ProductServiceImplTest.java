/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.roche.assignment.commerce.backend.dto.ProductDTO;
import com.roche.assignment.commerce.backend.persistence.dao.ProductDAO;

/**
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 *
 */
public class ProductServiceImplTest {

	@Test
	void testNewProduct_validUnique_created() throws ServiceLayerException {
		final ProductDAO dao = Mockito.mock(ProductDAO.class);
		Mockito.when(dao.save(ArgumentMatchers.any())).then(i -> i.getArgument(0));

		final ProductService objectUnderTest = new ProductServiceImpl(dao);

		final ProductDTO input = ProductDTO.builder().sku("sku").name("widget 1").price(BigDecimal.valueOf(1.23))
				.build();

		final ProductDTO output = objectUnderTest.newProduct(input);

		Assertions.assertNotNull(output);
	}

}

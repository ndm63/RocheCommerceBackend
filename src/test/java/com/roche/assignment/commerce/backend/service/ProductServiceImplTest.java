/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.common.collect.Lists;
import com.roche.assignment.commerce.backend.dto.ProductDTO;
import com.roche.assignment.commerce.backend.persistence.dao.ProductDAO;
import com.roche.assignment.commerce.backend.persistence.model.Product;

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

	@Test
	void testNewProduct_validNotUnique_exception() {
		final ProductDAO dao = Mockito.mock(ProductDAO.class);
		final DataIntegrityViolationException exc = Mockito.mock(DataIntegrityViolationException.class);
		final Exception cause = Mockito.mock(PropertyValueException.class);
		Mockito.when(dao.save(ArgumentMatchers.any())).thenThrow(exc);
		Mockito.when(exc.getCause()).thenReturn(cause);

		final ProductService objectUnderTest = new ProductServiceImpl(dao);

		Assertions.assertThrows(InvalidIncomingDataException.class, () -> {
			final ProductDTO input = ProductDTO.builder().sku("sku").name("widget 1").price(BigDecimal.valueOf(1.23))
					.build();
			objectUnderTest.newProduct(input);
		});
	}

	@Test
	void testNewProduct_notValid_exception() {
		final ProductDAO dao = Mockito.mock(ProductDAO.class);
		final DataIntegrityViolationException exc = Mockito.mock(DataIntegrityViolationException.class);
		final Exception cause = Mockito.mock(ConstraintViolationException.class);
		Mockito.when(dao.save(ArgumentMatchers.any())).thenThrow(exc);
		Mockito.when(exc.getCause()).thenReturn(cause);

		final ProductService objectUnderTest = new ProductServiceImpl(dao);

		Assertions.assertThrows(ConflictingIncomingDataException.class, () -> {
			final ProductDTO input = ProductDTO.builder().sku("sku").name("widget 1").price(BigDecimal.valueOf(1.23))
					.build();
			objectUnderTest.newProduct(input);
		});
	}

	@Test
	void testAll_someDeleted_listOfNotDeleted() {
		final ProductDAO dao = Mockito.mock(ProductDAO.class);
		Product product2 = Product.builder().sku("sku2").name("name2").price(BigDecimal.valueOf(2.00)).build();
		List<Product> products = Lists.newArrayList(product2);
		Mockito.when(dao.findAllNotDeleted()).thenReturn(products);

		final ProductService objectUnderTest = new ProductServiceImpl(dao);

		final List<ProductDTO> output = objectUnderTest.all();

		Assertions.assertNotNull(output);
		Assertions.assertEquals(1, output.size());
	}

}

/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.roche.assignment.commerce.backend.dto.ProductDTO;
import com.roche.assignment.commerce.backend.persistence.dao.ProductDAO;
import com.roche.assignment.commerce.backend.persistence.model.Product;

import lombok.RequiredArgsConstructor;

/**
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 *
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductDAO dao;

	@Override
	public ProductDTO newProduct(final ProductDTO newProduct) throws ServiceLayerException {
		try {
			final Product entity = mapToEntity(newProduct);
			final Product saved = dao.save(entity);
			return mapToDto(saved);
		} catch (DataIntegrityViolationException exc ) {
			// Translate persistence layer exceptions to service layer exceptions
			if (exc.getCause() instanceof SQLIntegrityConstraintViolationException
					|| exc.getCause() instanceof  ConstraintViolationException) {
				throw new ConflictingIncomingDataException("The new product violated constraints with existing data");
			} else if (exc.getCause() instanceof PropertyValueException) {
				throw new InvalidIncomingDataException("The new product definition is invalid");
			} else {
				throw new ResponseStatusException(
				          HttpStatus.INTERNAL_SERVER_ERROR, "Server failed to add new product for unknown reason");				
			}
		}
	}

	@Override
	public List<ProductDTO> all() {
		final List<Product> entities = dao.findAll();
		return entities.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	/**
	 * Map from a DTO to an entity. Currently just using Lombok builder. This will eventually be moved to a mapper from
	 * a mapping framework.
	 *
	 * @param dto The DTO to be mapped
	 * @return The resulting entity
	 */
	private Product mapToEntity(final ProductDTO dto) {
		return Product.builder().sku(dto.getSku()).name(dto.getName()).price(dto.getPrice()).build();
	}

	/**
	 * Map from an entity to a DTO. Currently just using Lombok builder. This will eventually be moved to a mapper from
	 * a mapping framework
	 *
	 * @param entity The entity to be mapped
	 * @return The resulting DTO.
	 */
	private ProductDTO mapToDto(final Product entity) {
		return ProductDTO.builder().sku(entity.getSku()).name(entity.getName()).price(entity.getPrice()).build();
	}

}

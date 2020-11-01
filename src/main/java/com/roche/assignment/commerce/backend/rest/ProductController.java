/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.roche.assignment.commerce.backend.dto.ProductDTO;
import com.roche.assignment.commerce.backend.persistence.dao.ProductDAO;
import com.roche.assignment.commerce.backend.persistence.model.Product;

import lombok.RequiredArgsConstructor;

/**
 * @author Neill McQuillin (created by)
 * @since 31 October 2020 (creation date)
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
	private final ProductDAO dao;

	@PostMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> newProduct(@RequestBody final ProductDTO newProduct) {
//	    ProductDTO prd      = ProductMapper.DtoToEntity(newProduct);
//      ProductDTO addedprd = productService.save(prd);
		final Product entity = Product.builder().sku(newProduct.getSku()).name(newProduct.getName())
				.price(newProduct.getPrice()).build();
		final Product saved = dao.save(entity);
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sku}")
				.buildAndExpand(saved.getSku()).toUri();
		final ProductDTO savedDto = ProductDTO.builder().sku(saved.getSku()).name(saved.getName())
				.price(saved.getPrice()).build();
		return ResponseEntity.created(location).body(savedDto);
	}

	@GetMapping("/products")
	@ResponseBody
	public ResponseEntity<List<Product>> all() {
		return ResponseEntity.ok().body(dao.findAll());
	}
}

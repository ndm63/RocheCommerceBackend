/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.persistence.rest;

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

	@PostMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> newProduct(@RequestBody final Product newProduct) {
//	    Product prd      = ProductMapper.DtoToEntity(newProduct);
//      Product addedprd = productService.save(prd);
		final Product saved = dao.save(newProduct);
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sku}")
				.buildAndExpand(saved.getSku()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/products")
	@ResponseBody
	public ResponseEntity<List<Product>> all() {
//		final Product newProduct = new Product();
//		newProduct.setSku(UUID.randomUUID().toString());
//		newProduct.setName("tyres");
//		newProduct.setPrice(BigDecimal.valueOf(2.34));
//		dao.save(newProduct);
		return ResponseEntity.ok().body(dao.findAll());
	}
}

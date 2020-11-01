/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.collect.ImmutableMap;
import com.roche.assignment.commerce.backend.dto.ProductDTO;
import com.roche.assignment.commerce.backend.service.ProductService;
import com.roche.assignment.commerce.backend.service.ServiceLayerException;

import lombok.RequiredArgsConstructor;

/**
 * @author Neill McQuillin (created by)
 * @since 31 October 2020 (creation date)
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;

	@PostMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> newProduct(@RequestBody final ProductDTO newProduct)
			throws ServiceLayerException {
		final ProductDTO saved = service.newProduct(newProduct);
		// The link is a fib because we don't actually need to implement the method to get an individual product
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sku}")
				.buildAndExpand(saved.getSku()).toUri();
		return ResponseEntity.created(location).body(saved);
	}

	@GetMapping("/products")
	@ResponseBody
	public ResponseEntity<List<ProductDTO>> all() {
		final List<ProductDTO> items = service.all();
		return ResponseEntity.ok().body(items);
	}

	@PutMapping(value = "/products/{sku}")
	public ResponseEntity<ProductDTO> update(@PathVariable("sku") final String sku,
			@RequestBody final ProductDTO product) {
		final ProductDTO updated = service.update(sku, product);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/products/{sku}")
	public ResponseEntity<Map<Object, Object>> delete(@PathVariable("sku") final String sku) {
		service.delete(sku);
		Map<Object, Object> output = ImmutableMap.builder().put("status", HttpStatus.OK).put("sku", sku).build();
		return ResponseEntity.ok(output);
	}
}

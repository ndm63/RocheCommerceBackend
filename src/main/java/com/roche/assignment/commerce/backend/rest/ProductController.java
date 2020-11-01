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
import com.roche.assignment.commerce.backend.service.ProductService;

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
	public ResponseEntity<ProductDTO> newProduct(@RequestBody final ProductDTO newProduct) {
		final ProductDTO saved = service.newProduct(newProduct);
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
}

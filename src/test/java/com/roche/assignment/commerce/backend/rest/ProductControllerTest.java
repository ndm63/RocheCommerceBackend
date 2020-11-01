/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import java.math.BigDecimal;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.hibernate.PropertyValueException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.roche.assignment.commerce.backend.service.ConflictingIncomingDataException;
import com.roche.assignment.commerce.backend.service.InvalidIncomingDataException;
import com.roche.assignment.commerce.backend.service.ProductNotFoundException;
import com.roche.assignment.commerce.backend.service.ProductService;
import com.roche.assignment.commerce.backend.service.ServiceLayerException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { ProductControllerTest.SpringConfig.class })
public class ProductControllerTest {
	@Configuration
	@ComponentScan(basePackages = { "com.roche.assignment.commerce.backend.rest" })
	@EnableWebMvc
	public static class SpringConfig {
		@Bean(value = "restTemplate")
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@Test
	public void testNewProduct_noBody_400badRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		Mockito.verifyNoInteractions(service);
	}

	@Test
	public void testNewProduct_validJsonBodyNotExists_201created() throws Exception {
		Mockito.when(service.newProduct(ArgumentMatchers.any())).then(i -> i.getArgument(0));

		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		// Why does this need to be added explicitly in Mockito 3 ?
		Mockito.verify(service).newProduct(ArgumentMatchers.any());
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void testNewProduct_noSku_400badRequest() throws Exception {
		Mockito.when(service.newProduct(ArgumentMatchers.any()))
				.thenThrow(new InvalidIncomingDataException("missing field sku"));

		final JSONObject postBody = new JSONObject();
		UUID.randomUUID().toString();
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		// Why does this need to be added explicitly inMockito 3 ?
		Mockito.verify(service).newProduct(ArgumentMatchers.any());
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void testNewProduct_addProductTwice_409conflict() throws Exception {
		Mockito.when(service.newProduct(ArgumentMatchers.any())).then(i -> i.getArgument(0))
				.thenThrow(new ConflictingIncomingDataException("duplicate"));

		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isConflict());

		Mockito.verify(service, Mockito.times(2)).newProduct(ArgumentMatchers.any());
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void testUpdate_exists_200ok() throws Exception {
		Mockito.when(service.newProduct(ArgumentMatchers.any())).then(i -> i.getArgument(0));
		Mockito.when(service.update(ArgumentMatchers.any(), ArgumentMatchers.any())).then(i -> i.getArgument(1));

		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		// Change the price
		postBody.put("price", BigDecimal.valueOf(3.99));

		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/v1/products/{sku}", sku).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(3.99));

		Mockito.verify(service).newProduct(ArgumentMatchers.any());
		Mockito.verify(service).update(ArgumentMatchers.any(), ArgumentMatchers.any());
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void testUpdate_notExists_404notFound() throws Exception {
		final String sku = "garbage";
		Mockito.when(service.update(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenThrow(new ProductNotFoundException(sku));

		final JSONObject postBody = new JSONObject();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/v1/products/{sku}", sku).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}

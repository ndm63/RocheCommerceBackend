/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.PropertyValueException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.roche.assignment.commerce.backend.service.ProductService;

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
		Mockito.when(service.newProduct(Mockito.any())).then(i -> i.getArgument(0));

		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		Mockito.verify(service).newProduct(Mockito.any()); // Why does this need to be added explicitly in Mockito 3 ?
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void testNewProduct_noSku_400badRequest() throws Exception {
		Mockito.when(service.newProduct(Mockito.any())).thenThrow(
				new PropertyValueException("not-null property references a null or transient value", "Product", "sku"));

		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(postBody.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		Mockito.verify(service).newProduct(Mockito.any()); // Why does this need to be added explicitly in Mockito 3 ?
		Mockito.verifyNoMoreInteractions(service);
	}

}

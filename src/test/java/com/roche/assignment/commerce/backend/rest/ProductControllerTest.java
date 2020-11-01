/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.rest;

import org.junit.jupiter.api.Test;
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

import com.roche.assignment.commerce.backend.persistence.dao.ProductDAO;

/**
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { ProductControllerTest.SpringConfig.class })
public class ProductControllerTest {
	@Configuration
	@ComponentScan(basePackages = {
			"com.roche.assignment.commerce.backend.rest" }, useDefaultFilters = true, includeFilters = {
					@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { ProductController.class }) })
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
	private ProductDAO dao;

	@Test
	public void testNewProduct_noBody_400badRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}

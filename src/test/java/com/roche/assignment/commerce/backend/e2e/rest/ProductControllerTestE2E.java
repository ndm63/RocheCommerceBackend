/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.e2e.rest;

import java.math.BigDecimal;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * End to end test for the ProductDTO REST API controller.
 *
 * This is black-box testing. I.e. we assume that we don't have access to the underlying database. Normally we'd write
 * such tests with access to the database. In this case we'll need to contrive the tests to check the expected behaviour
 * without being able to reset the database between tests or performing the tests in a specific order.
 *
 * @author Neill McQuillin (created by)
 * @since 01 November 2020 (creation date)
 */
@WebMvcTest
@AutoConfigureWebClient
@ContextConfiguration(classes = { ProductControllerTestE2E.SpringConfig.class })
@TestPropertySource(locations = "classpath:e2e.properties")
public class ProductControllerTestE2E {
	@Configuration
	@EnableWebMvc
	public static class SpringConfig {
		@Bean(value = "restTemplate")
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	/** The default REST API url. */
	@Value(value = "${app.ws.url.base}")
	private String defaulTestUrlBase;

	/** The REST API url. */
	private String restUrlBase;

	@BeforeEach
	void beforeEach() {
		restUrlBase = System.getProperty("app.ws.url.base", defaulTestUrlBase) + "/api/v1/products";
		RestAssured.baseURI = restUrlBase;
	}

	@Test
	public void testNewProduct_noBody_400badRequest() {
		RestAssured.given().contentType(ContentType.JSON).when().post().then().statusCode(400);
	}

	@Test
	public void testNewProduct_validJsonBodyNotExists_201created() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Check that we find the created item in the list of all products
		RestAssured.when().get().then().statusCode(200).body("sku", Matchers.hasItem(sku));
	}

	@Test
	public void testNewProduct_noSku_400badRequest() throws JSONException {
		final JSONObject postBody = new JSONObject();
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then()
				.statusCode(400);
	}
}

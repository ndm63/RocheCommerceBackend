/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.e2e.rest;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.collect.Lists;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * End to end test for the Product REST API controller.
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

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

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
		final RestTemplate restTemplate = restTemplateBuilder.build();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		final HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);
		final ResponseEntity<String> reply = restTemplate.postForEntity(restUrlBase, entity, String.class);

		Assertions.assertThat(reply).isNotNull();
		Assertions.assertThat(reply.getStatusCodeValue()).isEqualTo(200);
		final String body = reply.getBody();
		System.out.println(body);
		Assertions.assertThat(body).isNotNull().matches("^(\\d+\\.\\d+\\.\\d+(\\.\\d+|-SNAPSHOT)|build-\\d+)$");
	}

	@Test
	public void testNewProduct2_noBody_400badRequest() {
		RestAssured.given().contentType(ContentType.JSON).when().post().then().statusCode(400);
	}

}

/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.e2e.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

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
@Testcontainers
public class ProductControllerTestE2E {
	@Container
	static final GenericContainer<?> APP = new GenericContainer<>(
			DockerImageName.parse(System.getProperty("dockerImagePullPath"))).withExposedPorts(8080)
					.withLogConsumer(new ToStringConsumer() {
						@Override
						public void accept(final OutputFrame outputFrame) {
							if (outputFrame.getBytes() != null) {
								try {
									System.out.write(outputFrame.getBytes());
								} catch (final IOException e) {
									throw new RuntimeException(e);
								}
							}
						}
					}).waitingFor(Wait.forHttp("/api/version"));

	@BeforeEach
	void beforeEach() {
		RestAssured.baseURI = String.format("http://%s:%d/api/v1/products", APP.getContainerIpAddress(),
				APP.getFirstMappedPort());
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
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(400)
				.body("status", Matchers.equalTo("BAD_REQUEST"))
				.body("message", Matchers.matchesRegex(".*definition is invalid.*"));
	}

	@Test
	public void testNewProduct_addProductTwice_409conflict() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Send the same again
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(409)
				.body("status", Matchers.equalTo("CONFLICT")).body("message", Matchers.matchesRegex(".*constraint.*"));
	}

	@Test
	public void testUpdate_exists_200ok() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Change the price
		postBody.put("price", BigDecimal.valueOf(3.99));
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().put("/{sku}", sku).then()
				.statusCode(200).body("price", Matchers.equalTo(3.99f));
	}

	@Test
	public void testUpdate_notExists_404notFound() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Change the price
		postBody.put("price", BigDecimal.valueOf(3.99));
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().put("/{sku}", "garbage")
				.then().statusCode(404).body("status", Matchers.equalTo("NOT_FOUND"))
				.body("message", Matchers.matchesRegex("Product.*not found"));
	}

	@Test
	public void testDelete_existsNotAlreadyDeleted_200ok() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Check that the delete returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().delete("/{sku}", sku).then()
				.statusCode(200).body("status", Matchers.equalTo("OK")).body("sku", Matchers.equalTo(sku));
	}

	@Test
	public void testDelete_notExists_404notFound() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().delete("/{sku}", "garbage")
				.then().statusCode(404).body("status", Matchers.equalTo("NOT_FOUND"))
				.body("message", Matchers.matchesRegex("Product.*not found"));
	}

	@Test
	public void testDelete_existsAlreadyDeleted_404notFound() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Check that the delete returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().delete("/{sku}", sku).then()
				.statusCode(200).body("status", Matchers.equalTo("OK")).body("sku", Matchers.equalTo(sku));
		// Check that the delete returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().delete("/{sku}", sku).then()
				.statusCode(404).body("status", Matchers.equalTo("NOT_FOUND"))
				.body("message", Matchers.matchesRegex("Product.*not found"));
	}

	@Test
	public void testNewProduct_previously_409conflict() throws JSONException {
		final JSONObject postBody = new JSONObject();
		final String sku = UUID.randomUUID().toString();
		postBody.put("sku", sku);
		postBody.put("name", "Widget 1");
		postBody.put("price", BigDecimal.valueOf(1.23));

		// Check that the creation post returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(201)
				.body("sku", Matchers.equalTo(sku));
		// Check that the delete returned correctly
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().delete("/{sku}", sku).then()
				.statusCode(200).body("status", Matchers.equalTo("OK")).body("sku", Matchers.equalTo(sku));
		// Send the same again
		RestAssured.given().contentType(ContentType.JSON).body(postBody.toString()).when().post().then().statusCode(409)
				.body("status", Matchers.equalTo("CONFLICT")).body("message", Matchers.matchesRegex(".*constraint.*"));
	}

}

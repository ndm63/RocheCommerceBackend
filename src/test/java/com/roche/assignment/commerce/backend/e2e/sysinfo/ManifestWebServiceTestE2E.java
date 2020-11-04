/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.e2e.sysinfo;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.json.JSONException;
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

/**
 * Test the Manifest (version) web service (REST service).
 *
 * @author Neill McQuillin (created by)
 * @since 10 October 2019 (creation date)
 */
@Testcontainers
public class ManifestWebServiceTestE2E {
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
		RestAssured.baseURI = String.format("http://%s:%d", APP.getContainerIpAddress(), APP.getFirstMappedPort());
	}

	@Test
	public void testW6Version_none_expectedString() {
		RestAssured.when().get("/api/version").then().statusCode(200)
				.body(Matchers.matchesRegex("^(\\d+\\.\\d+\\.\\d+(\\.\\d+|-SNAPSHOT)|build-\\d+)$"));
	}

	@Test
	public void testW7Info_none_expectedJson() {
		RestAssured.when().get("/api/info").then().statusCode(200).body("version", Matchers.matchesRegex("^.*$"))
				.body("buildTime", Matchers.matchesRegex("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*$"));
	}

	@Test
	public void testW8Manifest_none_expectedJson() throws JSONException {
		RestAssured.when().get("/api/manifest").then().statusCode(200).body("version", Matchers.matchesRegex("^.*$"))
				.body("buildTime", Matchers.matchesRegex("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*$"))
				.body("createdBy", Matchers.matchesRegex("^.*$"));
	}
}

/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.e2e.sysinfo;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Test the Manifest (version) web service (REST service).
 *
 * @author Neill McQuillin (created by)
 * @since 10 October 2019 (creation date)
 */
@WebMvcTest
@AutoConfigureWebClient
@ContextConfiguration(classes = { ManifestWebServiceTestE2E.SpringConfig.class })
@TestPropertySource(locations = "classpath:e2e.properties")
public class ManifestWebServiceTestE2E {
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
		restUrlBase = System.getProperty("app.ws.url.base", defaulTestUrlBase) + "/api";
	}

	@Test
	public void testW6Version_none_expectedString() {
		final String url = restUrlBase + "/version";
		final RestTemplate restTemplate = restTemplateBuilder.build();
		final ResponseEntity<String> reply = restTemplate.getForEntity(url, String.class);

		Assertions.assertThat(reply).isNotNull();
		Assertions.assertThat(reply.getStatusCodeValue()).isEqualTo(200);
		final String body = reply.getBody();
		System.out.println(body);
		Assertions.assertThat(body).isNotNull().matches("^(\\d+\\.\\d+\\.\\d+(\\.\\d+|-SNAPSHOT)|build-\\d+)$");
	}

	@Test
	public void testW7Info_none_expectedJson() throws JSONException {
		final String url = restUrlBase + "/info";
		final RestTemplate restTemplate = restTemplateBuilder.build();
		final ResponseEntity<String> reply = restTemplate.getForEntity(url, String.class);

		Assertions.assertThat(reply).isNotNull();
		Assertions.assertThat(reply.getStatusCodeValue()).isEqualTo(200);
		final String body = reply.getBody();
		System.out.println(body);
		Assertions.assertThat(body).isNotNull();
		final String expectedProcessingStatusJson = "{\"version\":\"version\",\"buildTime\":\"buildTime\"}";
		JSONAssert.assertEquals(expectedProcessingStatusJson, body,
				new CustomComparator(JSONCompareMode.STRICT,
						new Customization("version", new RegularExpressionValueMatcher<>("^.*$")),
						new Customization("buildTime",
								new RegularExpressionValueMatcher<>("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*$"))));
	}

	@Test
	public void testW8Manifest_none_expectedJson() throws JSONException {
		final String url = restUrlBase + "/manifest";
		final RestTemplate restTemplate = restTemplateBuilder.build();
		final ResponseEntity<String> reply = restTemplate.getForEntity(url, String.class);

		Assertions.assertThat(reply).isNotNull();
		Assertions.assertThat(reply.getStatusCodeValue()).isEqualTo(200);
		final String body = reply.getBody();
		System.out.println(body);
		Assertions.assertThat(body).isNotNull();
		final String expectedProcessingStatusJson = "{\"version\":\"version\",\"buildTime\":\"buildTime\",\"implementationTitle\":\"commerce-backend\",\"scmBranch\":\"scmBranch\",\"scmRevision\":\"scmRevision\",\"createdBy\":\"createdBy\",\"buildJdk\":\"buildJdk\"}";
		JSONAssert.assertEquals(expectedProcessingStatusJson, body,
				new CustomComparator(JSONCompareMode.STRICT,
						new Customization("version", new RegularExpressionValueMatcher<>("^.*$")),
						new Customization("buildTime",
								new RegularExpressionValueMatcher<>("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*$")),
						new Customization("scmBranch", new RegularExpressionValueMatcher<>("^.*$")),
						new Customization("scmRevision", new RegularExpressionValueMatcher<>("^.*$")),
						new Customization("createdBy", new RegularExpressionValueMatcher<>("^.*$")),
						new Customization("buildJdk", new RegularExpressionValueMatcher<>("^.*$"))));
	}
}

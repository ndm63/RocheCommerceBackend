/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.util.jar.Attributes.Name;

import javax.servlet.ServletContext;

import org.assertj.core.api.Assertions;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@WebMvcTest(ManifestRestController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { ManifestRestControllerTest.SpringConfig.class })
public class ManifestRestControllerTest {
	@Configuration
	@ComponentScan(basePackages = { "com.roche.assignment.commerce.backend.sysinfo" }, useDefaultFilters = true, includeFilters = {
			@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { ManifestRestController.class }) })
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
	private ServletContext servletContext;

	@MockBean
	private ManifestProvider manifestReader;

	@Test
	public void testVersion_none_OK() throws Exception {
		Mockito.when(manifestReader.getManifestMainAttributesValue(Name.IMPLEMENTATION_VERSION)).thenReturn("version1");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/version")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("version1"));
	}

	@Test
	public void testInfo_none_OK() throws Exception {
		final ManifestBasicBuildInfo manifestBasicBuildInfo = new ManifestBasicBuildInfo("version1", "just now");
		Mockito.when(manifestReader.getManifestBasicBuildInfo()).thenReturn(manifestBasicBuildInfo);

		final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/info"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content()
						.json("{\"version\":\"version1\",\"buildTime\":\"just now\"}", true))
				.andReturn();
		final MockHttpServletResponse response = result.getResponse();
		System.out.println(response.getContentType());
		System.out.println(response.getContentAsString());
	}

	@Test
	public void testManifest_none_OK() throws Exception {
		final ManifestDetails manifestDetails = new ManifestDetails("1.0.0-SNAPSHOT", "2020-01-29 13:16",
				"commerce-backend", "master", "a721f8835e5d6fe2e9cab7cc30a31d8aff74639a", "Horatio", "1.8.0_211");
		Mockito.when(manifestReader.getManifestDetails()).thenReturn(manifestDetails);

		final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/manifest"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(
						"{\"version\":\"1.0.0-SNAPSHOT\",\"buildTime\":\"2020-01-29 13:16\",\"implementationTitle\":\"commerce-backend\",\"scmBranch\":\"master\",\"scmRevision\":\"a721f8835e5d6fe2e9cab7cc30a31d8aff74639a\",\"createdBy\":\"Horatio\",\"buildJdk\":\"1.8.0_211\"}",
						true))
				.andReturn();
		final MockHttpServletResponse response = result.getResponse();
		System.out.println(response.getContentType());
		System.out.println(response.getContentAsString());
	}

	/**
	 * This test is just to up the coverage. Normally headers will never be null. However, if we don't check for null,
	 * Sonar will complain. If we don't exercise the route with headers null, Sonar will compalin. So we can't win. This
	 * test solves the issue.
	 */
	@Test
	public void testVersion_headersNull_OK() {
		final ManifestRestController objectUnderTest = new ManifestRestController(manifestReader);
		final ResponseEntity<String> output = objectUnderTest.getVersion(null);
		Assertions.assertThat(output).isNotNull();
	}
}

/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes.Name;

import javax.servlet.ServletContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Neill McQuillin (created by)
 * @since 30 January 2020 (creation date)
 */
public class ManifestReaderTest {
	@Test
	public void testGetManifestBasicBuildInfo_validManifest_basicBuildInfoReturned() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final ManifestBasicBuildInfo output = objectUnderTest.getManifestBasicBuildInfo();

			Assertions.assertThat(output).isNotNull();
			Assertions.assertThat(output.getBuildTime()).isNotNull().isEqualTo("2020-01-30 14:19");
			Assertions.assertThat(output.getVersion()).isNotNull().isEqualTo("1.0.0-SNAPSHOT");
		}
	}

	@Test
	public void testGetManifestBasicBuildInfo_missingManifest_null() {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(null);

		final ManifestReader objectUnderTest = new ManifestReader(servletContext);

		final ManifestBasicBuildInfo output = objectUnderTest.getManifestBasicBuildInfo();

		Assertions.assertThat(output).isNull();
	}

	@Test
	public void testGetManifestBasicBuildInfo_ioException_null() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());
		try (final InputStream inputStream = Mockito.mock(InputStream.class);) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);
			Mockito.when(inputStream.read(ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
					.thenThrow(IOException.class);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final ManifestBasicBuildInfo output = objectUnderTest.getManifestBasicBuildInfo();

			Assertions.assertThat(output).isNull();
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_enum_string() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final String output = objectUnderTest.getManifestMainAttributesValue(Name.IMPLEMENTATION_TITLE);

			Assertions.assertThat(output).isNotNull().isEqualTo("commerce-backend");
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_enumWithNoEntry_null() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final String output = objectUnderTest.getManifestMainAttributesValue(Name.CLASS_PATH);

			Assertions.assertThat(output).isNull();
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_string_string() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final String output = objectUnderTest.getManifestMainAttributesValue("Build-Time");

			Assertions.assertThat(output).isNotNull().isEqualTo("2020-01-30 14:19");
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_garbageString_null() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final String output = objectUnderTest.getManifestMainAttributesValue("garbage");

			Assertions.assertThat(output).isNull();
		}
	}

	@Test
	public void testGetManifestDetails_validManifest_manifestDetailsReturned() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			final ManifestReader objectUnderTest = new ManifestReader(servletContext);

			final ManifestDetails output = objectUnderTest.getManifestDetails();

			Assertions.assertThat(output).isNotNull();
			Assertions.assertThat(output.getBuildTime()).isNotNull().isEqualTo("2020-01-30 14:19");
			Assertions.assertThat(output.getVersion()).isNotNull().isEqualTo("1.0.0-SNAPSHOT");
		}
	}

}

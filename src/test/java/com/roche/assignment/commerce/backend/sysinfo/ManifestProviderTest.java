/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Function;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Lists;

/**
 * @author Neill McQuillin (created by)
 * @since 30 January 2020 (creation date)
 */
public class ManifestProviderTest {
	@Test
	public void testGetManifestBasicBuildInfo_validManifest_basicBuildInfoReturned() throws IOException {
		ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
		Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			List<URL> urlList = Lists.newArrayList(new URL("file://manifest/MANIFEST.MF"));
			Enumeration<URL> urlEnum = Collections.enumeration(urlList);
			Mockito.when(urlEnumSupplier.get()).thenReturn(urlEnum);
			Mockito.when(manifestReader.apply(Mockito.any())).thenReturn(new Manifest(inputStream));
			
			final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

			final ManifestBasicBuildInfo output = objectUnderTest.getManifestBasicBuildInfo();

			Assertions.assertThat(output).isNotNull();
			Assertions.assertThat(output.getBuildTime()).isNotNull().isEqualTo("2020-01-30 14:19");
			Assertions.assertThat(output.getVersion()).isNotNull().isEqualTo("1.0.0-SNAPSHOT");
		}
	}

	@Test
	public void testGetManifestBasicBuildInfo_missingManifest_null() {
		ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
		Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );

		List<URL> urlList = Lists.newArrayList();
		Enumeration<URL> urlEnum = Collections.enumeration(urlList);
		Mockito.when(urlEnumSupplier.get()).thenReturn(urlEnum);

		final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

		final ManifestBasicBuildInfo output = objectUnderTest.getManifestBasicBuildInfo();

		Assertions.assertThat(output).isNull();
	}

	@Test
	public void testGetManifestMainAttributesValue_enum_string() throws IOException {
		ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
		Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			List<URL> urlList = Lists.newArrayList(new URL("file://manifest/MANIFEST.MF"));
			Enumeration<URL> urlEnum = Collections.enumeration(urlList);
			Mockito.when(urlEnumSupplier.get()).thenReturn(urlEnum);
			Mockito.when(manifestReader.apply(Mockito.any())).thenReturn(new Manifest(inputStream));
			
			final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

			final String output = objectUnderTest.getManifestMainAttributesValue(Name.IMPLEMENTATION_TITLE);

			Assertions.assertThat(output).isNotNull().isEqualTo("commerce-backend");
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_enumWithNoEntry_null() throws IOException {
		ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
		Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			List<URL> urlList = Lists.newArrayList(new URL("file://manifest/MANIFEST.MF"));
			Enumeration<URL> urlEnum = Collections.enumeration(urlList);
			Mockito.when(urlEnumSupplier.get()).thenReturn(urlEnum);
			Mockito.when(manifestReader.apply(Mockito.any())).thenReturn(new Manifest(inputStream));
			
			final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

			final String output = objectUnderTest.getManifestMainAttributesValue(Name.CLASS_PATH);

			Assertions.assertThat(output).isNull();
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_string_string() throws IOException {
		ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
		Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			List<URL> urlList = Lists.newArrayList(new URL("file://manifest/MANIFEST.MF"));
			Enumeration<URL> urlEnum = Collections.enumeration(urlList);
			Mockito.when(urlEnumSupplier.get()).thenReturn(urlEnum);
			Mockito.when(manifestReader.apply(Mockito.any())).thenReturn(new Manifest(inputStream));
			
			final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

			final String output = objectUnderTest.getManifestMainAttributesValue("Build-Time");

			Assertions.assertThat(output).isNotNull().isEqualTo("2020-01-30 14:19");
		}
	}

	@Test
	public void testGetManifestMainAttributesValue_garbageString_null() throws IOException {
		final ServletContext servletContext = Mockito.mock(ServletContext.class, Mockito.withSettings().stubOnly());

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			Mockito.when(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).thenReturn(inputStream);

			ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
			Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );
			final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

			final String output = objectUnderTest.getManifestMainAttributesValue("garbage");

			Assertions.assertThat(output).isNull();
		}
	}

	@Test
	public void testGetManifestDetails_validManifest_manifestDetailsReturned() throws IOException {
		ManifestUrlEnumerationSupplier urlEnumSupplier = Mockito.mock(ManifestUrlEnumerationSupplier.class );
		Function<URL, Manifest> manifestReader = Mockito.mock(Function.class );

		try (InputStream inputStream = new ClassPathResource("/manifest/MANIFEST.MF").getInputStream();) {
			List<URL> urlList = Lists.newArrayList(new URL("file://manifest/MANIFEST.MF"));
			Enumeration<URL> urlEnum = Collections.enumeration(urlList);
			Mockito.when(urlEnumSupplier.get()).thenReturn(urlEnum);
			Mockito.when(manifestReader.apply(Mockito.any())).thenReturn(new Manifest(inputStream));
			
			final ManifestProvider objectUnderTest = new ManifestProvider(urlEnumSupplier, manifestReader);

			final ManifestDetails output = objectUnderTest.getManifestDetails();

			Assertions.assertThat(output).isNotNull();
			Assertions.assertThat(output.getBuildTime()).isNotNull().isEqualTo("2020-01-30 14:19");
			Assertions.assertThat(output.getVersion()).isNotNull().isEqualTo("1.0.0-SNAPSHOT");
		}
	}

}

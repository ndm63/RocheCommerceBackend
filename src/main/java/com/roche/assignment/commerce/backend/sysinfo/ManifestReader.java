/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.google.common.base.Suppliers;

import lombok.RequiredArgsConstructor;

/**
 * Read and cache the manifest from the war.
 *
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@Component
@RequiredArgsConstructor
public class ManifestReader {
	private final ServletContext servletContext;

	private final Supplier<Manifest> manifest = Suppliers.memoize(this::supplyManifest);

	private final Supplier<Attributes> manifestMainAttributes = Suppliers
			.memoize(this::supplyManifestMainAttributes);

	/**
	 * Get the value of a main attribute from the manifest.
	 *
	 * @param attributeName The arbitrary string name of the main attribute to be read from the manifest
	 * @return The manifest main attribute value or null if the value could not be obtained
	 */
	public String getManifestMainAttributesValue(final String attributeName) {
		return (manifestMainAttributes.get() != null) ? manifestMainAttributes.get().getValue(attributeName) : null;
	}

	/**
	 * Get the value of a main attribute from the manifest.
	 *
	 * @param attributeName A predefined standard enum name of the main attribute to be read from the manifest
	 * @return The manifest main attribute value or null if the value could not be obtained
	 */
	public String getManifestMainAttributesValue(final Name attributeName) {
		return (manifestMainAttributes.get() != null) ? manifestMainAttributes.get().getValue(attributeName) : null;
	}

	/**
	 * Get the basic build details from the manifest.
	 *
	 * @return A structure holding basic build information from the manifest or null if it cannot be obtained.
	 */
	public ManifestBasicBuildInfo getManifestBasicBuildInfo() {
		return (manifest.get() != null)
				? new ManifestBasicBuildInfo(manifest.get().getMainAttributes().getValue(Name.IMPLEMENTATION_VERSION),
						manifest.get().getMainAttributes().getValue("Build-Time"))
				: null;
	}

	/**
	 * Get more detailed info from the manifest.
	 *
	 * @return A structure holding details from the manifest or null if it cannot be obtained.
	 */
	public ManifestDetails getManifestDetails() {
		return (manifest.get() != null)
				? new ManifestDetails(manifest.get().getMainAttributes().getValue(Name.IMPLEMENTATION_VERSION),
						manifest.get().getMainAttributes().getValue("Build-Time"),
						manifest.get().getMainAttributes().getValue(Name.IMPLEMENTATION_TITLE),
						manifest.get().getMainAttributes().getValue("SCM-Branch"),
						manifest.get().getMainAttributes().getValue("SCM-Revision"),
						manifest.get().getMainAttributes().getValue("Created-By"),
						manifest.get().getMainAttributes().getValue("Build-Jdk"))
				: null;
	}

	/**
	 * Helper method to supply the manifest by reading it from the WAR resource.
	 *
	 * @return The supplied manifest or null if it could not be read.
	 */
	private Manifest supplyManifest() {
		Manifest retVal;
		try (final InputStream inputStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");) {
			retVal = (inputStream != null) ? new Manifest(inputStream) : null;
		} catch (final IOException exc) {
			retVal = null;
		}
		return retVal;
	}

	/**
	 * Helper method to supply the manifest main attributes. This is implemented as a method, rather than directly in
	 * the initialisation of the data member, so that it can access other data members, specifically the manifest.
	 *
	 * @return The supplied manifest main attributes or null if they could not be supplied
	 */
	private Attributes supplyManifestMainAttributes() {
		return (manifest.get() != null) ? manifest.get().getMainAttributes() : null;
	}
}

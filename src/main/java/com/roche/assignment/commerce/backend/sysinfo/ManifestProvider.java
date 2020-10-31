/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.springframework.stereotype.Component;

import com.google.common.base.Suppliers;

import lombok.RequiredArgsConstructor;

/**
 * Read and cache the application manifest obtained from the jar.
 *
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@Component
@RequiredArgsConstructor
public class ManifestProvider {
	/** Name used to identify the MANIFEST file */
	private static final String APP_NAME = "commerce-backend";

	/**
	 * Supply the enumeration of URLs for jar manifests.
	 */
	private final ManifestUrlEnumerationSupplier manifestUrlEnumerationSupplier;

	/**
	 * Function to read and return a manifest given the jar URL
	 */
	private final Function<URL, Manifest> manifestReader;

	/**
	 * Cache the application manifest
	 */
	private final Supplier<Manifest> manifest = Suppliers.memoize(this::supplyManifest);

	/**
	 * Cache the application manifest main attributes
	 */
	private final Supplier<Attributes> manifestMainAttributes = Suppliers.memoize(this::supplyManifestMainAttributes);

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
		final Enumeration<URL> resources = manifestUrlEnumerationSupplier.get();
		if (resources != null) {
			final Optional<Manifest> mfOpt = Collections.list(resources).stream().map(manifestReader)
					.filter(this::isAppManifest).findFirst();
			retVal = mfOpt.isPresent() ? mfOpt.get() : null;
		} else {
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

	/**
	 * Helper predicate. Determine if a manifest is the one sought. We are looking for the one that has the
	 * implementation title as the name of this application
	 *
	 * @param manifest1 The manifest to be tested
	 * @return True if it is the manifest sought.
	 */
	private boolean isAppManifest(final Manifest manifest1) {
		boolean retVal = false;
		if (manifest1 != null) {
			final Attributes attrs = manifest1.getMainAttributes();
			if (attrs != null) {
				final String title = attrs.getValue(Name.IMPLEMENTATION_TITLE);
				retVal = APP_NAME.equals(title);
			}
		}
		return retVal;
	}
}

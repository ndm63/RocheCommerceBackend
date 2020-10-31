/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;
import java.util.jar.Manifest;

import org.springframework.stereotype.Component;

/**
 * Simple function to read the manifest from a jar, given the URL. Extracted to separate class to make the reading of
 * manifests unit testable.
 *
 * @author Neill McQuillin (created by)
 * @since 31 October 2020 (creation date)
 */
@Component
public class ManifestReader implements Function<URL, Manifest> {
	@Override
	public Manifest apply(final URL url) {
		try (final InputStream inputStream = url.openStream();) {
			return (inputStream != null) ? new Manifest(inputStream) : null;
		} catch (final IOException exc) {
			return null;
		}
	}
}

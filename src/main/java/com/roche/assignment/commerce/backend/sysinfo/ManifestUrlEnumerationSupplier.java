/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

/**
 * Simple supplier of jar manifest URLs. Extracted to separate class to make the reading of manifests unit testable.
 *
 * @author Neill McQuillin (created by)
 * @since 31 October 2020 (creation date)
 */
@Component
public class ManifestUrlEnumerationSupplier implements Supplier<Enumeration<URL>> {
	@Override
	public Enumeration<URL> get() {
		try {
			return getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
		} catch (final IOException e) {
			return null;
		}
	}
}

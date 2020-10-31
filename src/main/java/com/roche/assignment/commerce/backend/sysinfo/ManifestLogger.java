/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.util.jar.Attributes.Name;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Log build details, from the manifest, at startup.
 *
 * This won't actually run until Spring has finished wiring everything. So if deployment fails nothing will get logged.
 *
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ManifestLogger {
	private final ManifestProvider manifestReader;

	@PostConstruct
	public void postConstruct() {
		ManifestLogger.LOGGER.info("Component build details from manifest:");
		ManifestLogger.LOGGER.info("  implementation title: {}",
				manifestReader.getManifestMainAttributesValue(Name.IMPLEMENTATION_TITLE));
		ManifestLogger.LOGGER.info("  implementation version: {}",
				manifestReader.getManifestMainAttributesValue(Name.IMPLEMENTATION_VERSION));
		ManifestLogger.LOGGER.info("  build time: {}", manifestReader.getManifestMainAttributesValue("Build-Time"));
		ManifestLogger.LOGGER.info("  scm branch: {}", manifestReader.getManifestMainAttributesValue("SCM-Branch"));
		ManifestLogger.LOGGER.info("  scm revision: {}", manifestReader.getManifestMainAttributesValue("SCM-Revision"));
		ManifestLogger.LOGGER.info("  created by: {}", manifestReader.getManifestMainAttributesValue("Created-By"));
		ManifestLogger.LOGGER.info("  build jdk: {}", manifestReader.getManifestMainAttributesValue("Build-Jdk"));
	}
}

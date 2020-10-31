/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Structure to hold basic build information, obtained from the manifest. This can then be marshaled to JSON and sent as
 * the response to an HTP request.
 *
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@Getter
@Setter
@AllArgsConstructor
public class ManifestBasicBuildInfo {
	/** The manifest implementation version. */
	private String version;

	/** The build time. */
	private String buildTime;
}

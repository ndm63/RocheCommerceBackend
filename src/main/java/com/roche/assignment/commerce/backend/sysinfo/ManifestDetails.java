/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Structure to hold more detailed information, obtained from the manifest. This can then be marshaled to JSON and sent
 * as the response to an HTP request.
 *
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@Getter
@Setter
@AllArgsConstructor
public class ManifestDetails {
	/** The manifest implementation version. */
	private String version;

	/** The build time. */
	private String buildTime;

	/**
	 * The manifest implementation title (name of the component)
	 */
	private String implementationTitle;

	/**
	 * The branch of the SCM (SCCS) (Git), from which the component was built
	 */
	private String scmBranch;

	/**
	 * The revision in the SCM (SCCS) (Git), from which the component was built
	 */
	private String scmRevision;

	/**
	 * The name of the entity that built the component.
	 */
	private String createdBy;

	/**
	 * The version of the Java JDK used to build the component.
	 */
	private String buildJdk;
}

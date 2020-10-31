/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.sysinfo;

import java.util.jar.Attributes.Name;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Rest API [web] service to return manifest details for the WAR component.
 *
 * @author Neill McQuillin (created by)
 * @since 29 January 2020 (creation date)
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManifestRestController {
	private final ManifestReader manifestReader;

	/**
	 * Obtain the version number of the component, from the manifest, and return it as a string in the body.
	 *
	 * @return The HTTP response.
	 */
	@GetMapping("/version")
	@ResponseBody
	public ResponseEntity<String> getVersion(@RequestHeader final HttpHeaders httpHeaders) {
		processXheaders(httpHeaders);
		return ResponseEntity.ok().body(manifestReader.getManifestMainAttributesValue(Name.IMPLEMENTATION_VERSION));
	}

	/**
	 * Obtain basic build info, of the component, from the manifest, and return it as a JSON string in the body.
	 *
	 * @return The HTTP response.
	 */
	@GetMapping(value = "/info", produces = "application/json")
	@ResponseBody
	public ResponseEntity<ManifestBasicBuildInfo> getInfo(@RequestHeader final HttpHeaders httpHeaders) {
		processXheaders(httpHeaders);
		return ResponseEntity.ok().body(manifestReader.getManifestBasicBuildInfo());
	}

	/**
	 * Obtain more detailed info, of the component, from the manifest, and return it as a JOSN string in the body.
	 *
	 * @return The HTTP response.
	 */
	@GetMapping(value = "/manifest", produces = "application/json")
	@ResponseBody
	public ResponseEntity<ManifestDetails> getDetailedInfo(@RequestHeader final HttpHeaders httpHeaders) {
		processXheaders(httpHeaders);
		return ResponseEntity.ok().body(manifestReader.getManifestDetails());
	}

	/**
	 * Helper method to set the correlation id, if there is an X-header containing it.
	 * 
	 * @param httpHeaders HTTP headers passed in a request.
	 */
	private void processXheaders(HttpHeaders httpHeaders) {
		if (httpHeaders != null) {
			final String corrId = httpHeaders.getFirst("X-Correlation-Id");
			MDC.put("correlationId", corrId);
		}
	}
}

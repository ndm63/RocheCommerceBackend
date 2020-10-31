/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA configuration, with data-source. The data-source is defined directly with JDBC configuration properties
 *
 * @author Neill McQuillin (created by)
 * @since 24 April 2020 (creation date)
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.roche.assignment.commerce.backend.persistence.dao" })
@EnableTransactionManagement
public class JpaRepositoryConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
}

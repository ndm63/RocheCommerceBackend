/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.roche.assignment.commerce.backend.persistence.model.Dummy;

/**
 * DAO for the Dummy table. If we have no entities and no DAOs, the Spring JPA instantiation fails
 *
 * @author Neill McQuillin (created by)
 * @since 22 July 2019 (creation date)
 */
public interface DummyDAO extends JpaRepository<Dummy, Long> {
	Dummy findUniqueByCommunityAndBatchNumber(@Param("community") final String community,
			@Param("batchNum") final String batchNum);
}

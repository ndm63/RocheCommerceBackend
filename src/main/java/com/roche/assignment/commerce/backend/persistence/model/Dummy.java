/**
 * Roche Home Assignment
 */
package com.roche.assignment.commerce.backend.persistence.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import lombok.Getter;

/**
 * Dummy entity. If we have no entities and no DAOs, the Spring JPA instantiation fails
 *
 * @author Neill McQuillin (created by)
 * @since 22 July 2019 (creation date)
 */
@Entity
@javax.persistence.Table(name = "NCA_FWD")
@AttributeOverride(name = "id", column = @Column(name = "NCA_FWD_ID"))
@NamedQuery(name = "Dummy.findUniqueByCommunityAndBatchNumber", query = "SELECT i FROM Dummy i WHERE i.community = :community AND i.batchNum = :batchNum")
public class Dummy extends DummyCommon implements Serializable {
	private static final long serialVersionUID = 8096668686739777877L;

	@Column(name = "BATCH_NUMBER")
	@Getter
	private String batchNum;

	@Column(name = "COMMUNITY")
	@Getter
	private String community;

	public void setCommunityAndBatchNum(final String community, final String batchNum) {
		this.community = community;
		this.batchNum = batchNum;
	}
}

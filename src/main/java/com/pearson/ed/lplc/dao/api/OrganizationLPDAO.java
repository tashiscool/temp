package com.pearson.ed.lplc.dao.api;

import java.util.List;

import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * 
 * @author UTRIVDI
 * 
 */
public interface OrganizationLPDAO {
	/**
	 * List License Pool based on organization ID provided.
	 * 
	 * @param organizationId
	 *            organizationId
	 * @param level
	 *            organization level
	 * @return List of LicensePoolMapping
	 */
	List<OrganizationLPMapping> listOrganizationMappingByOrganizationId(String organizationId, int level);

	/**
	 * List License Pool based on organization IDs provided.
	 * 
	 * @param organizationIds
	 *            list of organizationIds
	 * @param level
	 *            organization level
	 * @return List of organization license pool mapping
	 */
	List<OrganizationLPMapping> listOrganizationMappingByOrganizationId(List<String> organizationIds, int level);

}

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
	 *            organizationId.
	 * @return List of LicensePoolMapping.
	 */
	public List<OrganizationLPMapping> listOrganizationMappingByOrganizationId(String organizationId, int level);
    

}

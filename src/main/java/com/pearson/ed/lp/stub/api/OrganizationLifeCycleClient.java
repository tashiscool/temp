package com.pearson.ed.lp.stub.api;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;

/**
 * Client stub API for the OrganizationLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public interface OrganizationLifeCycleClient {

	/**
	 * Get request service activator to call the GetParentTreeByOrganizationId service function
	 * of the OrganizationLifeCycle service.
	 * 
	 * @param organizationId organization id string
	 * @return {@link OrganizationDisplayNamesResponse}
	 * @throws AbstractRumbaException on service error
	 */
	OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId)
		throws AbstractRumbaException;

	/**
	 * Get request service activator to call the GetChildTreeByOrganizationId service function
	 * of the OrganizationLifeCycle service.
	 * 
	 * @param organizationId organization id string
	 * @return {@link OrganizationDisplayNamesResponse}
	 * @throws AbstractRumbaException on service error
	 */
	OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId)
		throws AbstractRumbaException;

	/**
	 * Get request service activator to call the GetOrganizationById service function
	 * of the OrganizationLifeCycle service.
	 * 
	 * @param organizationId organization id string
	 * @return {@link OrganizationDisplayNamesResponse}
	 * @throws AbstractRumbaException on service error
	 */
	OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId)
		throws AbstractRumbaException;

}

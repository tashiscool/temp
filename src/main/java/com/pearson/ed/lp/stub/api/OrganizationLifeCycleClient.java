package com.pearson.ed.lp.stub.api;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrganizationDisplayNameRequest;
import com.pearson.ed.lp.message.OrganizationDisplayNameResponse;

/**
 * Client stub API for the OrganizationLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public interface OrganizationLifeCycleClient {

	/**
	 * Get request service activator to call the GetOrganizationById service function
	 * of the OrganizationLifeCycle service.
	 * 
	 * @param {@link OrganizationDisplayNameRequest}
	 * @return {@link OrganizationDisplayNameResponse}
	 * @throws AbstractRumbaException on service error
	 */
	OrganizationDisplayNameResponse getOrganizationDisplayName(OrganizationDisplayNameRequest request)
		throws AbstractRumbaException;

}

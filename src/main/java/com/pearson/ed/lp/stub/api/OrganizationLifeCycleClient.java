package com.pearson.ed.lp.stub.api;

import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;

/**
 * Client stub API for the OrganizationLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public interface OrganizationLifeCycleClient {

	OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId);

	OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId);

	OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId);

}

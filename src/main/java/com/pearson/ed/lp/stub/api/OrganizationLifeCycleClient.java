package com.pearson.ed.lp.stub.api;

import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;


public interface OrganizationLifeCycleClient {

	OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId);

	OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId);

	OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId);
	
}

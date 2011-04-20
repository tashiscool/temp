package com.pearson.ed.lplc.stub.impl;

import java.util.ArrayList;
import java.util.List;

import com.pearson.ed.lplc.stub.api.OrganizationServiceClient;
import com.pearson.ed.lplc.stub.dto.OrganizationDTO;

public class OrganizationServiceClientStubImpl implements OrganizationServiceClient {
	@Override
	public List<OrganizationDTO> getChildOrganizations(String organizationId) {
		List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
		orgList.add(new OrganizationDTO("DummyChildOrg1", 1));
		orgList.add(new OrganizationDTO("DummyChildOrg2", 1));
		orgList.add(new OrganizationDTO("DummyChildOrg3", 2));
		orgList.add(new OrganizationDTO("DummyChildOrg4", 2));
		orgList.add(new OrganizationDTO("DummyChildOrg5", 2));
		orgList.add(new OrganizationDTO("DummyChildOrg6", 3));
		return orgList;
	}
}

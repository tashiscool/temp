package com.pearson.ed.lplc.stub;

import java.util.ArrayList;
import java.util.List;

public class OrganizationStub {
	public List<OrganizationDTO> getChildOrganizaitons(String orgId){
		List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
		orgList.add(new OrganizationDTO("DummyChildOrg1",1));
		orgList.add(new OrganizationDTO("DummyChildOrg2",1));
		orgList.add(new OrganizationDTO("DummyChildOrg3",2));
		orgList.add(new OrganizationDTO("DummyChildOrg4",2));
		orgList.add(new OrganizationDTO("DummyChildOrg5",2));
		orgList.add(new OrganizationDTO("DummyChildOrg6",3));
		return orgList;
	}
	
	public List<OrganizationDTO> getParentOrganizations(String orgId){
		List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
		orgList.add(new OrganizationDTO("DummyParentOrg1",1));
		orgList.add(new OrganizationDTO("DummyParentOrg2",1));
		orgList.add(new OrganizationDTO("DummyParentOrg3",2));
		orgList.add(new OrganizationDTO("DummyParentOrg4",3));
		return orgList;
	}

}

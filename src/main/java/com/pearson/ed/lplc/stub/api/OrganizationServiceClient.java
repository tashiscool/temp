package com.pearson.ed.lplc.stub.api;

import java.util.List;

import com.pearson.ed.lplc.stub.dto.OrganizationDTO;

/**
 * This interface defines the to be implemented to be invoked on Organization Life Cycle service.
 * 
 * @author vtirura
 * 
 */
public interface OrganizationServiceClient {

	/**
	 * Method to get the child organizations for supplied orgId.
	 * 
	 * @param organizationId
	 * @return List of OrganizationDTOs.
	 */
	List<OrganizationDTO> getChildOrganizations(String organizationId);

}

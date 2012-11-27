package com.pearson.ed.lp.message;

/**
 * Request for OrganizationLifeCycleClient api.
 * 
 * @author ULLOYNI
 * 
 */
public class OrganizationDisplayNameRequest {

	private String organizationId = null;

	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

}

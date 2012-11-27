package com.pearson.ed.lp.message;

/**
 * Response for the OrganizationLifeCycleClient api that gets the organization display name.
 * 
 * @author ULLOYNI
 * 
 */
public class OrganizationDisplayNameResponse {

	private String organizationDisplayName = null;
	private String organizationId = null;

	/**
	 * @return the organizationDisplayName
	 */
	public String getOrganizationDisplayName() {
		return organizationDisplayName;
	}

	/**
	 * @param organizationDisplayName the organizationDisplayName to set
	 */
	public void setOrganizationDisplayName(String organizationDisplayName) {
		this.organizationDisplayName = organizationDisplayName;
	}

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

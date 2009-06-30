package com.pearson.ed.lplc.dto;

import java.io.Serializable;

public class GetLicensePoolByOrgIdRequestDTO implements Serializable{
	
	private static final long serialVersionUID = -6791950440560657888L;
	
     private String organizationId;

	/**Getter.
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * Setter.
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
}

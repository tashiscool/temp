package com.pearson.ed.lp.message;

import java.util.List;

import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * Response for the LicensePoolLifeCycleClient that wraps a collection of {@link OrganizationLPMapping} objects.
 * 
 * @author ULLOYNI
 * 
 */
public class LicensePoolResponse {

	private List<OrganizationLPMapping> licensePools = null;

	/**
	 * Default constructor.
	 */
	public LicensePoolResponse() {
	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param licensePools collection of {@link OrganizationLPMapping} instances
	 */
	public LicensePoolResponse(List<OrganizationLPMapping> licensePools) {
		this.licensePools = licensePools;
	}

	/**
	 * Get the list of {@link OrganizationLPMapping} objects.
	 * 
	 * @return list of {@link OrganizationLPMapping} objects
	 */
	public List<OrganizationLPMapping> getLicensePools() {
		return licensePools;
	}

	/**
	 * Set the list of {@link OrganizationLPMapping} objects.
	 * 
	 * @param licensePools list of {@link OrganizationLPMapping} objects
	 */
	public void setLicensePools(List<OrganizationLPMapping> licensePools) {
		this.licensePools = licensePools;
	}

}

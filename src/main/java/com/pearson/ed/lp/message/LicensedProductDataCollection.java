package com.pearson.ed.lp.message;

/**
 * Special response message for collection of client response data for the final response aggregator.
 * 
 * @author ULLOYNI
 * 
 */
public class LicensedProductDataCollection {

	private LicensePoolResponse licensePools = null;

	private OrganizationDisplayNamesResponse organizationDisplayNames = null;

	/**
	 * Get the license pools in the response.
	 * 
	 * @return {@link LicensePoolResponse} instance
	 */
	public LicensePoolResponse getLicensePools() {
		return licensePools;
	}

	/**
	 * Set the license pools for the response.
	 * 
	 * @param licensePools {@link LicensePoolResponse} instance
	 */
	public void setLicensePools(LicensePoolResponse licensePools) {
		this.licensePools = licensePools;
	}

	/**
	 * Get the organization display names in the response.
	 * 
	 * @return {@link OrganizationDisplayNamesResponse} instance
	 */
	public OrganizationDisplayNamesResponse getOrganizationDisplayNames() {
		return organizationDisplayNames;
	}

	/**
	 * Set the organization display names for the response.
	 * 
	 * @param organizationDisplayNames {@link OrganizationDisplayNamesResponse} instance
	 */
	public void setOrganizationDisplayNames(OrganizationDisplayNamesResponse organizationDisplayNames) {
		this.organizationDisplayNames = organizationDisplayNames;
	}

}

package com.pearson.ed.lp.message;

/**
 * Simple request object that wraps an Organization ID string and 
 * a qualifying license pool string as extracted from an XML request.
 * 
 * @author ULLOYNI
 *
 */
public class LicensePoolByOrganizationIdRequest {
	
	private String organizationId;
	private String qualifyingLicensePool;
	
	public LicensePoolByOrganizationIdRequest() {}
	
	/**
	 * Parameterized constructor.
	 * 
	 * @param organizationId organization id string
	 * @param qualifyingLicensePool qualifying license pool string
	 */
	public LicensePoolByOrganizationIdRequest(String organizationId, 
			String qualifyingLicensePool) {
		this.organizationId = organizationId;
		this.qualifyingLicensePool = qualifyingLicensePool;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getQualifyingLicensePool() {
		return qualifyingLicensePool;
	}

	public void setQualifyingLicensePool(String qualifyingLicensePool) {
		this.qualifyingLicensePool = qualifyingLicensePool;
	}
}

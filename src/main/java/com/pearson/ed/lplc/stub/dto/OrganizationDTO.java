package com.pearson.ed.lplc.stub.dto;

public class OrganizationDTO {

	private String orgId;
	private int orgLevel;

	public OrganizationDTO() {
	}
	
	public OrganizationDTO(String orgId, int orgLevel) {
		this.orgId = orgId;
		this.orgLevel = orgLevel;
	}
	
	/**
	 * @return the org_id
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param org_id the org_id to set
	 */
	public void setOrgId(String org_id) {
		this.orgId = org_id;
	}
	/**
	 * @return the org_level
	 */
	public int getOrgLevel() {
		return orgLevel;
	}
	/**
	 * @param org_level the org_level to set
	 */
	public void setOrgLevel(int org_level) {
		this.orgLevel = org_level;
	}

}

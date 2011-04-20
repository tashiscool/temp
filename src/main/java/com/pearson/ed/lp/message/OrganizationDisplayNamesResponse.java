package com.pearson.ed.lp.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * Response for the OrganizationLifeCycleClient api that wraps a map of organization id strings to organization display
 * name strings.
 * 
 * @author ULLOYNI
 * 
 */
public class OrganizationDisplayNamesResponse {

	private Map<String, String> organizationDisplayNamesByIds = new Hashtable<String, String>();

	/**
	 * Get map of organization id strings to organization display name strings.
	 * 
	 * @return map of organization id strings to organization display name strings
	 */
	public Map<String, String> getOrganizationDisplayNamesByIds() {
		return organizationDisplayNamesByIds;
	}

	/**
	 * Set map of organization id strings to organization display name strings.
	 * 
	 * @param organizationDisplayNamesByIds map of organization id strings to organization display name strings
	 */
	public void setOrganizationDisplayNamesByIds(Map<String, String> organizationDisplayNamesByIds) {
		this.organizationDisplayNamesByIds = organizationDisplayNamesByIds;
	}
}

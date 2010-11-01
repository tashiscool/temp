package com.pearson.ed.lplc.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dao.impl.common.LPLCBaseDAOImpl;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

public class OrganizationLPDAOImpl extends LPLCBaseDAOImpl implements
OrganizationLPDAO, LPLCConstants {
	/**
	 * Gets license pools for the given organizationId,
	 * that are created at the organization or inherited from parent 
	 * using the level attribute. level =0 means, license pools created for an organization.
	 * 
	 * @param organizationId
	 *            organizationId
	 * @param level
	 *            organization level
	 * @return List of LicensePoolMapping
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationLPMapping> listOrganizationMappingByOrganizationId(
			String organizationId, int level) {
		Criteria criteria = getSession().createCriteria(OrganizationLPMapping.class)
							.setFetchMode("licensepoolMapping", FetchMode.JOIN)
							.setFetchMode("licensepoolMapping.orderLineItems", FetchMode.JOIN);
		Criterion eqOrganizationId = Restrictions.eq("organization_id", organizationId);
		Criterion eqRootOrganization = Restrictions.like("organization_level", LPLCConstants.INITIAL_LEVEL);
		if (level == LPLCConstants.INITIAL_LEVEL)
			criteria.add(eqRootOrganization);
				
		return (new ArrayList(new LinkedHashSet(criteria.add(eqOrganizationId).list())));
	}
	
	/**
	 * Gets license pools for the given organizationIds,
	 * that are created at the organization or inherited from parent 
	 * using the level attribute. level =0 means, license pools created for an organization.
	 * 
	 * @param organizationIds
	 *            list of organizationIds
	 * @param level
	 *            organization level
	 * @return List of organization license pool mapping
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationLPMapping> listOrganizationMappingByOrganizationId(
			List<String> organizationIds, int level) {
		Criteria criteria = getSession().createCriteria(OrganizationLPMapping.class)
							.setFetchMode("licensepoolMapping", FetchMode.JOIN)
							.setFetchMode("licensepoolMapping.orderLineItems", FetchMode.JOIN)
							.add(Restrictions.in("organization_id", organizationIds))
							.add(Restrictions.eq("organization_level",level));
		
		return  (new ArrayList(new LinkedHashSet(criteria.list())));
	}
	
	/**
	 * Saves newly updated licensepools to an organization.
	 * 
	 * @param licenses
	 * 			the newly applied licenses
	 *	 
	 */
	public void saveAllLicenses(List<OrganizationLPMapping> licenses) {
		getHibernateTemplate().saveOrUpdateAll(licenses);
	}
}

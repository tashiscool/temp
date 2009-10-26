package com.pearson.ed.lplc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dao.impl.common.LPLCBaseDAOImpl;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

public class OrganizationLPDAOImpl extends LPLCBaseDAOImpl implements
OrganizationLPDAO {
	/**
	 * List License Pool based on organization ID provided.
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
							.setFetchMode("licensepoolMapping", FetchMode.JOIN);
		Criterion eqOrganizationId = Restrictions.eq("organization_id", organizationId);
		Criterion eqRootOrganization = Restrictions.like("organization_level", 0);
		if (level == 0)
			criteria.add(eqRootOrganization);
		return criteria.add(eqOrganizationId).list();
	}
	
	/**
	 * List License Pool based on organization IDs provided.
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
							.add(Restrictions.in("organization_id", organizationIds))
							.add(Restrictions.eq("organization_level", 0));
		return criteria.list();
	}
}

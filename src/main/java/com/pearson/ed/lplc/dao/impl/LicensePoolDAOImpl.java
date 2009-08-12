package com.pearson.ed.lplc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.impl.common.LPLCBaseDAOImpl;
import com.pearson.ed.lplc.model.LicensePoolMapping;

/**
 * LicensePoolMappingDAOImpl is the implementation for user mapping data access
 * objects in the LPLC.
 * 
 * @author RUMBA
 */

public class LicensePoolDAOImpl extends LPLCBaseDAOImpl implements
		LicensePoolDAO {
	/**
	 * Creates a license pool mapping.
	 * 
	 * @param lplcMapping
	 *            the license pool mapping.
	 */
	public void createLicensePool(LicensePoolMapping lplcMapping) {
		setCreateAndModifiedValues(lplcMapping);
		getHibernateTemplate().save(lplcMapping);
	}

	/**
	 * Update license pool.
	 * 
	 * @param lplcMapping
	 *            lplcMapping.
	 */
	public void update(LicensePoolMapping lplcMapping) {
		setModifiedValues(lplcMapping);
		getHibernateTemplate().saveOrUpdate(lplcMapping);
	}

	/**
	 * Find license pool by name.
	 * 
	 * @param lplcName
	 * @return LicensePool mapping.
	 */
	public LicensePoolMapping findByLicensePoolId(String lplcId) {
		Criteria criteria = getSession().createCriteria(
				LicensePoolMapping.class);
		Criterion eqLicensePoolId = Restrictions.eq("licensepoolId", lplcId);
		criteria.add(eqLicensePoolId);
		return (LicensePoolMapping) criteria.uniqueResult();
	}
	
	/**
     * Get LicensePool for Subscription.
     * 
     * @param organizationId organizationId.
     * @param productId productId.
     * @param asOfDate - To check license pool start date and end date lie within the given date
     * @param considerDenySubscriptions - if true checks if denySubscriptions is not set
     * @return list of LicensePoolMapping.
     */
	@SuppressWarnings("unchecked")
	public List<LicensePoolMapping> findOrganizationMappingToSubscribe(String organizationId,
			String productId, Date asOfDate, boolean considerDenySubscriptions){
		Criteria criteria = getSession().createCriteria(LicensePoolMapping.class);		
		criteria.createAlias("organizations", "organizations");
		
		Criterion eqOrganizationId = Restrictions.eq("organizations.organization_id", organizationId);
		Criterion eqProductId = Restrictions.eq("product_id", productId);
		Criterion leStartDate = Restrictions.le("start_date", asOfDate);
		Criterion geEndDate = Restrictions.ge("end_date", asOfDate);
		Criterion eqDenyNewSubscription = Restrictions.eq("denyManualSubscription",LPLCConstants.DENY_SUBSCRIPTIONS_FALSE);
		Criterion eqDenyNewSubscriptionOrgLevel = Restrictions.eq("organizations.denyManualSubscription",LPLCConstants.DENY_SUBSCRIPTIONS_FALSE);
		
		criteria.add(eqProductId)
					.add(eqOrganizationId);				
		
		if (asOfDate != null) {
			criteria.add(leStartDate)
					   .add(geEndDate);	
		}
		
		if (considerDenySubscriptions) {
			criteria	.add(eqDenyNewSubscription)
						.add(eqDenyNewSubscriptionOrgLevel);
		}
		
		criteria.addOrder(Order.asc("organizations.organization_level"));
		criteria.addOrder(Order.desc("end_date"));
		
		// Store in a LinkedHashSet to maintain the order of the results.
		List<LicensePoolMapping> qualifyingLicensePools = 
					new ArrayList<LicensePoolMapping>(new LinkedHashSet<LicensePoolMapping>(criteria.list()));
		
		return qualifyingLicensePools;
	}
}

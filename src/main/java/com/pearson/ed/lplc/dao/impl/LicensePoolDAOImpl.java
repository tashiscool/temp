package com.pearson.ed.lplc.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.impl.common.LPLCBaseDAOImpl;
import com.pearson.ed.lplc.model.LicensePoolMapping;


/**
 * LicensePoolMappingDAOImpl is the implementation for user mapping data access objects
 * in the LPLC.
 * 
 * @author RUMBA
 */

public class LicensePoolDAOImpl extends LPLCBaseDAOImpl implements LicensePoolDAO {
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
	 * Find license pool by name.
	 * @param lplcName
	 * @return LicensePool mapping.
	 */
	public LicensePoolMapping findByLicensePoolId(String lplcId) {
		Criteria criteria = getSession().createCriteria(LicensePoolMapping.class);
		Criterion eqLicensePoolId = Restrictions.eq("LICENSEPOOL_ID", lplcId);
		criteria.add(eqLicensePoolId);
		return (LicensePoolMapping) criteria.uniqueResult();
	}
}

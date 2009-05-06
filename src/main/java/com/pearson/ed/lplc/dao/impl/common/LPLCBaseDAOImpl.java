package com.pearson.ed.lplc.dao.impl.common;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pearson.ed.lplc.model.common.LPLCBaseEntity;

/**
 * The base class for all data access object implementations in the LPLC.
 * 
 * @author RUMBA
 */

public abstract class LPLCBaseDAOImpl extends HibernateDaoSupport {
	/**
	 * Save the licensepool object.
	 * @param entity entity.
	 */
	public void save(LPLCBaseEntity entity) {
		getHibernateTemplate().save(entity);
	}
	/**
	 * Update the licensepool object.
	 * @param entity entity.
	 */
	public void update(LPLCBaseEntity entity) {
		getHibernateTemplate().update(entity);
	}
	/**
	 * Delete the licensepool object.
	 * @param entity entity.
	 */
	public void delete(LPLCBaseEntity entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * update modified fields of the entity with current relevant info
	 * 
	 * @param baseEntity
	 *            LPLCBaseEntity
	 */
	protected void setModifiedValues(LPLCBaseEntity baseEntity) {
		String lastUpdatedBy = baseEntity.getLastUpdatedBy();
		if (lastUpdatedBy == null || lastUpdatedBy.length() <= 0) {
			lastUpdatedBy = "System";
		}
		Date lastUpdatedDate = baseEntity.getLastUpdatedDate();
		if (lastUpdatedDate == null) {
			lastUpdatedDate = new Date();
		}
		baseEntity.setLastUpdatedBy(lastUpdatedBy);
		baseEntity.setLastUpdatedDate(lastUpdatedDate);
	}

	/**
	 * update created fields of the entity with current relevant info
	 * 
	 * @param baseEntity
	 *            LPLCBaseEntity
	 */
	protected void setCreatedValues(LPLCBaseEntity baseEntity) {
		String createdBy = baseEntity.getCreatedBy();
		if (createdBy == null || createdBy.length() <= 0) {
			createdBy = "System";
		}
		Date createdDate = baseEntity.getCreatedDate();
		if (createdDate == null) {
			createdDate = new Date();
		}
		baseEntity.setCreatedBy(createdBy);
		baseEntity.setCreatedDate(createdDate);
	}

	/**
	 * set modified and create fields so that they are in synch
	 * 
	 * @param entity  LPLCBaseEntity
	 */
	protected void setCreateAndModifiedValues(LPLCBaseEntity entity) {
		setCreatedValues(entity);
		setModifiedValues(entity);
	}
}

package com.pearson.ed.lplc.services.converter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.common.LPLCBaseEntity;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.LicensePool;

public class LicensePoolConverterImpl implements LicensePoolConverter {
	private static final Logger logger = Logger
			.getLogger(LicensePoolConverterImpl.class);

	/**
	 * Convert to LicensePoolDTO.
	 * 
	 * @param licensepool
	 * @return LicensePoolDTO.
	 */
	public LicensePoolDTO convertLicensePoolToLicensePoolDTO(
			LicensePool licensepool) {
		LicensePoolDTO licensepoolDTO = new LicensePoolDTO();
		licensepoolDTO.setLicensepoolId(licensepool.getLicenseId());
		licensepoolDTO.setLicensePoolStatus(licensepool.getLicensePoolStatus()
				.value());
		licensepoolDTO.setType(licensepool.getType());
		licensepoolDTO.setDenyManualSubscription(licensepool
				.getDenyNewSubscription());
		licensepoolDTO.setStartDate(licensepool.getStartDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setEndDate(licensepool.getEndDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setQuantity(licensepool.getQuantity());
		licensepoolDTO.setUsedLicenses(licensepool.getUsedLicenses());
		licensepoolDTO.setOrganizationId(licensepool.getOrgnizationId());
		licensepoolDTO.setProductId(getProducts(licensepool));
		licensepoolDTO.setSourceSystem(licensepool.getSourceSystem());
		licensepoolDTO.setCreatedBy(licensepool.getCreatedBy());
		licensepoolDTO.setCreatedDate(licensepool.getCreatedDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setLastUpdatedBy(licensepool.getLastUpdatedBy());
		licensepoolDTO.setLastUpdatedDate(licensepool.getLastUpdatedDate()
				.toGregorianCalendar().getTime());
		return licensepoolDTO;

	}

	private List<String> getProducts(LicensePool licensepool) {
		List<String> newProductIds = new ArrayList<String>();
		List<String> productIds = licensepool.getProductId();
		Iterator<String> iterator = productIds.iterator();
		while (iterator.hasNext())
			newProductIds.add(iterator.next());
		return newProductIds;
	}

	/**
	 * Converts a LicensePoolMapping to a LicensePool.
	 * 
	 * @param licensepoolMapping
	 *            the LicensePoolMapping object.
	 * 
	 * @return the LicensePool object.
	 */
	public LicensePoolDTO convertLicensePoolFromLicensePoolMapping(
			LicensePoolMapping licensepoolMapping) {
		LicensePoolDTO licensepool = new LicensePoolDTO();
		licensepool.setLicensepoolId(licensepoolMapping.getLicensepoolId());
		licensepool.setType(licensepoolMapping.getType());
		licensepool.setDenyManualSubscription(licensepoolMapping
				.getDenyManualSubscription());
		licensepool.setLicensePoolStatus(licensepoolMapping.getStatus());
		licensepool.setQuantity(licensepoolMapping.getQuantity());
		licensepool.setUsedLicenses(licensepoolMapping.getUsed_quantity());
		licensepool.setStartDate(licensepoolMapping.getStart_date());
		licensepool.setEndDate(licensepoolMapping.getEnd_date());
		licensepool.setOrganizationId(licensepoolMapping.getOrg_id());
		List<String> productList = new ArrayList<String>();
		// Set<ProductLPMapping> products = licensepoolMapping.getProducts();
		// Iterator<ProductLPMapping> iterator = products.iterator();
		// while (iterator.hasNext())
		// productList.add(iterator.next().getProduct_id());
		// licensepool.setProductId(productList);
		licensepool.setSourceSystem(licensepoolMapping.getSource_system());
		licensepool.setCreatedBy(licensepoolMapping.getCreatedBy());
		licensepool.setCreatedDate(licensepoolMapping.getCreatedDate());
		licensepool.setLastUpdatedBy(licensepoolMapping.getLastUpdatedBy());
		licensepool.setLastUpdatedDate(licensepoolMapping.getLastUpdatedDate());

		return licensepool;
	}

	/**
	 * Converts a LicensePool to a LicensePoolMappings.
	 * 
	 * @param user
	 *            the LicensePool object.
	 * @param userMapping
	 *            the LicensePoolMapping object
	 * 
	 * @return the LicensePoolMapping object.
	 */
	public LicensePoolMapping convertLicensePoolToLicensePoolMapping(
			LicensePoolDTO licensepool, LicensePoolMapping licensepoolMapping) {
		// validate and set mode
		if (StringUtils.isEmpty(licensepool.getMode())) {
			if (StringUtils.isEmpty(licensepool.getLicensepoolId())) {
				licensepool.setMode(LPLCConstants.CREATE_MODE);
			} else {
				licensepool.setMode(LPLCConstants.UPDATE_MODE);
			}
		}

		return buildLicensePoolMapping(licensepool, licensepoolMapping);
	}

	private LicensePoolMapping buildLicensePoolMapping(
			LicensePoolDTO licensepool, LicensePoolMapping licensepoolMapping) {
		String mode = licensepool.getMode();
		if (LPLCConstants.CREATE_MODE.equals(mode)) {
			licensepoolMapping = new LicensePoolMapping();
		}

		String type = licensepool.getType();
		if (StringUtils.isNotBlank(type)) {
			licensepoolMapping.setType(type);
		}

		String status = licensepool.getLicensePoolStatus();
		if (StringUtils.isNotBlank(status)) {
			licensepoolMapping.setStatus(status);
		}

		licensepoolMapping.setDenyManualSubscription(licensepool
				.getDenyManualSubscription());

		licensepoolMapping.setStart_date(licensepool.getStartDate());
		licensepoolMapping.setEnd_date(licensepool.getEndDate());
		licensepoolMapping.setQuantity(licensepool.getQuantity());
		licensepoolMapping.setUsed_quantity(licensepool.getUsedLicenses());
		licensepoolMapping.setSource_system(licensepool.getSourceSystem());
		licensepoolMapping.setOrg_id(licensepool.getOrganizationId());
		licensepoolMapping.setProducts(getProducts(licensepool));
		
		String createdBy = licensepool.getCreatedBy();
		if (StringUtils.isNotBlank(createdBy))
			licensepoolMapping.setCreatedBy(createdBy);

		if (LPLCConstants.CREATE_MODE.equals(mode)) {
			setCreatedValues(licensepoolMapping, licensepool);
		}
		setModifiedValues(licensepoolMapping, licensepool);

		return licensepoolMapping;
	}

	private List<String> getProducts(LicensePoolDTO licensepool) {
		List<String> productList = new ArrayList<String>();
		List<String> productIds = licensepool.getProductId();
		for (String product : productIds) {
			productList.add(product);
		}
		return productList;
	}

	private void setModifiedValues(LPLCBaseEntity lplcBaseEntity,
			LicensePoolDTO licensepool) {
		String lastUpdatedBy = licensepool.getLastUpdatedBy();
		if (StringUtils.isEmpty(lastUpdatedBy)) {
			lastUpdatedBy = LPLCConstants.DEFAULT_USER;
		}

		Date lastUpdatedDate = licensepool.getLastUpdatedDate();
		if (lastUpdatedDate == null) {
			lastUpdatedDate = LPLCConstants.DEFAULT_DATE;
		}
		lplcBaseEntity.setLastUpdatedBy(lastUpdatedBy);
		lplcBaseEntity.setLastUpdatedDate(lastUpdatedDate);
	}

	private void setCreatedValues(LPLCBaseEntity lplcBaseEntity,
			LicensePoolDTO licensepool) {
		String createdBy = lplcBaseEntity.getCreatedBy();
		if (StringUtils.isEmpty(createdBy)) {
			createdBy = LPLCConstants.DEFAULT_USER;
		}

		Date createdDate = lplcBaseEntity.getCreatedDate();
		if (createdDate == null) {
			createdDate = LPLCConstants.DEFAULT_DATE;
		}
		lplcBaseEntity.setCreatedBy(createdBy);
		lplcBaseEntity.setCreatedDate(createdDate);
	}

	public LicensePoolDTO covertCreateRequestToLicensePoolDTO(
			CreateLicensePool createLicensePoolSchemaObj) {
		LicensePoolDTO licensepoolDTO = new LicensePoolDTO();
		licensepoolDTO.setType(createLicensePoolSchemaObj.getType());
		licensepoolDTO.setDenyManualSubscription(createLicensePoolSchemaObj
				.getDenyNewSubscription());
		licensepoolDTO.setStartDate(createLicensePoolSchemaObj.getStartDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setEndDate(createLicensePoolSchemaObj.getEndDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setQuantity(createLicensePoolSchemaObj.getQuantity());
		licensepoolDTO.setOrganizationId(createLicensePoolSchemaObj
				.getOrgnizationId());
		licensepoolDTO.setProductId(getProducts(createLicensePoolSchemaObj));
		licensepoolDTO.setSourceSystem(createLicensePoolSchemaObj
				.getSourceSystem());
		licensepoolDTO.setCreatedBy(createLicensePoolSchemaObj.getCreatedBy());
		return licensepoolDTO;
	}

	private List<String> getProducts(CreateLicensePool licensepool) {
		List<String> newProductIds = new ArrayList<String>();
		List<String> productIds = licensepool.getProductId();
		if (productIds!= null){
			Iterator<String> iterator = productIds.iterator();
			while (iterator.hasNext())
				newProductIds.add(iterator.next());
		}
		return newProductIds;
	}

}

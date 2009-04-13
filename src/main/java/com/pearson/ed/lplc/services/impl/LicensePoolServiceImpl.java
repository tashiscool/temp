/**
 * LicensePoolServiceImpl.
 */
package com.pearson.ed.lplc.services.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;

/**
 * The LPLC's primary implementation of the licensepool service.
 * 
 * @author Dipali Trivedi
 * 
 */
public class LicensePoolServiceImpl implements LicensePoolService {

	/**
	 * Setter.
	 * 
	 * @param licensepoolConverter
	 */
	public void setLicensePoolConverter(
			LicensePoolConverter licensepoolConverter) {
		this.licensePoolConverter = licensepoolConverter;
	}

	/**
	 * @return the licensePoolConverter
	 */
	public LicensePoolConverter getLicensePoolConverter() {
		return licensePoolConverter;
	}

	/**
	 * @return the licensePoolDAO
	 */
	public LicensePoolDAO getLicensePoolDAO() {
		return licensePoolDAO;
	}

	/**
	 * Getter.
	 * 
	 * @param licensepoolDAO
	 */
	public void setLicensePoolDAO(LicensePoolDAO licensepoolDAO) {
		this.licensePoolDAO = licensepoolDAO;
	}

	private static final Logger logger = Logger
			.getLogger(LicensePoolServiceImpl.class);

	private LicensePoolConverter licensePoolConverter;
	private LicensePoolDAO licensePoolDAO;

	/**
	 * Create LicensePool Service.
	 * 
	 * @param licensepool
	 * @return String licensepoolId.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String createLicensePool(LicensePoolDTO licensepoolDTO) {
		licensepoolDTO.setMode(LPLCConstants.CREATE_MODE);
		Date startDate = licensepoolDTO.getStartDate();
		Date endDate = licensepoolDTO.getEndDate();
		Date currentDate = new Date();
		if ((currentDate.compareTo(startDate)>0) && (currentDate.compareTo(endDate)<0))
		    licensepoolDTO.setLicensePoolStatus("A");
		else if  (currentDate.compareTo(endDate)>0)
		    licensepoolDTO.setLicensePoolStatus("E");
		else if  (currentDate.compareTo(startDate)<0)
		    licensepoolDTO.setLicensePoolStatus("P");
		licensepoolDTO.setOrganizationLevel(0);
		LicensePoolMapping licensepoolMapping = licensePoolConverter
				.convertLicensePoolToLicensePoolMapping(licensepoolDTO, null);
		licensePoolDAO.createLicensePool(licensepoolMapping);
		return licensepoolMapping.getLicensepoolId();

	}

}
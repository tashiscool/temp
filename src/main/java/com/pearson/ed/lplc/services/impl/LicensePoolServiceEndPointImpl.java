package com.pearson.ed.lplc.services.impl;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.services.api.LicensePoolServiceEndPoint;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.UpdateLicensePool;

public class LicensePoolServiceEndPointImpl implements
		LicensePoolServiceEndPoint {

	private static final Logger logger = Logger
			.getLogger(LicensePoolServiceEndPointImpl.class);

	private LicensePoolService licensepoolService;
	private String transactionId;
	private LicensePoolConverter licensePoolConverter;

	/**
	 * @return the licensePoolConverter
	 */
	public LicensePoolConverter getLicensePoolConverter() {
		return licensePoolConverter;
	}

	/**
	 * @param licensePoolConverter
	 *            the licensePoolConverter to set
	 */
	public void setLicensePoolConverter(
			LicensePoolConverter licensePoolConverter) {
		this.licensePoolConverter = licensePoolConverter;
	}

	/**
	 * Mutator for the licensepool service.
	 * 
	 * @param licensepool
	 *            service the licensepool service.
	 */
	public void setLicensepoolService(LicensePoolService licensepoolService) {
		this.licensepoolService = licensepoolService;
	}

	/**
	 * Creates all the components for the given user. Optional components like
	 * email and phone will only be created if they are present.
	 * 
	 * @param licensepool
	 *            the licensepool to be created.
	 */
	public String createLicensePool(CreateLicensePool licensepool) {
		return licensepoolService.createLicensePool(licensePoolConverter
				.covertCreateRequestToLicensePoolDTO(licensepool));
	}

	/**
	 * Update License pool.
	 * 
	 * @param licensepool
	 *            the licensepool to be updated.
	 */
	public String updateLicensePool(UpdateLicensePool licensepool) {
		return licensepoolService.updateLicensePool(licensePoolConverter
				.covertupdateRequestToUpdateLicensePoolDTO(licensepool));
	}

	/**
	 * @return the licensepoolService
	 */
	public LicensePoolService getLicensepoolService() {
		return licensepoolService;
	}

	/**
	 * Mutator for the transaction ID.
	 * 
	 * @param transactionId
	 *            the transaction ID.
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Generates a transaction ID.
	 * 
	 * @return the transaction ID.
	 */
	public String generateTransactionId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Accessor for the transaction ID.
	 * 
	 * @return the transaction ID.
	 */
	public String getTransactionId() {
		return transactionId;
	}

}

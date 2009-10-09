package com.pearson.ed.lplc.services.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.services.api.LicensePoolServiceEndPoint;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.LicensePoolDetails;
import com.pearson.ed.lplc.ws.schema.LicensePoolToSubscribe;
import com.pearson.ed.lplc.ws.schema.LicensepoolsByOrganizationId;
import com.pearson.ed.lplc.ws.schema.UpdateLicensePool;

public class LicensePoolServiceEndPointImpl implements LicensePoolServiceEndPoint {

	private static final Logger logger = Logger.getLogger(LicensePoolServiceEndPointImpl.class);

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
	public void setLicensePoolConverter(LicensePoolConverter licensePoolConverter) {
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
	 * List license pools as per criteria.
	 * 
	 * @param organizationId
	 *            organizationId.
	 * @param qualifyingOrgs
	 *            qualifyingOrgs.
	 * @return the transaction ID.
	 */
	public LicensepoolsByOrganizationId getLicensePoolByOrganizationId(String organizationId, String qualifyingOrgs) {
		List<OrganizationLPMapping> licenses = licensepoolService.getLicensePoolByOrganizationId(organizationId,
				qualifyingOrgs);
		return licensePoolConverter.convertForGetFromLPMappingToSchema(licenses);
	}

	/**
	 * Get Licensepool for subscription.
	 * 
	 * @param organizationId
	 *            organizationId.
	 * @param productId
	 * @return
	 */
	public LicensePoolToSubscribe getLicensePoolToSubscribe(String organizationId, String productId) {
		return licensePoolConverter.convertForGetLicensepoolToSubscribe(licensepoolService.getLicensePoolToSubscribeId(
				organizationId, productId));
	}

	/**
	 * Gets license pool details for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @return licensePoolDetails details of license pool.
	 */
	public LicensePoolDetails getLicensePoolDetailsById(String licensePoolId) {
		return licensepoolService.getLicensePoolDetailsById(licensePoolId);
	}

	/**
	 * Denies New Subscriptions for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @param denyNewSubscription
	 *            denies new Subscription of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @return licensePoolId.
	 */
	public String denyNewSubscriptions(String licensePoolId, int denyNewSubscription, String createdBy) {
		return licensepoolService.denyNewSubscriptions(licensePoolId, denyNewSubscription, createdBy);
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

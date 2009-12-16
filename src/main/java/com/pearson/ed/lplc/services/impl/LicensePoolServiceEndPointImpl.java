package com.pearson.ed.lplc.services.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.exception.LicensePoolJMSException;
import com.pearson.ed.lplc.exception.RequiredObjectNotFound;
import com.pearson.ed.lplc.jms.util.LicensepoolJMSUtils;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.services.api.LicensePoolServiceEndPoint;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.EventTypeType;
import com.pearson.ed.lplc.ws.schema.LicensePoolDetails;
import com.pearson.ed.lplc.ws.schema.LicensePoolToSubscribe;
import com.pearson.ed.lplc.ws.schema.LicensepoolsByOrganizationId;
import com.pearson.ed.lplc.ws.schema.UpdateLicensePool;

public class LicensePoolServiceEndPointImpl implements LicensePoolServiceEndPoint {

	private static final Logger logger = Logger.getLogger(LicensePoolServiceEndPointImpl.class);

	private LicensePoolService licensepoolService;
	private String transactionId;
	private LicensePoolConverter licensePoolConverter;
	private LicensepoolJMSUtils licensepoolJMSUtils;

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
	 * @param licensepoolJMSUtils
	 *            the licensepoolJMSUtils to set
	 */
	public void setLicensepoolJMSUtils(LicensepoolJMSUtils licensepoolJMSUtils) {
		this.licensepoolJMSUtils = licensepoolJMSUtils;
	}

	/**
	 * Creates all the components for the given user. Optional components like
	 * email and phone will only be created if they are present.
	 * 
	 * @param licensepool
	 *            the licensepool to be created.
	 * @throws LicensePoolJMSException            
	 */
	public String createLicensePool(CreateLicensePool licensepool) {
		LicensePoolDTO licensePoolDTO = licensePoolConverter.covertCreateRequestToLicensePoolDTO(licensepool);
		String licensePoolId = licensepoolService.createLicensePool(licensePoolDTO);
		try {
			licensePoolDTO.setLicensepoolId(licensePoolId);
			licensepoolJMSUtils.publish(licensePoolDTO, EventTypeType.LP_CREATE);
		} catch (Exception e) {
			throw new LicensePoolJMSException("Failed to publish JMS message.");
		}
		return licensePoolId;
	}

	/**
	 * Update License pool.
	 * 
	 * @param licensepool
	 *            the licensepool to be updated.
	 */
	public String updateLicensePool(UpdateLicensePool licensepool) {
		return licensepoolService.updateLicensePool(licensePoolConverter
				.convertupdateRequestToUpdateLicensePoolDTO(licensepool));
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
	 * @param requestIsDenied
	 *            denies new Subscription of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @return licensePoolId.
	 */
	public String denyNewSubscriptions(String licensePoolId, boolean requestIsDenied, String createdBy) {
		return licensepoolService.denyNewSubscriptions(licensePoolId, requestIsDenied, createdBy);
	}

	/**
	 * Cancels or Revokes a License Pool.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @param isCancel
	 *            cancels a subscription.
	 * 
	 * @return licensepoolId
	 * @throws Exception
	 */
	public String cancel(String licensePoolId, String createdBy, boolean isCancel) {
		LicensePoolDTO licensePoolDTO = new LicensePoolDTO();
		String canceledLicensePoolId = null;
		try {
			canceledLicensePoolId = licensepoolService.cancel(licensePoolId, createdBy, isCancel);
			if (null != canceledLicensePoolId) {
				licensePoolDTO.setLicensepoolId(licensePoolId);
				licensepoolJMSUtils.publish(licensePoolDTO, EventTypeType.LP_CANCEL);
			}
		} catch (RequiredObjectNotFound e) {
			throw new RequiredObjectNotFound("Licensepool with ID: " + licensePoolId + " does not exist.");
		} catch (Exception e) {
			throw new LicensePoolJMSException("Failed to publish JMS message.");
		}
		return canceledLicensePoolId;
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

/**
 * LicensePoolServiceImpl.
 */
package com.pearson.ed.lplc.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.dto.UpdateLicensePoolDTO;
import com.pearson.ed.lplc.exception.ComponentValidationException;
import com.pearson.ed.lplc.exception.LPLCBaseException;
import com.pearson.ed.lplc.exception.LicensePoolExpiredException;
import com.pearson.ed.lplc.exception.LicensePoolForFutureException;
import com.pearson.ed.lplc.exception.LicensePoolUnavailableException;
import com.pearson.ed.lplc.exception.NewSubscriptionsDeniedException;
import com.pearson.ed.lplc.exception.ObjectAlreadyExists;
import com.pearson.ed.lplc.exception.RequiredObjectNotFound;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.stub.api.OrganizationServiceClient;
import com.pearson.ed.lplc.stub.dto.OrganizationDTO;
import com.pearson.ed.lplc.ws.schema.LicensePoolDetails;

/**
 * The LPLC's primary implementation of the licensepool service.
 * 
 * @author Dipali Trivedi
 * 
 */
public class LicensePoolServiceImpl implements LicensePoolService {
	private static final Logger logger = Logger.getLogger(LicensePoolServiceImpl.class);

	/**
	 * Setter.
	 * 
	 * @param licensepoolConverter
	 */
	public void setLicensePoolConverter(LicensePoolConverter licensepoolConverter) {
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

	private LicensePoolConverter licensePoolConverter;
	private LicensePoolDAO licensePoolDAO;
	private OrganizationLPDAO organizationLPDAO;
	private OrganizationServiceClient organizationServiceClient;

	/**
	 * @param organizationServiceClient
	 *            the organizationServiceClient to set
	 */
	public void setOrganizationServiceClient(OrganizationServiceClient organizationServiceClient) {
		this.organizationServiceClient = organizationServiceClient;
	}

	/**
	 * @return the organizationLPDAO
	 */
	public OrganizationLPDAO getOrganizationLPDAO() {
		return organizationLPDAO;
	}

	/**
	 * @param organizationLPDAO
	 *            the organizationLPDAO to set
	 */
	public void setOrganizationLPDAO(OrganizationLPDAO organizationLPDAO) {
		this.organizationLPDAO = organizationLPDAO;
	}

	/**
	 * Create LicensePool Service.
	 * 
	 * @param licensepool
	 * @return String licensepoolId.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String createLicensePool(LicensePoolDTO licensepoolDTO) {
		licensepoolDTO.setMode(LPLCConstants.CREATE_MODE);
		if (licensepoolDTO.getStartDate().after(licensepoolDTO.getEndDate()))
			throw new ComponentValidationException("Start Date can not be greater than End Date");
		LicensePoolMapping licensepoolMapping = licensePoolConverter.convertLicensePoolToLicensePoolMapping(
				licensepoolDTO, null);
		manageOrganizationHierarchyForLP(licensepoolDTO.getOrganizationId(), licensepoolMapping);
		licensePoolDAO.createLicensePool(licensepoolMapping);
		return licensepoolMapping.getLicensepoolId();

	}

	/**
	 * 
	 * @param organizationId
	 *            organizationId.
	 * @param qualifyingOrgs
	 *            qualifyingOrgs.
	 * @return List.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<OrganizationLPMapping> getLicensePoolByOrganizationId(String organizationId, String qualifyingOrgs) {
		int level = 999;
		if (LPLCConstants.QUALIFYING_ORGS_ROOT.equalsIgnoreCase(qualifyingOrgs))
			level = 0;
		return organizationLPDAO.listOrganizationMappingByOrganizationId(organizationId, level);
	}

	/**
	 * Update LicensePool Service.
	 * 
	 * @param licensepool
	 * @return String licensepoolId.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String updateLicensePool(UpdateLicensePoolDTO updateLicensepool) {
		LicensePoolMapping licensepool = licensePoolDAO.findByLicensePoolId(updateLicensepool.getLicensepoolId());
		if (licensepool == null)
			throw new RequiredObjectNotFound("Licensepool doesn't exists with ID: "
					+ updateLicensepool.getLicensepoolId());
		licensePoolConverter.buildLicensepoolMappingFromUpdateLicensepoolDTO(updateLicensepool, licensepool);
		licensePoolDAO.update(licensepool);
		return licensepool.getLicensepoolId();

	}
	
	/**
	 * Cancels or Revokes a License Pool.
	 * 
	 * @param licensePoolId
	 * 			id of the license pool.
	 * @param createdBy
	 * 			the created by.
	 * @param cancel
	 * 			cancels a subscription.
	 * 
	 * @return licensepoolId
	 * @throws RequiredObjectNotFoundException 
	 * 								-when there is no license pool exists.
	 * @throws ObjectAlreadyExists
	 * 					- When already cancelled license pool tried to cancel again.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String cancel(String licensePoolId, String createdBy, int cancel) {
		LicensePoolMapping licensepool = licensePoolDAO.findByLicensePoolId(licensePoolId);
		if (licensepool == null)
			throw new RequiredObjectNotFound("Licensepool with ID: " + licensePoolId +" does not exist.");
		if (cancel == 1) {
			if (licensepool.getIsCancelled().equals(LPLCConstants.IS_CANCELLED_YES) && licensepool.getStatus().trim().equals(LPLCConstants.STATUS_CANCELLED))
				throw new ObjectAlreadyExists("Licensepool with ID: " + licensePoolId +" is already cancelled.");
		} else {
			if (licensepool.getIsCancelled().equals(LPLCConstants.IS_CANCELLED_NO) && licensepool.getStatus().trim().equals(LPLCConstants.STATUS_ACTIVE))
				throw new ObjectAlreadyExists("Licensepool with ID: " + licensePoolId +" is already revoked.");
		}
		
		if (cancel == 1) {
			licensepool.setIsCancelled(LPLCConstants.IS_CANCELLED_YES);
		} else {
			licensepool.setIsCancelled(LPLCConstants.IS_CANCELLED_NO);
		}
		if (createdBy != null) {
			licensepool.setLastUpdatedBy(createdBy);
		}
		licensePoolDAO.update(licensepool);
		return licensepool.getLicensepoolId();
	}

	/**
	 * 
	 * Get License pool to subscribe. Fetches the license pool Id that qualifies
	 * to be used for subscription for the given organizationId and ProductId.
	 * 
	 * @param organizationId
	 *            - id of the organization.
	 * @param productId
	 *            - Id of the product
	 * @return instance of qualifying LicensePoolMapping object
	 * 
	 * @throw NewSubscriptionsDeniedException - throws this exception if new
	 *        subscriptions are denied for the license pool or organization
	 * @throw LicensePoolExpiredException - throws this exception if license
	 *        pool's start and end dates are out of bound of the current date
	 * @throw LicensePoolForFutureException - throws this exception if existing
	 *        license pools are configured for future use and not available
	 *        currently
	 * @throw LicensePoolUnavailableException - if no license pools exist for
	 *        the given product and organization
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public LicensePoolMapping getLicensePoolToSubscribeId(String organizationId, String productId) {
		Date currentDate = new Date();
		List<LicensePoolMapping> qualifyingLicensePools = licensePoolDAO.findOrganizationMappingToSubscribe(
				organizationId, productId, currentDate, true);

		if (qualifyingLicensePools != null && qualifyingLicensePools.size() > 0) {
			return qualifyingLicensePools.get(0);
		}

		// No license pools found, check other conditions
		// Remove date and deny subscription restrictions and check for
		// qualifying license pools
		qualifyingLicensePools = licensePoolDAO.findOrganizationMappingToSubscribe(organizationId, productId, null,
				false);

		if (qualifyingLicensePools == null || qualifyingLicensePools.isEmpty()) {
			throw new LicensePoolUnavailableException("No LicensePool Available For Subscription.");
		}
		boolean lpExpired = false;
		boolean denySubscriptionsSet = false;
		boolean lpForFuture = false;

		for (LicensePoolMapping licensePool : qualifyingLicensePools) {
			if (currentDate.after(licensePool.getStart_date()) && !currentDate.before(licensePool.getEnd_date())) {
				lpExpired = true;
			}

			if (currentDate.before(licensePool.getStart_date())) {
				lpForFuture = true;
			}

			if (licensePool.getDenyManualSubscription() == LPLCConstants.DENY_SUBSCRIPTIONS_TRUE) {
				denySubscriptionsSet = true;
			}
			Set<OrganizationLPMapping> licensePoolOrgMaps = licensePool.getOrganizations();

			if (!lpExpired && !denySubscriptionsSet && !lpForFuture) {
				// Check if there are any denials for subscriptions at
				// Organization level.
				for (OrganizationLPMapping orgLPMapping : licensePoolOrgMaps) {
					if (orgLPMapping.getDenyManualSubscription() == LPLCConstants.DENY_SUBSCRIPTIONS_TRUE) {
						denySubscriptionsSet = true;
						break;
					}
				}
			}

			if (lpForFuture) {
				throw new LicensePoolForFutureException("LicensePool are available for future subscriptions.");
			}
			if (lpExpired && denySubscriptionsSet) {
				throw new LicensePoolExpiredException(
						"License pool(s) for the given organization and product have expired");
			}
			if (lpExpired && !denySubscriptionsSet) {
				throw new LicensePoolExpiredException(
						"License pool(s) for the given organization and product have expired");
			}
			if (denySubscriptionsSet) {
				throw new NewSubscriptionsDeniedException("Existing License pools have new subscriptions denied");
			}
		}
		return null;
	}

	private void manageOrganizationHierarchyForLP(String orgId, LicensePoolMapping licensepool) {

		Set<OrganizationLPMapping> orgList = new HashSet<OrganizationLPMapping>();
		addRootOrg(orgId, licensepool, orgList);
		try {
			List<OrganizationDTO> childOrganizaitons = organizationServiceClient.getChildOrganizations(orgId);

			OrganizationLPMapping organization;
			for (OrganizationDTO organizationDTO : childOrganizaitons) {
				organization = new OrganizationLPMapping();
				organization.setLicensepoolMapping(licensepool);
				organization.setOrganization_id(organizationDTO.getOrgId());
				organization.setOrganization_level(organizationDTO.getOrgLevel());
				organization.setUsed_quantity(0);
				organization.setDenyManualSubscription(licensepool.getDenyManualSubscription());
				organization.setCreatedBy(licensepool.getCreatedBy());
				organization.setCreatedDate(licensepool.getCreatedDate());
				organization.setLastUpdatedBy(licensepool.getLastUpdatedBy());
				organization.setLastUpdatedDate(licensepool.getLastUpdatedDate());
				orgList.add(organization);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, "Exception while tracking child organizations :" + e.getStackTrace());
			throw new LPLCBaseException("Business rule failure: Could not track child organizations.");
		}
		licensepool.setOrganizations(orgList);

	}

	private void addRootOrg(String orgId, LicensePoolMapping licensepool, Set<OrganizationLPMapping> orgList) {
		OrganizationLPMapping organizationLPMapping = new OrganizationLPMapping();
		organizationLPMapping.setOrganization_id(orgId);
		organizationLPMapping.setLicensepoolMapping(licensepool);
		organizationLPMapping.setOrganization_level(0);
		organizationLPMapping.setUsed_quantity(0);
		organizationLPMapping.setDenyManualSubscription(licensepool.getDenyManualSubscription());
		organizationLPMapping.setCreatedBy(licensepool.getCreatedBy());
		organizationLPMapping.setCreatedDate(licensepool.getCreatedDate());
		organizationLPMapping.setLastUpdatedBy(licensepool.getLastUpdatedBy());
		organizationLPMapping.setLastUpdatedDate(licensepool.getLastUpdatedDate());
		orgList.add(organizationLPMapping);
	}

	/*
	 * This function isolate the logic to figure out license status based on
	 * current date, start date and end date.
	 */
	private String getLicenseStatus(LicensePoolDTO licensepoolDTO) {
		Date startDate = licensepoolDTO.getStartDate();
		Date endDate = licensepoolDTO.getEndDate();
		Date currentDate = new Date();
		String licenseType = null;
		if ((currentDate.compareTo(startDate) > 0) && (currentDate.compareTo(endDate) < 0))
			licenseType = "A";
		else if (currentDate.compareTo(endDate) > 0)
			licenseType = "E";
		else if (currentDate.compareTo(startDate) < 0)
			licenseType = "P";
		return licenseType;
	}

	/**
	 * Gets license pool details for the given license pool Id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * 
	 * @return licensePoolDetails license pool details object.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public LicensePoolDetails getLicensePoolDetailsById(String licensePoolId) {
		LicensePoolMapping licensePool = licensePoolDAO.findByLicensePoolId(licensePoolId);
		if (null == licensePool) {
			throw new RequiredObjectNotFound("Licensepool for license pool id: " + licensePoolId + " not found.");
		}
		LicensePoolDetails licensePoolDetails = licensePoolConverter
				.convertLicensePoolMappingToLicensePoolDetails(licensePool);

		return licensePoolDetails;

	}

	/**
	 * This service will find expired license pools that expired yesterday. It
	 * returns list of license pool id.
	 * 
	 * @return List List of license pool id.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<String> findExpiredLicensePool() {
		return licensePoolDAO.findExpiredLicensePool();
	}

	/**
	 * Denies New Subscriptions for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @param deny
	 *            denies new Subscription of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @throws RequiredObjectNotFound
	 *             - throws this exception at required license pool is not
	 *             found.
	 * @return licensePoolId.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String denyNewSubscriptions(String licensePoolId, int deny, String createdBy) {
		LicensePoolMapping licensePool = licensePoolDAO.findByLicensePoolId(licensePoolId);
		if (null == licensePool) {
			throw new RequiredObjectNotFound("Licensepool for license pool id: " + licensePoolId + " not found.");
		}
		licensePool.setDenyManualSubscription(deny);
		if (null != createdBy) {
			licensePool.setLastUpdatedBy(createdBy);
		}

		/*
		 * Set<OrganizationLPMapping> organizationLPMappings =
		 * licensePool.getOrganizations(); for (OrganizationLPMapping
		 * organizationLPMapping : organizationLPMappings) {
		 * organizationLPMapping
		 * .setDenyManualSubscription(LPLCConstants.DENY_SUBSCRIPTIONS_TRUE); if
		 * (null != createdBy) {
		 * organizationLPMapping.setLastUpdatedBy(createdBy); }
		 * licensePool.setOrganizations(organizationLPMappings); }
		 */

		licensePoolDAO.update(licensePool);
		return licensePool.getLicensepoolId();
	}
}
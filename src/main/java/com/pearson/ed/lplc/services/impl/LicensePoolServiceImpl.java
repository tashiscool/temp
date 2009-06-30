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
import com.pearson.ed.lplc.exception.RequiredObjectNotFound;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.stub.OrganizationDTO;
import com.pearson.ed.lplc.stub.OrganizationStub;

/**
 * The LPLC's primary implementation of the licensepool service.
 * 
 * @author Dipali Trivedi
 * 
 */
public class LicensePoolServiceImpl implements LicensePoolService {
	private static final Logger logger = Logger
			.getLogger(LicensePoolServiceImpl.class);

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

	private LicensePoolConverter licensePoolConverter;
	private LicensePoolDAO licensePoolDAO;
	private OrganizationLPDAO organizationLPDAO;

	

	/**
	 * @return the organizationLPDAO
	 */
	public OrganizationLPDAO getOrganizationLPDAO() {
		return organizationLPDAO;
	}

	/**
	 * @param organizationLPDAO the organizationLPDAO to set
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
		LicensePoolMapping licensepoolMapping = licensePoolConverter
				.convertLicensePoolToLicensePoolMapping(licensepoolDTO, null);
		manageOrganizationHierarchyForLP(licensepoolDTO.getOrganizationId(),
				licensepoolMapping);
		licensePoolDAO.createLicensePool(licensepoolMapping);
		return licensepoolMapping.getLicensepoolId();

	}
	/**
	 * 
	 * @param organizationId organizationId.
	 * @param qualifyingOrgs qualifyingOrgs.
	 * @return List.
	 */
	public List<OrganizationLPMapping> getLicensePoolByOrganizationId(String organizationId,
			String qualifyingOrgs){
		int level =999;
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
	public String updateLicensePool(UpdateLicensePoolDTO updateLicensepool){
		LicensePoolMapping licensepool = licensePoolDAO.findByLicensePoolId(updateLicensepool.getLicensepoolId());
		if (licensepool == null)
			throw new RequiredObjectNotFound("Licensepool doesn't exists with ID: "+updateLicensepool.getLicensepoolId());
	    licensePoolConverter.buildLicensepoolMappingFromUpdateLicensepoolDTO(updateLicensepool,
				licensepool);
	   licensePoolDAO.update(licensepool);
	   return licensepool.getLicensepoolId();
		
	}

	
	private void manageOrganizationHierarchyForLP(String orgId,
			LicensePoolMapping licensepool) {

		OrganizationStub orgStub = new OrganizationStub();
		Set<OrganizationLPMapping> orgList = new HashSet<OrganizationLPMapping>();
		addRootOrg(orgId, licensepool, orgList);
		try {

			List<OrganizationDTO> childOrganizaitons = orgStub
					.getChildOrganizaitons(orgId);

			OrganizationLPMapping organization;
			for (OrganizationDTO organizationDTO : childOrganizaitons) {
				organization = new OrganizationLPMapping();
				organization.setLicensepoolMapping(licensepool);
				organization.setOrganization_id(organizationDTO.getOrg_id());
				organization.setOrganization_level(organizationDTO.getOrg_level());
				organization.setUsed_quantity(0);
				organization.setDenyManualSubscription(licensepool
						.getDenyManualSubscription());
				organization.setCreatedBy(licensepool.getCreatedBy());
				organization.setCreatedDate(licensepool.getCreatedDate());
				organization.setLastUpdatedBy(licensepool.getLastUpdatedBy());
				organization.setLastUpdatedDate(licensepool
						.getLastUpdatedDate());
				orgList.add(organization);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR,
					"Exception while tracking child organizations :"
							+ e.getStackTrace());
			throw new LPLCBaseException(
					"Business rule failure: Could not track child organizations.");
		}
		licensepool.setOrganizations(orgList);

	}

	private void addRootOrg(String orgId, LicensePoolMapping licensepool,
			Set<OrganizationLPMapping> orgList) {
		OrganizationLPMapping organizationLPMapping = new OrganizationLPMapping();
		organizationLPMapping.setOrganization_id(orgId);
		organizationLPMapping.setLicensepoolMapping(licensepool);
		organizationLPMapping.setOrganization_level(0);
		organizationLPMapping.setUsed_quantity(0);
		organizationLPMapping.setDenyManualSubscription(licensepool
				.getDenyManualSubscription());
		organizationLPMapping.setCreatedBy(licensepool.getCreatedBy());
		organizationLPMapping.setCreatedDate(licensepool.getCreatedDate());
		organizationLPMapping.setLastUpdatedBy(licensepool.getLastUpdatedBy());
		organizationLPMapping.setLastUpdatedDate(licensepool
				.getLastUpdatedDate());
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
		if ((currentDate.compareTo(startDate) > 0)
				&& (currentDate.compareTo(endDate) < 0))
			licenseType = "A";
		else if (currentDate.compareTo(endDate) > 0)
			licenseType = "E";
		else if (currentDate.compareTo(startDate) < 0)
			licenseType = "P";
		return licenseType;
	}

}
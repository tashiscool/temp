package com.pearson.ed.lplc.services.converter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.common.LPLCErrorMessages;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.dto.UpdateLicensePoolDTO;
import com.pearson.ed.lplc.exception.ComponentValidationException;
import com.pearson.ed.lplc.exception.LPLCBaseException;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrderLineItemLPMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.model.common.LPLCBaseEntity;
import com.pearson.ed.lplc.services.converter.api.LicensePoolConverter;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.LicensePoolByOrganizationId;
import com.pearson.ed.lplc.ws.schema.LicensePoolDetails;
import com.pearson.ed.lplc.ws.schema.LicensePoolToSubscribe;
import com.pearson.ed.lplc.ws.schema.LicensePoolToSubscribeType;
import com.pearson.ed.lplc.ws.schema.LicensepoolsByOrganizationId;
import com.pearson.ed.lplc.ws.schema.OrderLineItemDetails;
import com.pearson.ed.lplc.ws.schema.OrderListType;
import com.pearson.ed.lplc.ws.schema.OrganizationDetails;
import com.pearson.ed.lplc.ws.schema.QualifyingOrganizationListType;
import com.pearson.ed.lplc.ws.schema.StatusType;
import com.pearson.ed.lplc.ws.schema.UpdateLicensePool;

public class LicensePoolConverterImpl implements LicensePoolConverter {
	private static final Logger logger = Logger
			.getLogger(LicensePoolConverterImpl.class);

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
		licensepool.setQuantity(licensepoolMapping.getQuantity());
		licensepool.setStartDate(licensepoolMapping.getStart_date());
		licensepool.setEndDate(licensepoolMapping.getEnd_date());
		licensepool.setOrganizationId(licensepoolMapping.getOrg_id());
		// List<String> productList = new ArrayList<String>();
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
		licensepoolMapping.setDenyManualSubscription(licensepool
				.getDenyManualSubscription());

		licensepoolMapping.setStart_date(licensepool.getStartDate());
		licensepoolMapping.setEnd_date(licensepool.getEndDate());
		licensepoolMapping.setQuantity(licensepool.getQuantity());
		licensepoolMapping.setSource_system(licensepool.getSourceSystem());
		licensepoolMapping.setOrg_id(licensepool.getOrganizationId());
		licensepoolMapping.setProduct_id(licensepool.getProductId());
		licensepoolMapping.setIsCancelled(LPLCConstants.IS_CANCELLED_NO);
		String createdBy = licensepool.getCreatedBy();
		if (StringUtils.isNotBlank(createdBy))
			licensepoolMapping.setCreatedBy(createdBy);

		if (LPLCConstants.CREATE_MODE.equals(mode)) {
			setCreatedValues(licensepoolMapping, licensepool);
		}
		setModifiedValues(licensepoolMapping, licensepool);
		if (StringUtils.isNotEmpty(licensepool.getOrderLineItemId()))
			buildOrderLineItemMapping(licensepool, licensepoolMapping, mode,
				createdBy);

		return licensepoolMapping;
	}

	private void buildOrderLineItemMapping(LicensePoolDTO licensepool,
			LicensePoolMapping licensepoolMapping, String mode, String createdBy) {
		OrderLineItemLPMapping orderLineItem = new OrderLineItemLPMapping();
		orderLineItem.setOrderLineItemId(licensepool.getOrderLineItemId());
		orderLineItem.setLicensepoolMapping(licensepoolMapping);
		if (StringUtils.isNotBlank(createdBy))
			orderLineItem.setCreatedBy(createdBy);

		if (LPLCConstants.CREATE_MODE.equals(mode)) {
			setCreatedValues(orderLineItem, licensepool);
		}
		setModifiedValues(orderLineItem, licensepool);
		licensepoolMapping.getOrderLineItems().add(orderLineItem);
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
		licensepoolDTO.setDenyManualSubscription((createLicensePoolSchemaObj
				.getDenyNewSubscription()==null)?0:createLicensePoolSchemaObj
						.getDenyNewSubscription().intValue());
		licensepoolDTO.setStartDate(createLicensePoolSchemaObj.getStartDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setEndDate(createLicensePoolSchemaObj.getEndDate()
				.toGregorianCalendar().getTime());
		licensepoolDTO.setQuantity(createLicensePoolSchemaObj.getQuantity());
		licensepoolDTO.setOrganizationId(createLicensePoolSchemaObj
				.getOrganizationId());
		licensepoolDTO.setProductId(getProducts(createLicensePoolSchemaObj).get(0));
		licensepoolDTO.setOrderLineItemId((createLicensePoolSchemaObj.getOrderLineItemId()==null)?null:createLicensePoolSchemaObj.getOrderLineItemId());
		licensepoolDTO.setSourceSystem(createLicensePoolSchemaObj
				.getSourceSystem());
		licensepoolDTO.setCreatedBy(createLicensePoolSchemaObj.getCreatedBy());
		return licensepoolDTO;
	}

	private List<String> getProducts(CreateLicensePool licensepool) {
		List<String> newProductIds = new ArrayList<String>();
		List<String> productIds = licensepool.getProductId();
		if (productIds != null) {
			Iterator<String> iterator = productIds.iterator();
			while (iterator.hasNext())
				newProductIds.add(iterator.next());
		}
		return newProductIds;
	}

	/**
	 * Converts updateLicensepoolSchema object to update licensepooldto.
	 *
	 * @param licensepool
	 * @return update licensepool DTO.
	 */
	public UpdateLicensePoolDTO convertupdateRequestToUpdateLicensePoolDTO(
			UpdateLicensePool licensepool) {
		UpdateLicensePoolDTO updateDTO = new UpdateLicensePoolDTO();
		updateDTO.setLicensepoolId(licensepool.getLicensePoolId());
		if (licensepool.getStartDate() != null)
			updateDTO.setStartDate(licensepool.getStartDate()
					.toGregorianCalendar().getTime());
		if (licensepool.getEndDate() != null)
			updateDTO.setEndDate(licensepool.getEndDate().toGregorianCalendar()
					.getTime());
		if (licensepool.getQuantity() != null)
			updateDTO.setQuantity(licensepool.getQuantity());
		if (licensepool.getOrderLineItemId()!=null)
			updateDTO.setOrderLineItem(licensepool.getOrderLineItemId());
		if (licensepool.getUsedLicenses()!=null){
			updateDTO.setUsedLicenses(licensepool.getUsedLicenses().getUsedLicenses());
		    updateDTO.setOrganizationId(licensepool.getUsedLicenses().getOrganizationId());
		}
		return updateDTO;

	}
	
	/**
	 * Convert mapping object to DTO for update licensepool.
	 * @param updateLicensepool UpdateLicensePoolDTO.
	 * @param licensepool mapping object.
	 */
	public void buildLicensepoolMappingFromUpdateLicensepoolDTO(
			UpdateLicensePoolDTO updateLicensepool,
			LicensePoolMapping licensepool) {
		if (updateLicensepool.getStartDate() != null)
	    	licensepool.setStart_date(updateLicensepool.getStartDate());
	    if (updateLicensepool.getEndDate() != null)
	    	licensepool.setEnd_date(updateLicensepool.getEndDate());
	    if (updateLicensepool.getQuantity()!= 0)
	    	licensepool.setQuantity(updateLicensepool.getQuantity());
	    if (updateLicensepool.getUsedLicenses()!=0){
	    	boolean update= false;
	       Set<OrganizationLPMapping> organizations = licensepool.getOrganizations();
	        for (OrganizationLPMapping organizationLPMapping : organizations) {
	        	if (organizationLPMapping.getOrganization_id().trim().equalsIgnoreCase(updateLicensepool.getOrganizationId().trim())){
	        		int usedlicenses = organizationLPMapping.getUsed_quantity()+updateLicensepool.getUsedLicenses();
	        		if (usedlicenses<0 )
	        			throw new ComponentValidationException(LPLCErrorMessages.LICENSEPOOL_CONSUMPTION_NEGATIVE);
	        		organizationLPMapping.setUsed_quantity(usedlicenses);
	        		update=true;
	        	}
	        }
	        if (update==false)
	        	 throw new LPLCBaseException(LPLCErrorMessages.NO_ORGNAIZATION_FOUND_UPDATE_LICENSEPOOL);
	       licensepool.setOrganizations(organizations);
	    }
	    if ((updateLicensepool.getUsedLicenses()==0) && (updateLicensepool.getOrganizationId()==null))
	    {
	    	 Set<OrganizationLPMapping> organizations = licensepool.getOrganizations();
	    	 for (OrganizationLPMapping organizationLPMapping : organizations) {
	    		 organizationLPMapping.setUsed_quantity(0);	    		
	    	}
	    }
	    if (licensepool.getStart_date().after(licensepool.getEnd_date()))
			throw new ComponentValidationException(LPLCErrorMessages.DATE_ERROR);
	    if (updateLicensepool.getOrderLineItem()!= null){
	    	OrderLineItemLPMapping orderLineItem = new OrderLineItemLPMapping();
	    	orderLineItem.setLicensepoolMapping(licensepool);
	    	orderLineItem.setOrderLineItemId(updateLicensepool.getOrderLineItem());
	    	orderLineItem.setCreatedBy(licensepool.getCreatedBy());
	    	orderLineItem.setCreatedDate(LPLCConstants.DEFAULT_DATE);
	    	orderLineItem.setLastUpdatedBy(licensepool.getCreatedBy());
	    	orderLineItem.setLastUpdatedDate(LPLCConstants.DEFAULT_DATE);
	        licensepool.getOrderLineItems().add(orderLineItem);
	    }
	}
	
	/**
	 * This will convert Mapping object to Schema object for GetLicensepoolsByOrganizationId.
	 *
	 * @param licenses list of licensepool mappings.
	 * @return LicensepoolsByOrganizationId LicensepoolsByOrganizationId.
	 */
	public LicensepoolsByOrganizationId convertForGetFromLPMappingToSchema(
			List<OrganizationLPMapping> licenses)  {
		LicensepoolsByOrganizationId schemaList = new LicensepoolsByOrganizationId();
		if (licenses == null)
			return schemaList;
		Iterator<OrganizationLPMapping> iterator = licenses.iterator();
		LicensePoolByOrganizationId licensepoolSchemaObj;
		while (iterator.hasNext()){
			licensepoolSchemaObj = new LicensePoolByOrganizationId();
			OrganizationLPMapping orgLPMapping = iterator.next();
			if (orgLPMapping == null)
				continue;
			licensepoolSchemaObj.getProductId().add(orgLPMapping.getLicensepoolMapping().getProduct_id().toString());
			licensepoolSchemaObj.setLicensePoolId(orgLPMapping.getLicensepoolMapping().getLicensepoolId());
			licensepoolSchemaObj.setRootOrganizationId(orgLPMapping.getLicensepoolMapping().getOrg_id());
			licensepoolSchemaObj.setType(orgLPMapping.getLicensepoolMapping().getType());
			try {
				licensepoolSchemaObj.setStartDate(convertToXMLGregorianCalendar(orgLPMapping.getLicensepoolMapping().getStart_date()));
				licensepoolSchemaObj.setEndDate(convertToXMLGregorianCalendar(orgLPMapping.getLicensepoolMapping().getEnd_date()));
			} catch (DatatypeConfigurationException e) {
				logger.log(Level.ERROR, e.getMessage());
			}
			licensepoolSchemaObj.setQuantity(orgLPMapping.getLicensepoolMapping().getQuantity());
			licensepoolSchemaObj.setUsedLicenses(orgLPMapping.getUsed_quantity());
			/*
			 * Setting the licensepool's DenyManualSubscription value to licensepoolSchemaObj 
			 * explicitly as per the business requirement.
			 */
			licensepoolSchemaObj.setDenyNewSubscription(orgLPMapping.getLicensepoolMapping().getDenyManualSubscription());
			licensepoolSchemaObj.setStatus(getStatusType(orgLPMapping.getLicensepoolMapping().getStatus()));
			schemaList.getLicensePoolByOrganizationId().add(licensepoolSchemaObj);
		}
		return schemaList;
	}

	/**
	 * Convert method to convert LPMapping object to Schema object.
	 * @param organizationLPMapping organizationLPMapping.
	 * @return LicensePoolToSubscribe.
	 */

	public LicensePoolToSubscribe convertForGetLicensepoolToSubscribe(
			LicensePoolMapping organizationLPMapping){
		LicensePoolToSubscribe licensePoolToSubscribe = new LicensePoolToSubscribe();
		LicensePoolToSubscribeType licensepoolSchema = new LicensePoolToSubscribeType();
		try {
			licensepoolSchema.setStartDate(convertToXMLGregorianCalendar(organizationLPMapping.getStart_date()));
			licensepoolSchema.setEndDate(convertToXMLGregorianCalendar(organizationLPMapping.getEnd_date()));
			licensepoolSchema.setLicensePoolId(organizationLPMapping.getLicensepoolId());
		} catch (DatatypeConfigurationException e) {
			logger.log(Level.ERROR, e.getMessage());
		}
		licensePoolToSubscribe.setLicensePoolToSubscribeType(licensepoolSchema);
		return licensePoolToSubscribe;
	}



	/**
	 * Convert LicensePoolMapping object to LicensePoolDetails object.
	 *
	 * @param licensePool
	 *            the LicensePoolMapping object.
	 * @return licensePoolDetails
	 * 			   the LicensePoolDetails object.
	 */
	public LicensePoolDetails convertLicensePoolMappingToLicensePoolDetails(LicensePoolMapping licensePool) {
		LicensePoolDetails licensePoolDetails = new LicensePoolDetails();

		licensePoolDetails.setLicensePoolId(licensePool.getLicensepoolId());
		licensePoolDetails.setType(licensePool.getType());
		licensePoolDetails.setStatus(getStatusType(licensePool.getStatus()));
		licensePoolDetails.setQuantity(licensePool.getQuantity());
		licensePoolDetails.setSourceSystem(licensePool.getSource_system());
		licensePoolDetails.setDenyNewSubscription(licensePool.getDenyManualSubscription());
		try {
			licensePoolDetails.setStartDate(convertToXMLGregorianCalendar(licensePool.getStart_date()));
			licensePoolDetails.setEndDate(convertToXMLGregorianCalendar(licensePool.getEnd_date()));
			if (null != licensePool.getLastUpdatedDate()) {
				licensePoolDetails.setLastUpdatedDate(convertToXMLGregorianCalendar(licensePool.getLastUpdatedDate()));
			}
			if (null != licensePool.getCreatedDate()) {
				licensePoolDetails.setCreatedDate(convertToXMLGregorianCalendar(licensePool.getCreatedDate()));
			}
		} catch (DatatypeConfigurationException e) {
			throw new ComponentValidationException(LPLCErrorMessages.DATE_FORMAT_ERROR);
		}
		if (null != licensePool.getCreatedBy()) {
			licensePoolDetails.setCreatedBy(licensePool.getCreatedBy());
		}
		if (null != licensePool.getLastUpdatedBy()) {
			licensePoolDetails.setLastUpdatedBy(licensePool.getLastUpdatedBy());
		}
		licensePoolDetails.getProductId().add(licensePool.getProduct_id());
		Set<OrganizationLPMapping> qualifyingOrganizations = licensePool.getOrganizations();
		if (qualifyingOrganizations.size() > 0) {
			populateUsedLicenses(qualifyingOrganizations, licensePoolDetails);
			populateQualifyingOrganizations(qualifyingOrganizations, licensePoolDetails);
		}
		Set<OrderLineItemLPMapping> orderLineItems = licensePool.getOrderLineItems();
		if (orderLineItems.size() > 0) {
			populateOrderLineItems(orderLineItems, licensePoolDetails);
		}

		return licensePoolDetails;
	}

	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}

	/**
	 * Populates used licenses.
	 *
	 * @param qualifyingOrganizations
	 *            Qualifying organizations details.
	 * @param licensePoolSummary
	 *            the LicensePoolSummary object.
	 */
	private void populateUsedLicenses(Set<OrganizationLPMapping> qualifyingOrganizations,
			LicensePoolDetails licensePoolDetails) {
		int usedLicenses = 0;

		for (OrganizationLPMapping organizationLPMapping : qualifyingOrganizations) {
			usedLicenses += organizationLPMapping.getUsed_quantity();
		}
		licensePoolDetails.setUsedLicenses(usedLicenses);
	}

	/**
	 * Populates the organization data from the organizationLPMapping.
	 *
	 * @param qualifyingOrganizations
	 *            Qualifying organizations details.
	 * @param licensePoolSummary
	 *            the LicensePoolSummary object.
	 */
	private void populateQualifyingOrganizations(Set<OrganizationLPMapping> qualifyingOrganizations,
			LicensePoolDetails licensePoolDetails) {
		QualifyingOrganizationListType qualifyingOrganizationList = new QualifyingOrganizationListType();

		OrganizationDetails qualifyingOrganizationDetails = null;
		
		for (OrganizationLPMapping organizationLPMapping : qualifyingOrganizations) {
			qualifyingOrganizationDetails = new OrganizationDetails();
			qualifyingOrganizationDetails.setOrganizationId(organizationLPMapping.getOrganization_id());
			qualifyingOrganizationDetails.setUsedLicenses(organizationLPMapping.getUsed_quantity());
			qualifyingOrganizationDetails.setDenyNewSubscription(organizationLPMapping.getDenyManualSubscription());
			qualifyingOrganizationDetails.setOrganizationLevel(organizationLPMapping.getOrganization_level());
			qualifyingOrganizationDetails.setLastUpdatedBy(organizationLPMapping.getLastUpdatedBy());
			qualifyingOrganizationDetails.setCreatedBy(organizationLPMapping.getCreatedBy());
			try {
				qualifyingOrganizationDetails.setLastUpdatedDate(convertToXMLGregorianCalendar(organizationLPMapping
						.getLastUpdatedDate()));
				qualifyingOrganizationDetails.setCreatedDate(convertToXMLGregorianCalendar(organizationLPMapping
						.getCreatedDate()));
			} catch (DatatypeConfigurationException e) {
				throw new ComponentValidationException(LPLCErrorMessages.DATE_FORMAT_ERROR);
			}
			qualifyingOrganizationList.getQualifyingOrganization().add(qualifyingOrganizationDetails);
		}
		licensePoolDetails.setQualifyingOrganizations(qualifyingOrganizationList);
	}

	/**
	 * Populates order line items from orderLineItemLPMapping.
	 *
	 * @param orderLineItems
	 *            OrderLineItems Details.
	 * @param licensePoolSummary
	 *            the LicensePoolSummary object.
	 */
	private void populateOrderLineItems(Set<OrderLineItemLPMapping> orderLineItems,
			LicensePoolDetails licensePoolDetails) {
		
		OrderListType orderList = new OrderListType();
		OrderLineItemDetails orderLineItemDetails = null;
		for (OrderLineItemLPMapping orderLineItemLPMapping : orderLineItems) {
			orderLineItemDetails = new OrderLineItemDetails();
			orderLineItemDetails.setOrderLineItemId(orderLineItemLPMapping.getOrderLineItemId());
			try {
				orderLineItemDetails.setLastUpdatedDate(convertToXMLGregorianCalendar(orderLineItemLPMapping
						.getLastUpdatedDate()));
				orderLineItemDetails.setCreatedDate(convertToXMLGregorianCalendar(orderLineItemLPMapping
						.getCreatedDate()));
			} catch (DatatypeConfigurationException e) {
				throw new ComponentValidationException(LPLCErrorMessages.DATE_FORMAT_ERROR);
			}
			orderLineItemDetails.setLastUpdatedBy(orderLineItemLPMapping.getLastUpdatedBy());
			orderLineItemDetails.setCreatedBy(orderLineItemLPMapping.getCreatedBy());
			orderList.getOrderLineItem().add(orderLineItemDetails);
		}
		licensePoolDetails.getOrderLineItems().add(orderList);
	}

	/**
	 * Gets StatusType value.
	 *
	 * @param status
	 *
	 * @return statusType.
	 */
	private StatusType getStatusType(String status) {
		StatusType statusType = null;
		if (StringUtils.isNotBlank(status)) {
			status = status.trim();
			if (status.compareTo(LPLCConstants.STATUS_PROCESS) == 0) {
				statusType = StatusType.P;
			} else if (status.compareTo(LPLCConstants.STATUS_ACTIVE) == 0) {
				statusType = StatusType.A;
			} else if (status.compareTo(LPLCConstants.STATUS_EXPIRED) == 0) {
				statusType = StatusType.E;
			} else if (status.compareTo(LPLCConstants.STATUS_CANCELLED) == 0) {
				statusType = StatusType.C;
			}
		}
		return statusType;
	}
}

package com.pearson.ed.lp.aggregator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.LicensedProductExceptionMessageCode;
import com.pearson.ed.lp.exception.RequiredObjectNotFoundException;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;

/**
 * Aggregator that collects a {@link LicensePoolResponse} object and one or more {@link OrganizationDisplayNameResponse}
 * objects into a single {@link LicensedProductDataCollection} object for downstream processing.
 * 
 * @author ULLOYNI
 * 
 */
public class LicensedProductsDataAggregator {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductsDataAggregator.class);
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;
	
	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	/**
	 * Aggregate a collection of response objects into a single {@link LicensedProductDataCollection}.
	 * Expected responses in set:
	 * <ul>
	 * <li>one of {@link LicensePoolResponse}</li>
	 * <li>one to many of {@link OrganizationDisplayNamesResponse}</li>
	 * </ul>
	 * @param responses collection of response objects to aggregate
	 * @return {@link LicensedProductDataCollection}
	 */
	public LicensedProductDataCollection aggregateResponse(List<Object> responses) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received messages to aggregate, count: %d", responses.size()));
			LOGGER.debug(responses.toString());
		}

		LicensePoolResponse licensePoolResponse = null;
		List<OrganizationDisplayNamesResponse> orgDisplayNameResponses = 
			new ArrayList<OrganizationDisplayNamesResponse>();

		for (Object response : responses) {
			if (response instanceof LicensePoolResponse) {
				licensePoolResponse = (LicensePoolResponse) response;
			} else if (response instanceof OrganizationDisplayNamesResponse) {
				orgDisplayNameResponses.add((OrganizationDisplayNamesResponse) response);
			}
		}
		
		if(licensePoolResponse == null) {
			LOGGER.error("Required LicensePoolResponse message not received!");
			throw new NullPointerException("Required LicensePoolResponse message not received!");
		}
		
		if(orgDisplayNameResponses.isEmpty() && !licensePoolResponse.getLicensePools().isEmpty()) {
			LOGGER.error("No organization display names found! Not enough information to assemble last response!");
			throw new RequiredObjectNotFoundException(
					exceptionFactory.findExceptionMessage(
							LicensedProductExceptionMessageCode.LP_EXC_0007.toString()));
		}
		
		OrganizationDisplayNamesResponse mergedResponse = merge(orgDisplayNameResponses);
		
		// no license pools found, check if we have organizations
		if(licensePoolResponse.getLicensePools().isEmpty()) {
			// no orgs, throw no organization found error
			if(mergedResponse.getOrganizationDisplayNamesByIds().isEmpty()) {
				throw new InvalidOrganizationException(
						exceptionFactory.findExceptionMessage(
								LicensedProductExceptionMessageCode.LP_EXC_0003.toString()));
			}
		}

		LicensedProductDataCollection collectedData = new LicensedProductDataCollection();
		collectedData.setLicensePools(licensePoolResponse);
		collectedData.setOrganizationDisplayNames(mergedResponse);

		return collectedData;
	}

	/**
	 * Merges multiple {@link OrganizationDisplayNamesResponse} instances into one.
	 * 
	 * @param responses
	 *            {@link List} of {@link OrganizationDisplayNamesResponse}
	 * @return a single, merged {@link OrganizationDisplayNamesResponse} instance
	 */
	private OrganizationDisplayNamesResponse merge(List<OrganizationDisplayNamesResponse> responses) {
		Map<String, String> mergedDisplayNames = new Hashtable<String, String>();
		OrganizationDisplayNamesResponse mergedResponse = new OrganizationDisplayNamesResponse();
		mergedResponse.setOrganizationDisplayNamesByIds(mergedDisplayNames);

		for (OrganizationDisplayNamesResponse response : responses) {
			mergedDisplayNames.putAll(response.getOrganizationDisplayNamesByIds());
		}

		return mergedResponse;
	}

}

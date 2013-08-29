package com.pearson.ed.lp.splitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;

import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdResponse;
import com.pearson.rws.licensepool.doc._2009._04._01.OrganizationDetails;
import com.pearson.rws.user.doc._2008._12._01.OrgRoleType;
import com.pearson.rws.user.doc.v3.GetUsersByAffiliationRequest;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetChildTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetParentTreeByOrganizationIdRequest;

/**
 * Splitter implementation that takes a {@link LicensePoolResponse} message payload and separates out data for
 * {@link ProductEntityIdsRequest}, {@link OrganizationDisplayNameRequest} and {@link OrderLineItemRequest} requests. The original
 * {@link LicensePoolResponse} is returned with the split collection for final aggregation downstream.
 * 
 * @author ULLOYNI
 * 
 */
public class LicensedProductDetailsRequestSplitter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductDetailsRequestSplitter.class);

	/**
	 * Split a single {@link LicensePoolResponse} message into several other messages
	 * for downstream processing.
	 * 
	 * Products of split:
	 * <ul>
	 * <li>{@link OrderLineItemRequest}</li>
	 * <li>{@link OrganizationDisplayNameRequest}</li>
 	 * <li>{@link ProductEntityIdsRequest}</li>
	 * <li>{@link LicensePoolResponse}, original for passthrough</li>
	 * </ul>
	 * 
	 * @param licensePoolResponse {@link LicensePoolResponse} to split
	 * @return {@link List} containing a {@link OrderLineItemRequest}, {@link OrganizationDisplayNameRequest}, 
	 * {@link ProductEntityIdsRequest}, and the original request as a pass through
	 */
	@Splitter
	public List<Object> split(GetLicensePoolDetailsByIdResponse licensePoolResponse) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received message to split");
		}
		
		if(licensePoolResponse == null) {
			LOGGER.error("Required LicensePoolResponse message not received!");
			throw new NullPointerException("Required LicensePoolResponse message not received!");
		}
		 
		GetUsersByAffiliationRequest userRequest = new GetUsersByAffiliationRequest();
		GetProductsByProductEntityIdsRequest getProducts = new GetProductsByProductEntityIdsRequest();
		GetResourcesByProductIdRequest getResource = new GetResourcesByProductIdRequest();
		GetChildTreeByOrganizationIdRequest getChildTree = new GetChildTreeByOrganizationIdRequest();
		GetParentTreeByOrganizationIdRequest getParentTree = new GetParentTreeByOrganizationIdRequest();
		String orgId = null;
		
		for (OrganizationDetails orgDetails : licensePoolResponse
				.getLicensePool().getQualifyingOrganizations()
				.getQualifyingOrganization()) 
		{
			if (orgDetails.getOrganizationLevel() == 0) 
			{
				orgId = orgDetails.getOrganizationId();
			}
		}
		
		userRequest.setOrganizationId(orgId);
		userRequest.setOrgRole(OrgRoleType.CA.name());
		
		Collection<Long> productIds = new ArrayList<Long>();
		for (String productId : licensePoolResponse.getLicensePool().getProductId())
			productIds.add(Long.parseLong(productId));
		getProducts.getProductEntityId().addAll(productIds );
		
		getResource.setRole("Instructor");
		getResource.setProductId(licensePoolResponse.getLicensePool().getProductId().get(0));
		
		getChildTree.setMaxLevel(999);
		getChildTree.setOrganizationId(orgId);
		
		getParentTree.setMaxLevel(999);
		getParentTree.setOrganizationId(orgId);
		
		List<Object> splitSet = new ArrayList<Object>();
		splitSet.add(userRequest);
		splitSet.add(getProducts);
		splitSet.add(getResource);
		splitSet.add(getChildTree);
		splitSet.add(getParentTree);
		splitSet.add(licensePoolResponse);
		return splitSet;
	}

}

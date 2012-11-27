package com.pearson.ed.lp.aggregator;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.OrderLineItemResponse;
import com.pearson.ed.lp.message.OrganizationDisplayNameResponse;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.LicensedProduct;

/**
 * Final response message aggregator for the GetLicensedProductV2 service.
 * 
 * @author ULLOYNI
 *
 */
public class LicensedProductResponseAggregator {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductResponseAggregator.class);

	/**
	 * Aggregate a list of message objects into a single {@link GetLicensedProductResponseElement}.
	 * Expected response messages in provided list:
	 * <ul>
	 * <li>{@link ProductEntityIdsResponse}</li>
	 * <li>{@link OrderLineItemResponse}</li>
	 * <li>{@link LicensePoolResponse}</li>
	 * <li>{@link OrganizationDisplayNameResponse}</li>
	 * </ul>
	 * 
	 * @param responseMessages collection of messages to aggregate
	 * @return {@link GetLicensedProductResponseElement} instance
	 */
	@Aggregator
	public GetLicensedProductResponseElement aggregate(List<Object> responseMessages) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received response messages to aggregate, message count: %d",
					responseMessages.size()));
			LOGGER.debug(responseMessages.toString());
		}

		Map<String, String> orderedISBNsByOrderLineItemIds = new HashMap<String, String>();
		Map<String, String> orgDisplayNameByOrganizationIds = new HashMap<String, String>();
		
		ProductEntityIdsResponse productsData = null;
		OrderLineItemResponse orderData = null;
		LicensePoolResponse licensePoolResponse = null;
		OrganizationDisplayNameResponse organizationData = null;

		for (Object responseMessage : responseMessages) {
			if (responseMessage instanceof ProductEntityIdsResponse) {
				productsData = (ProductEntityIdsResponse) responseMessage;
			} else if (responseMessage instanceof OrderLineItemResponse) {
				orderData = (OrderLineItemResponse) responseMessage;
				if(orderData != null) {
					orderedISBNsByOrderLineItemIds.put(orderData.getOrderLineItemId(), orderData.getOrderedISBN());
				}
			} else if (responseMessage instanceof OrganizationDisplayNameResponse) {
				organizationData = (OrganizationDisplayNameResponse) responseMessage;
				if(organizationData != null) {
					orgDisplayNameByOrganizationIds.put(organizationData.getOrganizationId(), organizationData.getOrganizationDisplayName());
				}
			} else if (responseMessage instanceof LicensePoolResponse) {
				licensePoolResponse = (LicensePoolResponse) responseMessage;
			}
		}

		if(licensePoolResponse == null) {
			LOGGER.error("Required LicensePoolResponse message not received " +
					"for final response generation!");
			throw new NullPointerException("Required LicensePoolResponse message not received " +
					"for final response generation!");
		}
		
		int licensePoolRespCount = licensePoolResponse.getLicensePools().size();
		
		if(orgDisplayNameByOrganizationIds.size() != licensePoolRespCount) {
			LOGGER.error("Required OrganizationDisplayNameResponse messages not received " +
					"for final response generation!");
			throw new NullPointerException("Required OrganizationDisplayNameResponse messages not received " +
					"for final response generation!");
		}
		
		if(productsData == null && !licensePoolResponse.getLicensePools().isEmpty()) {
			LOGGER.error("Required ProductEntityIdsResponse message not received " +
					"for final response generation!");
			throw new NullPointerException("Required ProductEntityIdsResponse message not received " +
					"for final response generation!");
		}
		
		if(orderedISBNsByOrderLineItemIds.size() != licensePoolRespCount) {
			LOGGER.error("Required OrderLineItemResponse message not received " +
					"for final response generation!");
			throw new NullPointerException("Required OrderLineItemResponse message not received " +
					"for final response generation!");
		}
		
		GetLicensedProductResponseElement getLicensedProductResponse = new GetLicensedProductResponseElement();
		List<LicensedProduct> licensedProducts = getLicensedProductResponse.getLicensedProduct();

		for (OrganizationLPMapping licensePool : licensePoolResponse.getLicensePools()) {
			LicensedProduct licensedProduct = new LicensedProduct();
			licensedProducts.add(licensedProduct);

			String organizationId = licensePool.getLicensepoolMapping().getOrg_id();
			Long productEntityId = Long.valueOf(licensePool.getLicensepoolMapping().getProduct_id());
			ProductData productData = productsData.getProductDataByEntityIds().get(productEntityId);
			String firstOrderLineItemId = licensePool.getLicensepoolMapping().getOrderLineItems().iterator().next()
					.getOrderLineItemId();

			licensedProduct.setOrganizationId(organizationId);
			licensedProduct.setLicensePoolId(licensePool.getLicensepoolMapping().getLicensepoolId());
			licensedProduct.setLicensedOrganizationId(organizationId);
			if (orgDisplayNameByOrganizationIds.containsKey(organizationId)) {
				if (orgDisplayNameByOrganizationIds.get(organizationId) != null) {
					licensedProduct.setLicensedOrganizationDisplayName(orgDisplayNameByOrganizationIds.get(
							organizationId));
				}
			}
			licensedProduct.setLicensePoolType(licensePool.getLicensepoolMapping().getType());
			licensedProduct.setLicensePoolStatus(licensePool.getLicensepoolMapping().getStatus().trim());
			licensedProduct.setDenyNewSubscription(licensePool.getLicensepoolMapping().getDenyManualSubscription());

			try {
				licensedProduct.setStartDate(convertToXMLGregorianCalendar(licensePool.getLicensepoolMapping()
						.getStart_date()));
				licensedProduct.setEndDate(convertToXMLGregorianCalendar(licensePool.getLicensepoolMapping()
						.getEnd_date()));
			} catch (DatatypeConfigurationException e) {
				LOGGER.error(e.getMessage());
			}

			licensedProduct.setQuantity(licensePool.getLicensepoolMapping().getQuantity());
			licensedProduct.setUsedLicenses(licensePool.getUsed_quantity());
			licensedProduct.setProductId(productData.getProductId());
			licensedProduct.setProductDisplayName(productData.getDisplayName());

			// optional data
			if (productData.getShortDescription() != null) {
				licensedProduct.setProductShortDescription(productData.getShortDescription());
			}
			if (productData.getLongDescription() != null) {
				licensedProduct.setProductLongDescription(productData.getLongDescription());
			}
			if (productData.getCgProgram() != null) {
				licensedProduct.setCGProgram(productData.getCgProgram());
			}
			if (!productData.getGradeLevels().isEmpty()) {
				licensedProduct.getGradeLevel().addAll(productData.getGradeLevels());
			}
			if (orderedISBNsByOrderLineItemIds.containsKey(firstOrderLineItemId)) {
				if (orderedISBNsByOrderLineItemIds.get(firstOrderLineItemId) != null) {
					licensedProduct.setOrderedISBN(orderedISBNsByOrderLineItemIds.get(firstOrderLineItemId));
				}
			}
		}

		return getLicensedProductResponse;
	}

	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}

}

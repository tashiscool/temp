package com.pearson.ed.lp.aggregator;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;

import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrderLineItemsResponse;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;
import com.pearson.rws.licensedproduct.doc.v2.LicensedProduct;

public class LicensedProductsResponseAggregator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductsResponseAggregator.class);
	
	@Aggregator
	public GetLicensedProductResponseElement aggregateResponse(List<Object> responseMessages) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received response messages to aggregate, message count: %d", 
					responseMessages.size()));
			LOGGER.debug(responseMessages.toString());
		}
		
		ProductEntityIdsResponse productsData = null;
		OrderLineItemsResponse orderData = null;
		LicensedProductDataCollection licensePoolData = null;
		OrganizationDisplayNamesResponse organizationData = null;
		
		for(Object responseMessage : responseMessages) {
			if(responseMessage instanceof ProductEntityIdsResponse) {
				productsData = (ProductEntityIdsResponse)responseMessage;
			} else if(responseMessage instanceof OrderLineItemsResponse) {
				orderData = (OrderLineItemsResponse)responseMessage;
			} else if(responseMessage instanceof LicensedProductDataCollection) {
				licensePoolData = (LicensedProductDataCollection)responseMessage;
				organizationData = licensePoolData.getOrganizationDisplayNames();
			}
		}
		
		// TODO null checks?
		
		GetLicensedProductResponseElement getLicensedProductResponse = new GetLicensedProductResponseElement();
		List<LicensedProduct> licensedProducts = getLicensedProductResponse.getLicensedProduct();

		for(OrganizationLPMapping licensePool : licensePoolData.getLicensePools().getLicensePools()) {
			LicensedProduct licensedProduct = new LicensedProduct();
			licensedProducts.add(licensedProduct);
			
//			<ns:LicensedProduct>
//		    <ns:OrganizationId>{data($OrganizationId)}</ns:OrganizationId>
//		    <ns:LicensePoolId>{data($LicensePoolByOrganizationId[$index]/ns1:LicensePoolId)}</ns:LicensePoolId>
//		    <ns:LicensedOrganizationId>{data($OrganizationId)}</ns:LicensedOrganizationId>
//		    <ns:LicensedOrganizationDisplayName>{data($LicensedOrganizationDisplayName)}</ns:LicensedOrganizationDisplayName>
//		    <ns:LicensePoolType>{data($LicensePoolByOrganizationId[$index]/ns1:Type)}</ns:LicensePoolType>
//		    <ns:LicensePoolStatus>{data($LicensePoolByOrganizationId[$index]/ns1:Status)}</ns:LicensePoolStatus>
//		    <ns:DenyNewSubscription>{data($LicensePoolByOrganizationId[$index]/ns1:DenyNewSubscription)}</ns:DenyNewSubscription>
//		    <ns:StartDate>{data($LicensePoolByOrganizationId[$index]/ns1:StartDate)}</ns:StartDate>
//		    <ns:EndDate>{data($LicensePoolByOrganizationId[$index]/ns1:EndDate)}</ns:EndDate>
//		    <ns:Quantity>{data($LicensePoolByOrganizationId[$index]/ns1:Quantity)}</ns:Quantity>
//		    <ns:UsedLicenses>{data($LicensePoolByOrganizationId[$index]/ns1:UsedLicenses)}</ns:UsedLicenses>
//		    <ns:ProductId>{data($productDetails//ProductId)}</ns:ProductId>
//		    <ns:ProductDisplayName>{data($productDetails//ProductDisplayName)}</ns:ProductDisplayName>
//		    <ns:CGProgram>{data($productDetails//CGAttribute)}</ns:CGProgram>
//		    <ns:OrderedISBN>{data($GetOrderLineItemByIdResponse/ns6:Order/ns6:OrderLineItems/ns6:OrderLine/ns6:OrderedISBN)}</ns:OrderedISBN>
		// </ns:LicensedProduct>
			
			String organizationId = licensePool.getLicensepoolMapping().getOrg_id();
			Long productEntityId = Long.valueOf(licensePool.getLicensepoolMapping().getProduct_id());
			ProductData productData = productsData.getProductDataByEntityIds().get(productEntityId);
			String firstOrderLineItemId = licensePool.getLicensepoolMapping()
				.getOrderLineItems().iterator().next().getOrderLineItemId();

			licensedProduct.setOrganizationId(organizationId);
			licensedProduct.setLicensePoolId(licensePool.getLicensepoolMapping().getLicensepoolId());
			licensedProduct.setLicensedOrganizationId(organizationId);
			licensedProduct.setLicensedOrganizationDisplayName(
					organizationData.getOrganizationDisplayNamesByIds().get(organizationId));
			licensedProduct.setLicensePoolType(licensePool.getLicensepoolMapping().getType());
			licensedProduct.setLicensePoolStatus(licensePool.getLicensepoolMapping().getStatus());
			licensedProduct.setDenyNewSubscription(licensePool.getLicensepoolMapping().getDenyManualSubscription());
			
			try {
				licensedProduct.setStartDate(convertToXMLGregorianCalendar(licensePool.getLicensepoolMapping().getStart_date()));
				licensedProduct.setEndDate(convertToXMLGregorianCalendar(licensePool.getLicensepoolMapping().getEnd_date()));
			} catch (DatatypeConfigurationException e) {
				LOGGER.error(e.getMessage());
			}
			
			licensedProduct.setQuantity(licensePool.getLicensepoolMapping().getQuantity());
			licensedProduct.setUsedLicenses(licensePool.getUsed_quantity());
			licensedProduct.setProductId(productEntityId.toString());
			licensedProduct.setProductDisplayName(productData.getDisplayName());
			
			// optional data
			if(productData.getShortDescription() != null) {
				licensedProduct.setProductShortDescription(productData.getShortDescription());
			}
			if(productData.getLongDescription() != null) {
				licensedProduct.setProductLongDescription(productData.getLongDescription());
			}
			if(productData.getCgProgram() != null) {
				licensedProduct.setCGProgram(productData.getCgProgram());
			}
			if(!productData.getGradeLevels().isEmpty()) {
				licensedProduct.getGradeLevel().addAll(productData.getGradeLevels());
			}
			if(orderData.getOrderedISBNsByOrderLineItemIds().containsKey(firstOrderLineItemId)) {
				licensedProduct.setOrderedISBN(orderData.getOrderedISBNsByOrderLineItemIds().get(firstOrderLineItemId));
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

/**
 * 
 */
package com.pearson.ed.lp;

import static com.pearson.ed.ltg.rumba.common.test.XmlUtils.marshalToSource;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.ws.LicensedProductWebServiceConstants;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProduct;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement;
import com.pearson.rws.licensedproduct.doc.v2.QualifyingLicensePool;
import com.pearson.rws.order.doc._2009._02._09.GetOrderLineItemByIdRequest;
import com.pearson.rws.order.doc._2009._02._09.GetOrderLineItemByIdResponse;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderLineItemsListType;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderLineType;
import com.pearson.rws.order.doc._2009._02._09.ReadOrderType;
import com.pearson.rws.organization.doc._2009._07._01.AttributeKeyType;
import com.pearson.rws.organization.doc._2009._07._01.GetChildTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetOrganizationByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetParentTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.Organization;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationIdRequestType;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeType;
import com.pearson.rws.organization.doc._2009._07._01.ReadAttributeType;
import com.pearson.rws.organization.doc._2009._07._01.ReadAttributesListType;
import com.pearson.rws.product.doc.v2.AttributeType;
import com.pearson.rws.product.doc.v2.AttributesType;
import com.pearson.rws.product.doc.v2.DisplayInfoType;
import com.pearson.rws.product.doc.v2.DisplayInfosType;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponseType;

/**
 * Wrapper around static convenience functions for generating test input and output
 * data for some LicensedProduct client stubs for mocking behavior as well as configuring
 * certain mock objects of internal client stub interfaces.
 * 
 * @author ULLOYNI
 *
 */
public class LicensedProductTestHelper {

	public static enum OrgRequestType {
		ROOT_ONLY, PARENT_TREE, CHILD_TREE;
	}
	
	public static Jaxb2Marshaller marshaller;
	
	/**
	 * Parameterized constructor to allow spring to autowire the static marshaller instance.
	 * Yes, I realize this is a questionable use of Spring, but for the purposes of this helper class
	 * makes utilizing static functions more useful, and this is better than passing a marshaller instance
	 * EVERYTIME through function arguments.
	 * 
	 * @param marshaller Jaxb2Marshaller instance
	 */
	@Autowired(required = true)
	public LicensedProductTestHelper(Jaxb2Marshaller marshaller) {
		LicensedProductTestHelper.marshaller = marshaller;
	}
	
	/**
	 * Generate the mock LicensePoolService implementation with behavior defined by the
	 * input parameters.
	 * 
	 * @param mockLicensePoolService EasyMock instance of LicensePoolService for configuration
	 * @param resultSet a {@link List} of {@link OrganizationLPMapping} instances to return,
	 * 		or null to throw an Exception instead.
	 */
	public static void configureMockLicensePoolService(LicensePoolService mockLicensePoolService, List<OrganizationLPMapping> resultSet) {
		reset(mockLicensePoolService);
		
		if(resultSet == null) {
			expect(mockLicensePoolService.getLicensePoolByOrganizationId(
					isA(String.class), isA(String.class)))
					.andThrow(new RuntimeException("Bad service! No cookie!"));
		} else {
			expect(mockLicensePoolService.getLicensePoolByOrganizationId(
					isA(String.class), isA(String.class)))
					.andReturn(resultSet);
		}
		
		replay(mockLicensePoolService);
	}
	
	/**
	 * Generate the mock LicensePoolService implementation with behavior defined by the
	 * input parameters.
	 * 
	 * @param mockLicensePoolService EasyMock instance of LicensePoolService for configuration
	 * @param toThrow the exception to throw.
	 */
	public static void configureMockLicensePoolService(LicensePoolService mockLicensePoolService, Throwable toThrow) {
		reset(mockLicensePoolService);
		
		expect(mockLicensePoolService.getLicensePoolByOrganizationId(
				isA(String.class), isA(String.class)))
				.andThrow(toThrow);
		
		replay(mockLicensePoolService);
	}

	/**
	 * Generate a source object representing a GetProductsByProductEntityIds request.
	 * 
	 * @param productEntityIds
	 *            collection of entity ids to use for the request
	 * @return {@link Source} instance
	 */
	public static Source generateDummyGetProductRequest(Long... productEntityIds) {
		GetProductsByProductEntityIdsRequest request = new GetProductsByProductEntityIdsRequest();
		request.getProductEntityId().addAll(Arrays.asList(productEntityIds));

		return marshalToSource(marshaller, request);
	}

	/**
	 * Generate a source object representing a GetProductsByProductEntityIds response using the provided seed data.
	 * 
	 * @param dummyDataByDummyProductEntityIds
	 *            seed data to convert into a response
	 * @return {@link Source} instance
	 */
	public static Source generateDummyGetProductResponse(Map<Long, ProductData> dummyDataByDummyProductEntityIds) {
		GetProductsByProductEntityIdsResponse response = new GetProductsByProductEntityIdsResponse();

		for (Entry<Long, ProductData> dummyProduct : dummyDataByDummyProductEntityIds.entrySet()) {
			GetProductsByProductEntityIdsResponseType product = new GetProductsByProductEntityIdsResponseType();
			response.getProduct().add(product);

			ProductData dummyData = dummyProduct.getValue();

			product.setProductEntityId(dummyProduct.getKey());
			product.setProductId(dummyData.getProductId());
			
			if((dummyData.getShortDescription() == null)
					&& (dummyData.getLongDescription() == null)
					&& (dummyData.getDisplayName() == null)
					&& (dummyData.getCgProgram() == null)
					&& ((dummyData.getGradeLevels() == null) || dummyData.getGradeLevels().isEmpty())) {
				continue;
			}

			DisplayInfoType displayInfo = new DisplayInfoType();
			displayInfo.setName(dummyData.getDisplayName());
			displayInfo.setShortDescription(dummyData.getShortDescription());
			displayInfo.setLongDescription(dummyData.getLongDescription());
			product.setDisplayInformation(new DisplayInfosType());
			product.getDisplayInformation().getDisplayInfo().add(displayInfo);

			product.setAttributes(new AttributesType());

			if (dummyData.getCgProgram() != null) {
				String[] cgPrograms = dummyData.getCgProgram().split(" ");
				for(String cgProgram : cgPrograms) {
					AttributeType attribute = new AttributeType();
					product.getAttributes().getAttribute().add(attribute);
					attribute.setAttributeKey(LicensedProductWebServiceConstants.CG_PROGRAM_ATTR_KEY);
					attribute.setAttributeValue(cgProgram);
				}
			}

			for (String gradeLevel : dummyData.getGradeLevels()) {
				AttributeType attribute = new AttributeType();
				product.getAttributes().getAttribute().add(attribute);
				attribute.setAttributeKey(LicensedProductWebServiceConstants.GRADE_LEVEL_ATTR_KEY);
				attribute.setAttributeValue(gradeLevel);
			}
		}

		return marshalToSource(marshaller, response);
	}

	/**
	 * Helper function to generate a dummy GetOrganizationById, GetChildTreeByOrganizationId, or
	 * GetParentTreeByOrganizationId service request.
	 * 
	 * @param orgId
	 *            dummy organization id
	 * @param requestType
	 *            what type of request to generate
	 * @return {@link Source} instance
	 */
	public static Source generateDummyGetOrgRequest(String orgId) {
		GetOrganizationByIdRequest request = new GetOrganizationByIdRequest();
		request.setOrganizationIdRequestType(new OrganizationIdRequestType());
		request.getOrganizationIdRequestType().setOrganizationId(orgId);

		return marshalToSource(marshaller, request);
	}

	/**
	 * Helper function to generate a dummy GetOrganizationById, GetChildTreeByOrganizationId, or
	 * GetParentTreeByOrganizationId service response using the provided seed data.
	 * 
	 * For the GetOrganizationById response only the first entry of the provided map of seed data is used. For the
	 * Get*TreeByOrganizationId responses, the seed data is turned into a depth-only tree with each subsequent entry in
	 * the seed data map being the child/parent organization of the preceding entry.
	 * 
	 * @param dummyOrgDisplayNamesByOrgId
	 *            seed data, map of organization ids to organization display names
	 * @param requestType
	 *            what type of request we need a response to
	 * @return {@link Source} instance
	 */
	public static Source generateDummyGetOrgResponseData(String requestedOrganizationId, String dummyOrgDisplayName) {
		OrganizationResponse response = new OrganizationResponse();

		Organization org = new Organization();
		response.setOrganization(org);

		org.setOrganizationId(requestedOrganizationId);
		org.setAttributes(new ReadAttributesListType());
		ReadAttributeType attribute = new ReadAttributeType();
		org.getAttributes().getAttribute().add(attribute);
		attribute.setAttributeKey(AttributeKeyType.ORG_DISPLAY_NAME);
		attribute.setAttributeValue(dummyOrgDisplayName);
		return marshalToSource(marshaller, response);
	}

	/**
	 * Generate a Source object representing an xml request to the OrderLifeCycle service's GetOrderLineItemById
	 * function.
	 * 
	 * @param requestedOrderLineItemId
	 *            dummy order line item id string
	 * @return {@link Source} instance
	 */
	public static Source generateDummyOrderRequest(String requestedOrderLineItemId) {
		GetOrderLineItemByIdRequest request = new GetOrderLineItemByIdRequest();
		request.setOrderLineItemId(requestedOrderLineItemId);

		return marshalToSource(marshaller, request);
	}

	/**
	 * Generate a Source object representing an (optionally) valid xml response from the OrderLifeCycle service's
	 * GetOrderLineItemById function.
	 * 
	 * @param requestedOrderLineItemId
	 *            dummy order line item id string
	 * @param dummyIsbn
	 *            dummy isbn string
	 * @param valid
	 *            if the response should be valid (i.e. have an OrderedISBN value set)
	 * @return {@link Source} instance
	 */
	public static Source generateDummyOrderResponse(String requestedOrderLineItemId, String dummyIsbn, boolean valid) {
		GetOrderLineItemByIdResponse response = new GetOrderLineItemByIdResponse();
		ReadOrderType order = new ReadOrderType();
		response.setOrder(order);
		order.setOrderId(requestedOrderLineItemId);
		ReadOrderLineItemsListType orderLineItems = new ReadOrderLineItemsListType();
		order.setOrderLineItems(orderLineItems);

		if (valid) {
			ReadOrderLineType orderLineItem = new ReadOrderLineType();
			orderLineItems.getOrderLine().add(orderLineItem);
			orderLineItem.setOrderLineItemId(requestedOrderLineItemId);
			orderLineItem.setOrderedISBN(dummyIsbn);
		}

		return marshalToSource(marshaller, response);
	}

	/**
	 * Generate a Source object representing an (optionally) valid xml response from the OrderLifeCycle service's
	 * GetOrderLineItemById function. This response has multiple line items with only one having the right id.
	 * 
	 * @param requestedOrderLineItemId
	 *            dummy order line item id string
	 * @param dummyIsbn
	 *            dummy isbn string
	 * @param valid
	 *            if the response should be valid (i.e. have an OrderedISBN value set)
	 * @return {@link Source} instance
	 */
	public static Source generateDummyOrderResponseMultipleItems(String requestedOrderLineItemId, String dummyIsbn,
			boolean valid) {
		GetOrderLineItemByIdResponse response = new GetOrderLineItemByIdResponse();
		ReadOrderType order = new ReadOrderType();
		response.setOrder(order);
		order.setOrderId(requestedOrderLineItemId);
		ReadOrderLineItemsListType orderLineItems = new ReadOrderLineItemsListType();
		order.setOrderLineItems(orderLineItems);

		if (valid) {
			ReadOrderLineType orderLineItem = new ReadOrderLineType();
			orderLineItems.getOrderLine().add(orderLineItem);
			orderLineItem.setOrderLineItemId(requestedOrderLineItemId);
			orderLineItem.setOrderedISBN(dummyIsbn);

			orderLineItem = new ReadOrderLineType();
			orderLineItems.getOrderLine().add(orderLineItem);
			orderLineItem.setOrderLineItemId("NOT-" + requestedOrderLineItemId);
		}

		return marshalToSource(marshaller, response);
	}
	
	/**
	 * Generate a Source object representing a valid XML request to the GetLicensedProduct V2 service.
	 * 
	 * @param dummyOrgId dummy organization id
	 * @param qualifyingLicensePool {@link QualifyingLicensePool} value
	 * @return {@link Source} instance
	 */
	public static Source generateDummyGetLicensedProductRequest(String dummyOrgId, QualifyingLicensePool qualifyingLicensePool) {
		GetLicensedProductRequestElement request = new GetLicensedProductRequestElement();
		request.setGetLicensedProduct(new GetLicensedProduct());
		request.getGetLicensedProduct().setOrganizationId(dummyOrgId);
		request.getGetLicensedProduct().setQualifyingLicensePool(qualifyingLicensePool);
		
		return marshalToSource(marshaller, request);
	}
	
	/**
	 * Convenience wrapper around {@link Jaxb2Marshaller#unmarshal(Source)} so we don't need to keep
	 * that class as a dependency everywhere for our tests.
	 * @return
	 */
	public static Object unmarshal(Source source) {
		return marshaller.unmarshal(source);
	}
	
}
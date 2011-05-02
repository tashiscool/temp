package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.exception.LicensedProductExceptionFactory.getFaultMessage;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.LicensedProductExceptionMessageCode;
import com.pearson.ed.lp.exception.ProductNotFoundException;
import com.pearson.ed.lp.exception.RequiredObjectNotFoundException;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;
import com.pearson.rws.product.doc.v2.AttributeType;
import com.pearson.rws.product.doc.v2.DisplayInfoType;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponseType;

/**
 * Web Service Client stub implementation of the {@link ProductLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author VALAGNA
 * 
 */
public class ProductLifeCycleClientImpl implements ProductLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductLifeCycleClientImpl.class);

	public static final String CG_PROGRAM_ATTR_KEY = "CG PROGRAM";
	public static final String GRADE_LEVEL_ATTR_KEY = "LEVEL";

	private WebServiceTemplate serviceClient;

	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * Populate ProductData pojos associated with the given product entity ids by calling the ProductLifeCycle service.
	 * Implements {@link ProductLifeCycleClient#getProductDataByProductEntityIds(ProductEntityIdsRequest)}.
	 * 
	 * @param request
	 *            ProductEntityIdsRequest wrapping a list of product entity ids
	 * @return ProductEntityIdsResponse mapping Display Name strings to associated product entity ids
	 * @throws AbstractRumbaException on service error
	 */
	public ProductEntityIdsResponse getProductDataByProductEntityIds(ProductEntityIdsRequest request)
			throws AbstractRumbaException {

		ProductEntityIdsResponse response = new ProductEntityIdsResponse();
		Map<Long, ProductData> responsePayload = response.getProductDataByEntityIds();

		GetProductsByProductEntityIdsRequest productEntityIdRequest = new GetProductsByProductEntityIdsRequest();
		GetProductsByProductEntityIdsResponse productEntityIdResponse = null;

		// pass through empty set if no entity ids exist to request, valid scenario
		if(request.getProductEntityIds().isEmpty()) {
			return response;
		}
		
		productEntityIdRequest.getProductEntityId().addAll(request.getProductEntityIds());

		try {
			productEntityIdResponse = (GetProductsByProductEntityIdsResponse) serviceClient
					.marshalSendAndReceive(productEntityIdRequest);
		} catch (SoapFaultClientException exception) {
			String faultMessage = getFaultMessage(exception.getWebServiceMessage());
			if(faultMessage.contains("Required object not found")) {
				throw new ProductNotFoundException(
						exceptionFactory.findExceptionMessage(
								LicensedProductExceptionMessageCode.LP_EXC_0004.toString()), 
								request.getProductEntityIds().toArray(), exception);
			} else {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage(), null, exception);
		}

		if (productEntityIdResponse != null) {

			for (GetProductsByProductEntityIdsResponseType responseType : productEntityIdResponse.getProduct()) {
				ProductData productData = new ProductData();
				responsePayload.put(responseType.getProductEntityId(), productData);

				productData.setProductId(responseType.getProductId());

				// quirk of the contract allows each possibility for empty display information
				if ((responseType.getDisplayInformation() == null)
						|| (responseType.getDisplayInformation().getDisplayInfo().isEmpty())) {
					LOGGER.error(String.format("No display information for product with entity id %d", 
							responseType.getProductEntityId()));
					throw new RequiredObjectNotFoundException(
							exceptionFactory.findExceptionMessage(
									LicensedProductExceptionMessageCode.LP_EXC_0006.toString()), 
									new Object[]{responseType.getProductEntityId()});
				}

				DisplayInfoType firstDisplayInfo = responseType.getDisplayInformation().getDisplayInfo().iterator()
						.next();

				// get the first display info name, just like the legacy service
				productData.setDisplayName(firstDisplayInfo.getName());
				productData.setShortDescription(firstDisplayInfo.getShortDescription());
				productData.setLongDescription(firstDisplayInfo.getLongDescription());

				// iterate through the attributes for the ones we want
				if (responseType.getAttributes() != null) {
					for (AttributeType attribute : responseType.getAttributes().getAttribute()) {
						if (attribute.getAttributeKey().equals(CG_PROGRAM_ATTR_KEY)) {
							if(productData.getCgProgram() == null) {
								productData.setCgProgram(attribute.getAttributeValue());
							} else {
								productData.setCgProgram(
										productData.getCgProgram() + " " + attribute.getAttributeValue());
							}
						} else if (attribute.getAttributeKey().equals(GRADE_LEVEL_ATTR_KEY)) {
							productData.addGradeLevel(attribute.getAttributeValue());
						}
					}
				}
			}
		}

		return response;
	}

	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	}

	public void setServiceClient(WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}

	public LicensedProductExceptionFactory getExceptionFactory() {
		return exceptionFactory;
	}

	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}
}

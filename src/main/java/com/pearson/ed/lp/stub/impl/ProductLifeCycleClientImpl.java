package com.pearson.ed.lp.stub.impl;

import java.util.Map;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.ProductData;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;
import com.pearson.rws.product.doc.v2.AttributeType;
import com.pearson.rws.product.doc.v2.DisplayInfoType;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponseType;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;

/**
 * Web Service Client stub implementation of the {@link ProductLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author VALAGNA
 * 
 */
public class ProductLifeCycleClientImpl implements ProductLifeCycleClient {

	public static final String CG_PROGRAM_ATTR_KEY = "CG PROGRAM";
	public static final String GRADE_LEVEL_ATTR_KEY = "LEVEL";

	private WebServiceTemplate serviceClient;

	// private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * Populate ProductData pojos associated with the given product entity ids by calling the ProductLifeCycle service.
	 * 
	 * @param request
	 *            ProductEntityIdsRequest wrapping a list of product entity ids
	 * @return ProductEntityIdsResponse mapping Display Name strings to associated product entity ids
	 */
	public ProductEntityIdsResponse getProductDataByProductEntityIds(ProductEntityIdsRequest request)
			throws AbstractRumbaException {

		ProductEntityIdsResponse response = new ProductEntityIdsResponse();
		Map<Long, ProductData> responsePayload = response.getProductDataByEntityIds();

		GetProductsByProductEntityIdsRequest productEntityIdRequest = new GetProductsByProductEntityIdsRequest();
		GetProductsByProductEntityIdsResponse productEntityIdResponse = null;

		productEntityIdRequest.getProductEntityId().addAll(request.getProductEntityIds());

		try {
			productEntityIdResponse = (GetProductsByProductEntityIdsResponse) serviceClient
					.marshalSendAndReceive(productEntityIdRequest);
		} catch (SoapFaultClientException exception) {
			// TODO
		} catch (Exception exception) {
			// TODO
			// throw new ExternalServiceCallException(exception.getMessage());
		}

		if (productEntityIdResponse != null) {

			for (GetProductsByProductEntityIdsResponseType responseType : productEntityIdResponse.getProduct()) {
				ProductData productData = new ProductData();
				responsePayload.put(responseType.getProductEntityId(), productData);

				productData.setProductId(responseType.getProductId());

				// quirk of the contract allows each possibility for empty display information
				if ((responseType.getDisplayInformation() == null)
						|| (responseType.getDisplayInformation().getDisplayInfo().isEmpty())) {
					// TODO throw exception, we need this data! Display Name is required!!!
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
							if(productData.getCgProgram() != null) {
								productData.setCgProgram(
										productData.getCgProgram() + " " + attribute.getAttributeValue());
							} else {
								productData.setCgProgram(attribute.getAttributeValue());
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
}

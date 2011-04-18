package com.pearson.ed.lp.stub.impl;

import java.util.Map;


import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsResponseType;

/**
 * Web Service Client stub implementation of the {@link ProductLifeCycleClient} interface.
 * Wraps an instance of the {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author VALAGNA
 *
 */
public class ProductLifeCycleClientImpl implements ProductLifeCycleClient{

private WebServiceTemplate serviceClient;
	
//	private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * Get all DisplayNames associated with the given product entity ids by calling the
	 * ProductLifeCycle service.
	 * @param request ProductEntityIdsRequest wrapping a list of product entity ids
	 * @return ProductEntityIdsResponse mapping Display Name strings to associated product entity ids
	 */
	public ProductEntityIdsResponse getDisplayNamesByProductEntityIds(ProductEntityIdsRequest request) 
		throws AbstractRumbaException {
		
		ProductEntityIdsResponse response = new ProductEntityIdsResponse();
		Map<Long,String> responsePayload = response.getProductDisplayNamesByEntityIds();
		
		for(Long productEntityId : request.getProductEntityIds()) {
			
			Long requestPayLoad = productEntityId;
			
			GetProductsByProductEntityIdsResponse productEntityIdResponse = null;
			
			try {
				productEntityIdResponse = (GetProductsByProductEntityIdsResponse)serviceClient
						.marshalSendAndReceive(requestPayLoad);
			} catch (SoapFaultClientException exception) {
				// TODO
			} catch (Exception exception) {
				// TODO
//				throw new ExternalServiceCallException(exception.getMessage());
			}
			
			if(productEntityIdResponse != null) {
				
			    for(GetProductsByProductEntityIdsResponseType responseType : productEntityIdResponse.getProduct()) {
					if(responseType.getProductEntityId().equals(productEntityId)) {
						responsePayload.put(productEntityId, responseType.getDisplayInformation().getDisplayInfo().get(0).toString());
						break;
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

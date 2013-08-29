package com.pearson.ed.lplc.stub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;


import com.pearson.ed.lplc.dto.ProductResource;
import com.pearson.ed.lplc.stub.api.ProductLifeCycleClient;
import com.pearson.rws.product.doc.v2.GetProductDetailsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdRequest;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdResponse;

/**
 * Web Service Client stub implementation of the {@link ProductLifeCycleClient}
 * interface. Wraps an instance of the {@link WebServiceTemplate} class pointing
 * to the ProductLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public class ProductLifeCycleClientImpl implements ProductLifeCycleClient {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ProductLifeCycleClientImpl.class);

    @Autowired
    private WebServiceTemplate productWebServiceClient;
    
//    @Autowired
//    private SubscriptionExceptionService excSvc;
    
    @Autowired
    private WebServiceTemplate serviceClientLicense;
    
    public WebServiceTemplate getServiceClientLicense() {
		return serviceClientLicense;
	}

	public void setServiceClientLicense(WebServiceTemplate serviceClientLicense) {
		this.serviceClientLicense = serviceClientLicense;
	}
    
    public WebServiceTemplate getProductWebServiceClient() {
        return productWebServiceClient;
    }

    public void setProductWebServiceClient(WebServiceTemplate serviceClient) {
        this.productWebServiceClient = serviceClient;
    }

    @Override
    public GetProductDetailsResponse getProductsByProductEntityId(
    		GetProductsByProductEntityIdsRequest request) {
        GetProductDetailsResponse response = null;
        try {
			LOGGER.debug("productWebServiceClient sent" + request.toString() + "\n");
            response = (GetProductDetailsResponse) productWebServiceClient
                    .marshalSendAndReceive(request);
			LOGGER.debug("productWebServiceClient recieved" + response + "\n");
        } catch (SoapFaultClientException e) {
//			LOGGER.error("productWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
//            return new GetProductDetailsResponseException(
//                    excSvc.getSoapFaultMessage(e).replace("{}", request.getProductId()), null,
//                    e);
        } catch (Exception exception) {
			LOGGER.error("productWebServiceClient SoapFaultClientException" + exception.getMessage() + "\n");
//            return new GetProductDetailsResponseException(
//                    exception.getMessage(), null, exception);
        }
        return response;
    }

    @Override
    public GetResourcesByProductIdResponse getResourcesByProductId(
    		GetResourcesByProductIdRequest request) {
        GetResourcesByProductIdResponse response = null;
        try {
			LOGGER.debug("productWebServiceClient sent" + request.toString() + "\n");
			
            response = (GetResourcesByProductIdResponse) productWebServiceClient
                    .marshalSendAndReceive(request);
			LOGGER.debug("productWebServiceClient recieved" + response + "\n");
        } catch (SoapFaultClientException e) {
//			LOGGER.error("productWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
//            return new GetResourcesByProductIdResponseException(
//                    excSvc.getSoapFaultMessage(e).replace("{}", request.getProductId()), null,
//                    e);
        } catch (Exception exception) {
			LOGGER.error("productWebServiceClient SoapFaultClientException" + exception.getMessage() + "\n");
//            return new GetResourcesByProductIdResponseException(
//                    exception.getMessage(), null, exception);
        }
        return response;
    }
    public ProductResource getProductsAndResourcesByProductEntityId(
    		GetProductsByProductEntityIdsRequest request) {
    	ProductResource returner = new ProductResource();
    	GetProductDetailsResponse response = getProductsByProductEntityId(request);
    	GetResourcesByProductIdRequest resourceRequest = new GetResourcesByProductIdRequest();
    	resourceRequest.setRole("Instructor");
    	resourceRequest.setProductId(response.getProduct().getProductId());
		getResourcesByProductId(resourceRequest);
		
		returner.setResources(getResourcesByProductId(resourceRequest));
		returner.setProduct(response);
        
		return returner;
    }
}

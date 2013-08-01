package com.pearson.ed.lplc.stub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.ltg.rumba.webservices.dto.GetProductDetailsRequestWithOrg;
import com.pearson.ed.ltg.rumba.webservices.dto.ProductLicenseWrapper;
import com.pearson.ed.ltg.rumba.webservices.exception.GetProductDetailsResponseException;
import com.pearson.ed.ltg.rumba.webservices.exception.GetResourcesByProductIdResponseException;
import com.pearson.ed.ltg.rumba.webservices.exception.LicensePoolToSubscribeException;
import com.pearson.ed.ltg.rumba.webservices.exception.SubscriptionExceptionService;
import com.pearson.ed.ltg.rumba.webservices.stub.api.ProductLifeCycleClient;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolToSubscribeRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolToSubscribe;
import com.pearson.rws.product.doc.v2.GetProductDetailsRequest;
import com.pearson.rws.product.doc.v2.GetProductDetailsResponse;
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
    
    @Autowired
    private SubscriptionExceptionService excSvc;
    
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
    public Object getProductDetailsById(
            GetProductDetailsRequest request) {
        GetProductDetailsResponse response = null;
        try {
			LOGGER.debug("productWebServiceClient sent" + request.toString() + "\n");
            response = (GetProductDetailsResponse) productWebServiceClient
                    .marshalSendAndReceive(request);
			LOGGER.debug("productWebServiceClient recieved" + response + "\n");
        } catch (SoapFaultClientException e) {
			LOGGER.error("productWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
            return new GetProductDetailsResponseException(
                    excSvc.getSoapFaultMessage(e).replace("{}", request.getProductId()), null,
                    e);
        } catch (Exception exception) {
			LOGGER.error("productWebServiceClient SoapFaultClientException" + exception.getMessage() + "\n");
            return new GetProductDetailsResponseException(
                    exception.getMessage(), null, exception);
        }
        return response;
    }

    @Override
    public Object getResourcesByProductId(
            GetResourcesByProductIdRequest request) {
        GetResourcesByProductIdResponse response = null;
        try {
			LOGGER.debug("productWebServiceClient sent" + request.toString() + "\n");
            response = (GetResourcesByProductIdResponse) productWebServiceClient
                    .marshalSendAndReceive(request);
			LOGGER.debug("productWebServiceClient recieved" + response + "\n");
        } catch (SoapFaultClientException e) {
			LOGGER.error("productWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
            return new GetResourcesByProductIdResponseException(
                    excSvc.getSoapFaultMessage(e).replace("{}", request.getProductId()), null,
                    e);
        } catch (Exception exception) {
			LOGGER.error("productWebServiceClient SoapFaultClientException" + exception.getMessage() + "\n");
            return new GetResourcesByProductIdResponseException(
                    exception.getMessage(), null, exception);
        }
        return response;
    }

	@Override
	public Object getProductAndLicenseDetailsById(
			GetProductDetailsRequestWithOrg request) {
		ProductLicenseWrapper wrapper = new ProductLicenseWrapper();
		try {
			Object prodObj = getProductDetailsById(request.getProductRequest());
			if (prodObj instanceof GetProductDetailsResponse)
			{
				GetProductDetailsResponse product = (GetProductDetailsResponse) prodObj;
				wrapper.setProductRepsonse(product);
				request.getLicensePoolRequest().getGetLicensePoolToSubscribeRequestType()
					.setProductId(String.valueOf(product.getProduct().getProductEntityId()));
	            Object responObject = getLicensePoolToSubscribe(request.getLicensePoolRequest());
	            if (responObject instanceof LicensePoolToSubscribe)
				{
	            	LicensePoolToSubscribe response =(LicensePoolToSubscribe) responObject;
	            	wrapper.setLicenseResponse(response);
				}
	            else
	            {
	            	return responObject;
	            }
			}
			else 
			{
				return prodObj;
			}
			
        }  catch (SoapFaultClientException e) {
			LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
            return new LicensePoolToSubscribeException(
                    excSvc.getSoapFaultMessage(e), null,
                    e);
        }
        catch (Exception exception) {
            return new LicensePoolToSubscribeException(exception.getMessage(), null, exception);
        }
		return wrapper;
	}

	public Object getLicensePoolToSubscribe(
			GetLicensePoolToSubscribeRequest licenseRequest) {
		LicensePoolToSubscribe response = null;
		try {
			LOGGER.debug("licenseRequest sent" + licenseRequest.toString() + "\n");
            response = (LicensePoolToSubscribe) serviceClientLicense
                    .marshalSendAndReceive(licenseRequest);
			LOGGER.debug("licenseRequest recieved" + response + "\n");
        }  catch (SoapFaultClientException e) {
			LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
            return new LicensePoolToSubscribeException(
                    excSvc.getSoapFaultMessage(e), null,
                    e);
        }
        catch (Exception exception) {
            return new LicensePoolToSubscribeException(exception.getMessage(), null, exception);
        }
        return response;
	}
}

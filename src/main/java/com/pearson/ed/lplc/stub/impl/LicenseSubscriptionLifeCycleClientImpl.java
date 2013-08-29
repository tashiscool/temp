package com.pearson.ed.lplc.stub.impl;

import com.pearson.ed.lplc.stub.api.LicenseSubscriptionLifeCycleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdRequest;
import com.pearson.rws.licensepool.doc._2009._04._01.GetLicensePoolDetailsByIdResponse;

/**
 * Web Service Client stub implementation of the {@link SubscriptionLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public class LicenseSubscriptionLifeCycleClientImpl implements LicenseSubscriptionLifeCycleClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseSubscriptionLifeCycleClientImpl.class);
    
    @Autowired
    private WebServiceTemplate subscriptionWebServiceClient;

    @Autowired
    private WebServiceTemplate serviceClientLicense;
    
    public WebServiceTemplate getServiceClientLicense() {
		return serviceClientLicense;
	}

	public void setServiceClientLicense(WebServiceTemplate serviceClientLicense) {
		this.serviceClientLicense = serviceClientLicense;
	}
    
    public WebServiceTemplate getSubscriptionWebServiceClient() {
        return subscriptionWebServiceClient;
    }

    public void setSubscriptionWebServiceClient(WebServiceTemplate serviceClient) {
        this.subscriptionWebServiceClient = serviceClient;
    }
    
	@Override
	public GetLicensePoolDetailsByIdResponse getLicensePoolById(
			GetLicensePoolDetailsByIdRequest request) {
		GetLicensePoolDetailsByIdResponse response = null;
		try {
				
				LOGGER.debug("serviceClientLicense sent" + request.toString() + "\n");
				response = (GetLicensePoolDetailsByIdResponse) serviceClientLicense.marshalSendAndReceive(request);
				if (response != null)
				{
					LOGGER.debug("serviceClientLicense recieved" + response.toString() + "\n");
				}
		} catch (SoapFaultClientException e) {
//			LOGGER.error("licenseRequest SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
//			throw new SubscribeUserB2CResponseElementException(
//					excSvc.getSoapFaultMessage(e), null, e);
		} catch (Exception exception) {
			LOGGER.error("licenseRequest SoapFaultClientException" + exception.getMessage() + "\n");
//			throw new SubscribeUserB2CResponseElementException(
//					exception.getMessage(), null, exception);
		}
		return response;
	}
}

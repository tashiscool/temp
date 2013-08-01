package com.pearson.ed.lp.stub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.ltg.rumba.webservices.exception.SubscribeUserB2CResponseElementException;
import com.pearson.ed.ltg.rumba.webservices.exception.SubscriptionExceptionService;
import com.pearson.ed.ltg.rumba.webservices.stub.api.SubscriptionLifeCycleClient;
import com.pearson.rws.subscriptionevent.doc._2009._06._01.SubscribeUserB2CRequestElement;
import com.pearson.rws.subscriptionevent.doc._2009._06._01.SubscribeUserB2CResponseElement;

/**
 * Web Service Client stub implementation of the {@link SubscriptionLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public class SubscriptionLifeCycleClientImpl implements SubscriptionLifeCycleClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionLifeCycleClientImpl.class);
    
    @Autowired
    private WebServiceTemplate subscriptionWebServiceClient;
    
    @Autowired
    private SubscriptionExceptionService excSvc;
    
    public WebServiceTemplate getSubscriptionWebServiceClient() {
        return subscriptionWebServiceClient;
    }

    public void setSubscriptionWebServiceClient(WebServiceTemplate serviceClient) {
        this.subscriptionWebServiceClient = serviceClient;
    }

    @Override
    public SubscribeUserB2CResponseElement subscribeUser(
            SubscribeUserB2CRequestElement request) {
        SubscribeUserB2CResponseElement response = null;
        try {
			LOGGER.debug("subscriptionWebServiceClient sent" + request.toString() + "\n");
			response = (SubscribeUserB2CResponseElement) subscriptionWebServiceClient
                .marshalSendAndReceive(request);
			LOGGER.debug("subscriptionWebServiceClient recieved" + response.toString() + "\n");
			} 
        catch (SoapFaultClientException e) {
			LOGGER.error("subscriptionWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
            throw new SubscribeUserB2CResponseElementException(
                    excSvc.getSoapFaultMessage(e), null,
                    e);
        }
        catch (Exception exception) {
            throw new SubscribeUserB2CResponseElementException(exception.getMessage(), null, exception);
        }
        return response;
    }

}

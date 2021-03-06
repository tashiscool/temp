package com.pearson.ed.lplc.stub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.lplc.stub.api.UserLifeCycleClient;
import com.pearson.rws.user.doc.v3.GetUsersByAffiliationRequest;
import com.pearson.rws.user.doc.v3.GetUsersByAffiliationResponse;

/**
 * Web Service Client stub implementation of the {@link ProductLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author VALAGNA
 * 
 */
public class UserLifeCycleClientImpl implements UserLifeCycleClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLifeCycleClientImpl.class);

    @Autowired
    private WebServiceTemplate serviceClientUser;
    
//    @Autowired
//    private SubscriptionExceptionService excSvc;
    
    public WebServiceTemplate getServiceClientUser() {
        return serviceClientUser;
    }

    public void setServiceClientUser(WebServiceTemplate serviceClientPayment) {
        this.serviceClientUser = serviceClientPayment;
    }


    @Override
    public GetUsersByAffiliationResponse getUsersByCriteria(GetUsersByAffiliationRequest getUserRequest) {
    	GetUsersByAffiliationResponse response = null;
        try {
			LOGGER.debug("serviceClientUser sent" + getUserRequest.toString() + "\n");
        	response = (GetUsersByAffiliationResponse) serviceClientUser
                .marshalSendAndReceive(getUserRequest);
        	LOGGER.debug("serviceClientUser recieved" + response.toString() + "\n");
        } 
        catch (SoapFaultClientException e) {
//			LOGGER.error("subscriptionWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
//            return new GetUserResponseException(
//                    excSvc.getSoapFaultMessage(e), null,
//                    e);
        }
    catch (Exception exception) {
//    	return new GetUserResponseException(exception.getMessage(), null, exception);
    }
        return response;
    }
    
}

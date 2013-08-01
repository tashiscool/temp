package com.pearson.ed.lp.stub.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.ltg.rumba.webservices.exception.GetPaymentRecordResponseException;
import com.pearson.ed.ltg.rumba.webservices.exception.SubscriptionExceptionService;
import com.pearson.ed.ltg.rumba.webservices.stub.api.PaymentRecordLifeCycleClient;
import com.pearson.rws.payment.doc._2010._09._01.GetPaymentRecordRequest;
import com.pearson.rws.payment.doc._2010._09._01.GetPaymentRecordResponse;

/**
 * Web Service Client stub implementation of the {@link PaymentRecordLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the ProductLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public class PaymentLifeCycleClientImpl implements PaymentRecordLifeCycleClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentLifeCycleClientImpl.class);

    @Autowired
    private WebServiceTemplate serviceClientPayment;
    
    @Autowired
    private SubscriptionExceptionService excSvc;
    
    public WebServiceTemplate getServiceClientPayment() {
        return serviceClientPayment;
    }

    public void setServiceClientPayment(WebServiceTemplate serviceClientPayment) {
        this.serviceClientPayment = serviceClientPayment;
    }

    @Override
    public Object getPaymentRecord(
            GetPaymentRecordRequest paymentRecordId) {
        GetPaymentRecordResponse response = null;
        try {
			LOGGER.debug("serviceClientPayment sent" + paymentRecordId.toString() + "\n");
            response = (GetPaymentRecordResponse) serviceClientPayment
                    .marshalSendAndReceive(paymentRecordId);
			LOGGER.debug("serviceClientPayment recieved" + response.toString() + "\n");
        }  catch (SoapFaultClientException e) {
			LOGGER.error("serviceClientPayment SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
			return new GetPaymentRecordResponseException(
                    excSvc.getSoapFaultMessage(e), null,
                    e);
        }
        catch (Exception exception) {
        	return new GetPaymentRecordResponseException(exception.getMessage(), null, exception);
        }
        return response;
    }
}

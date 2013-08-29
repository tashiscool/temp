package com.pearson.ed.lplc.stub.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.lplc.stub.api.SubscriptionLifeCycleClient;
import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserRequestElement;
import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserResponseElement;

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
    
//    @Autowired
//    private SubscriptionExceptionService excSvc;
    
    @Autowired
    private ThreadPoolExecutor executor;
    
    public WebServiceTemplate getSubscriptionWebServiceClient() {
        return subscriptionWebServiceClient;
    }

    public void setSubscriptionWebServiceClient(WebServiceTemplate serviceClient) {
        this.subscriptionWebServiceClient = serviceClient;
    }

    @Override
    public List<SubscribeUserResponseElement> subscribeUser(
            List<SubscribeUserRequestElement> request) throws InterruptedException, ExecutionException {
    	List<SubscribeUserResponseElement> returner = new ArrayList<SubscribeUserResponseElement>();
    	List<Future<SubscribeUserResponseElement>> futuregets = new ArrayList<Future<SubscribeUserResponseElement>>();
    	for (SubscribeUserRequestElement subscribeUser : request)
    	{
    		futuregets.add(executor.submit(new SubscribeUserFuture(subscribeUser)));
    	}
    	for (Future<SubscribeUserResponseElement> future : futuregets)
    	{
    		returner.add(future.get());
    	}
    	return returner;
    }

    class SubscribeUserFuture implements Callable<SubscribeUserResponseElement>
    {

		public Object getRequest() {
			return request;
		}

		public void setRequest(Object request) {
			this.request = request;
		}

		private Object request;

		public SubscribeUserFuture(Object request) {
			super();
			this.request = request;
		}

		
		@Override
		public SubscribeUserResponseElement call() throws Exception {
			// TODO Auto-generated method stub
			SubscribeUserResponseElement response = null;
	        try {
				LOGGER.debug("subscriptionWebServiceClient sent" + request.toString() + "\n");
				response = (SubscribeUserResponseElement) subscriptionWebServiceClient
	                .marshalSendAndReceive(request);
				LOGGER.debug("subscriptionWebServiceClient recieved" + response.toString() + "\n");
				} 
	        catch (SoapFaultClientException e) {
//				LOGGER.error("subscriptionWebServiceClient SoapFaultClientException" + excSvc.getSoapFaultMessage(e) + "\n");
//	            throw new SubscribeUserB2CResponseElementException(
//	                    excSvc.getSoapFaultMessage(e), null,
//	                    e);
	        }
	        catch (Exception exception) {
//	            throw new SubscribeUserB2CResponseElementException(exception.getMessage(), null, exception);
	        }
	        return response;
		}
    	
    }
}

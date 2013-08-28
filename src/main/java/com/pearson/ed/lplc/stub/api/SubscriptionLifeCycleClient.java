package com.pearson.ed.lplc.stub.api;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserRequestElement;
import com.pearson.rws.subscriptionevent.doc.v2.SubscribeUserResponseElement;


public interface SubscriptionLifeCycleClient {
	/**
	 * This end point method uses marshalling to handle message with a payload.
	 * 
	 * @param subscribeUserRequestElement
	 *            the subscribe user (B2C specific) request.
	 * 
	 * @return SubscribeUserB2CResponseElement
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@ServiceActivator
	 public List<SubscribeUserResponseElement> subscribeUser(
			 List<SubscribeUserRequestElement> request) throws InterruptedException, ExecutionException;

}

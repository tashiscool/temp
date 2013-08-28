package com.pearson.ed.lplc.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.rws.user.doc.v3.GetUsersByAffiliationRequest;
import com.pearson.rws.user.doc.v3.GetUsersByAffiliationResponse;


public interface UserLifeCycleClient {
	@ServiceActivator
	public GetUsersByAffiliationResponse getUsersByCriteria(GetUsersByAffiliationRequest getUserRequest);
}

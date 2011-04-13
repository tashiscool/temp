package com.pearson.ed.lp.stub.mock;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;
import com.pearson.ed.lp.stub.api.ProductLifeCycleClient;

public class MockProductLifeCycleClient implements ProductLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MockProductLifeCycleClient.class);

	@Resource
	private List<Class> hitClasses;

	@Override
	public ProductEntityIdsResponse getDisplayNamesByProductEntityIds(ProductEntityIdsRequest request) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received ProductEntityIdsRequest message");
		}
		hitClasses.add(this.getClass());
		return new ProductEntityIdsResponse();
	}

}

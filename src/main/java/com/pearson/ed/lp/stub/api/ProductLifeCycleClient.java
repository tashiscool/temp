package com.pearson.ed.lp.stub.api;

import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;

public interface ProductLifeCycleClient {
	
	ProductEntityIdsResponse getDisplayNamesByProductEntityIds(ProductEntityIdsRequest request);
}

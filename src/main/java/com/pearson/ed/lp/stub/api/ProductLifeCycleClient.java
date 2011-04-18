package com.pearson.ed.lp.stub.api;

import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;

/**
 * Client stub API for the OrderLifeCycle service.
 * 
 * @author ULLOYNI
 *
 */
public interface ProductLifeCycleClient {
	
	ProductEntityIdsResponse getProductDataByProductEntityIds(ProductEntityIdsRequest request);
}

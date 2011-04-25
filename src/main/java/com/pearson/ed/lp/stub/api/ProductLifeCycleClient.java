package com.pearson.ed.lp.stub.api;

import org.springframework.integration.annotation.ServiceActivator;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsResponse;

/**
 * Client stub API for the OrderLifeCycle service.
 * 
 * @author ULLOYNI
 * 
 */
public interface ProductLifeCycleClient {

	/**
	 * Get request service activator to call the GetProductsByProductEntityIds service function
	 * of the ProductLifeCycleV2 service.
	 * 
	 * @param request {@link ProductEntityIdsRequest}
	 * @return {@link ProductEntityIdsResponse}
	 * @throws AbstractRumbaException on service error
	 */
	@ServiceActivator
	ProductEntityIdsResponse getProductDataByProductEntityIds(ProductEntityIdsRequest request)
		throws AbstractRumbaException;
}

package com.pearson.ed.lplc.stub.api;

import com.pearson.rws.product.doc.v2.GetProductDetailsResponse;
import com.pearson.rws.product.doc.v2.GetProductsByProductEntityIdsRequest;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdRequest;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdResponse;

public interface ProductLifeCycleClient {
	public GetProductDetailsResponse getProductsByProductEntityId(
    		GetProductsByProductEntityIdsRequest request);
	 public GetResourcesByProductIdResponse getResourcesByProductId(
	    		GetResourcesByProductIdRequest request);
}

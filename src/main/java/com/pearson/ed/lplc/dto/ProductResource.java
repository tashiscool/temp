package com.pearson.ed.lplc.dto;

import com.pearson.rws.product.doc.v2.GetProductDetailsResponse;
import com.pearson.rws.product.doc.v2.GetResourcesByProductIdResponse;

public class ProductResource {
	
	GetProductDetailsResponse product;
	
	GetResourcesByProductIdResponse resources;

	public GetProductDetailsResponse getProduct() {
		return product;
	}

	public void setProduct(GetProductDetailsResponse product) {
		this.product = product;
	}

	public GetResourcesByProductIdResponse getResources() {
		return resources;
	}

	public void setResources(GetResourcesByProductIdResponse resources) {
		this.resources = resources;
	}

}

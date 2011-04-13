package com.pearson.ed.lp.ws;

import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.GET_LICENSEDPRODUCT_REQUEST_ELEMENT;
import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.LICENSEDPRODUCT_NAMESPACE;

import org.springframework.integration.annotation.Gateway;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductRequestElement;
import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;

/**
 * LicensedProduct service endpoints for handling JAXB2 requests/responses.
 * 
 * @author ULLOYNI
 *
 */
@Endpoint
public interface MarshallingLicensedProductServiceEndpoint {
	
	@PayloadRoot(localPart = GET_LICENSEDPRODUCT_REQUEST_ELEMENT, namespace = LICENSEDPRODUCT_NAMESPACE)
	@Gateway
	public GetLicensedProductResponseElement getLicensedProducts(GetLicensedProductRequestElement request);
	
}

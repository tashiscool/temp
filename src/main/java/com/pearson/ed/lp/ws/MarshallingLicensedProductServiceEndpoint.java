package com.pearson.ed.lp.ws;

import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.GET_LICENSEDPRODUCT_REQUEST_ELEMENT;
import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.LICENSEDPRODUCT_V2_NAMESPACE;

import org.springframework.integration.annotation.Gateway;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
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

	/**
	 * Service endpoint for GetLicensedProductV2 service.
	 * 
	 * @param request {@link GetLicensedProductRequestElement}
	 * @return {@link GetLicensedProductResponseElement}
	 */
	@PayloadRoot(localPart = GET_LICENSEDPRODUCT_REQUEST_ELEMENT, namespace = LICENSEDPRODUCT_V2_NAMESPACE)
	@Gateway
	@ResponsePayload
	GetLicensedProductResponseElement getLicensedProducts(@RequestPayload GetLicensedProductRequestElement request)
		throws AbstractRumbaException;

}

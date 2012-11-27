package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.exception.LicensedProductExceptionFactory.getFaultMessage;
import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.ORG_DISPLAY_NAME_ATTR_KEY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.LicensedProductExceptionMessageCode;
import com.pearson.ed.lp.message.OrganizationDisplayNameRequest;
import com.pearson.ed.lp.message.OrganizationDisplayNameResponse;
import com.pearson.ed.lp.stub.api.OrganizationLifeCycleClient;
import com.pearson.rws.organization.doc._2009._07._01.GetOrganizationByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationIdRequestType;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationResponse;
import com.pearson.rws.organization.doc._2009._07._01.ReadAttributeType;

/**
 * Web Service Client stub implementation of the {@link OrganizationLifeCycleClient} interface. Wraps an instance of the
 * {@link WebServiceTemplate} class pointing to the OrganizationLifeCycle service.
 * 
 * @author VALAGNA
 * 
 */
public class OrganizationLifeCycleClientImpl implements OrganizationLifeCycleClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationLifeCycleClientImpl.class);

	private WebServiceTemplate serviceClient;
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	public void setServiceClient(WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}

	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	}

	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}


	/**
	 * Get Display Name associated with the given organization id by calling the OrganizationLifeCycle service.
	 * Implements {@link OrganizationLifeCycleClient#getOrganizationDisplayName(OrganizationDisplayNameRequest)}.
	 * 
	 * @param request
	 *            OrganizationDisplayNameRequest that contains organization id
	 * @return OrganizationDisplayNameResponse display name for the given organization id
	 * @throws AbstractRumbaException on service error
	 */
	public OrganizationDisplayNameResponse getOrganizationDisplayName(OrganizationDisplayNameRequest request)
			throws AbstractRumbaException {

		String organizationId = request.getOrganizationId();
		OrganizationDisplayNameResponse response = new OrganizationDisplayNameResponse();
		response.setOrganizationId(organizationId);

		GetOrganizationByIdRequest getOrganizationbyIdRequest = new GetOrganizationByIdRequest();
		OrganizationIdRequestType organizationIdRequestType = new OrganizationIdRequestType();
		organizationIdRequestType.setOrganizationId(organizationId);
		getOrganizationbyIdRequest.setOrganizationIdRequestType(organizationIdRequestType);
		OrganizationResponse organizationResponse = null;

		try {
			organizationResponse = (OrganizationResponse) serviceClient
					.marshalSendAndReceive(getOrganizationbyIdRequest);
		} catch (SoapFaultClientException exception) {
			String faultMessage = getFaultMessage(exception.getWebServiceMessage());
			if(faultMessage.contains("No Organization with Organization Id")) {
				throw new InvalidOrganizationException(
						exceptionFactory.findExceptionMessage(
								LicensedProductExceptionMessageCode.LP_EXC_0002), 
						new Object[]{organizationId}, exception);
			} else {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage(), null, exception);
		}

		if ((organizationResponse != null) && (organizationResponse.getOrganization().getAttributes() != null)) {
			for (ReadAttributeType attribute : organizationResponse.getOrganization().getAttributes().getAttribute()) {
				if (attribute.getAttributeKey().value().equals(ORG_DISPLAY_NAME_ATTR_KEY)) {
					response.setOrganizationDisplayName(attribute.getAttributeValue());
					break;
				}
			}
		}

		return response;
	}

}

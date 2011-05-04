package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.exception.LicensedProductExceptionFactory.getFaultMessage;
import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.ORG_DISPLAY_NAME_ATTR_KEY;

import java.util.Map;

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
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.stub.api.OrganizationLifeCycleClient;
import com.pearson.rws.organization.doc._2009._07._01.GetChildTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetOrganizationByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetParentTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationIdRequestType;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeType;
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

	/**
	 * Get all DisplayNames associated with the given organization id by calling the OrganizationLifeCycle service.
	 * Implements {@link OrganizationLifeCycleClient#getOrganizationDisplayName(String)}.
	 * 
	 * @param organizationId organization id string
	 * @return {@link OrganizationDisplayNamesResponse}
	 * @throws AbstractRumbaException on service error
	 */
	public OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId)
			throws AbstractRumbaException {

		OrganizationDisplayNamesResponse response = new OrganizationDisplayNamesResponse();
		Map<String, String> responsePayload = response.getOrganizationDisplayNamesByIds();

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
								LicensedProductExceptionMessageCode.LP_EXC_0003.toString()), 
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
					responsePayload.put(organizationResponse.getOrganization().getOrganizationId(),
							attribute.getAttributeValue());
				}
			}
		}

		return response;
	}

	/**
	 * Get all ChildTreeDisplayNames associated with the given organization id by calling the OrganizationLifeCycle
	 * service.
	 * Implements {@link OrganizationLifeCycleClient#getChildTreeDisplayNamesByOrganizationId(String)}.
	 * 
	 * @param organizationId organization id string
	 * @return {@link OrganizationDisplayNamesResponse}
	 * @throws AbstractRumbaException on service error
	 */

	public OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId)
			throws AbstractRumbaException {

		OrganizationDisplayNamesResponse response = new OrganizationDisplayNamesResponse();
		Map<String, String> responsePayload = response.getOrganizationDisplayNamesByIds();

		GetChildTreeByOrganizationIdRequest getChildTreeRequest = new GetChildTreeByOrganizationIdRequest();
		getChildTreeRequest.setOrganizationId(organizationId);
		OrganizationTreeResponse treeResponse = null;

		try {
			treeResponse = (OrganizationTreeResponse) serviceClient.marshalSendAndReceive(getChildTreeRequest);
		} catch (SoapFaultClientException exception) {
			String faultMessage = getFaultMessage(exception.getWebServiceMessage());
			if(faultMessage.contains("Invalid Organization Id")) {
				throw new InvalidOrganizationException(
						exceptionFactory.findExceptionMessage(
								LicensedProductExceptionMessageCode.LP_EXC_0003.toString()), 
						new Object[]{organizationId}, exception);
			} else if(faultMessage.contains("No child organizations found")) {
				// consume the exception, this is an acceptable situation
				LOGGER.info(String.format("No child organizations found for organization id: %s", organizationId));
			} else {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage(), null, exception);
		}

		if (treeResponse != null) {
			// dive into the response tree, hitting every node
			parseOrganizationTree(treeResponse.getOrganization(), responsePayload);
		}

		return response;
	}

	/**
	 * Get all ParentTreeDisplayNames associated with the given organization id by calling the OrganizationLifeCycle
	 * service.
	 * Implements {@link OrganizationLifeCycleClient#getParentTreeDisplayNamesByOrganizationId(String)}.
	 * 
	 * @param organizationId organization id string
	 * @return {@link OrganizationDisplayNamesResponse}
	 * @throws AbstractRumbaException on service error
	 */
	public OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId)
			throws AbstractRumbaException {

		OrganizationDisplayNamesResponse response = new OrganizationDisplayNamesResponse();
		Map<String, String> responsePayload = response.getOrganizationDisplayNamesByIds();

		GetParentTreeByOrganizationIdRequest getParentTreeRequest = new GetParentTreeByOrganizationIdRequest();
		getParentTreeRequest.setOrganizationId(organizationId);
		OrganizationTreeResponse treeResponse = null;

		try {
			treeResponse = (OrganizationTreeResponse) serviceClient.marshalSendAndReceive(getParentTreeRequest);
		} catch (SoapFaultClientException exception) {
			String faultMessage = getFaultMessage(exception.getWebServiceMessage());
			if(faultMessage.contains("Invalid Organization Id")) {
				throw new InvalidOrganizationException(
						exceptionFactory.findExceptionMessage(
								LicensedProductExceptionMessageCode.LP_EXC_0003.toString()), 
						new Object[]{organizationId}, exception);
			} else if(faultMessage.contains("No parent organizations found")) {
				// consume the exception, this is an acceptable situation
				LOGGER.info(String.format("No parent organizations found for organization id: %s", organizationId));
			} else {
				throw new ExternalServiceCallException(exception.getMessage(), null, exception);
			}
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage(), null, exception);
		}

		if (treeResponse != null) {
			// dive into the response tree, hitting every node
			parseOrganizationTree(treeResponse.getOrganization(), responsePayload);
		}

		return response;
	}

	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	}

	public void setServiceClient(WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}

	/**
	 * Recursive funtion to parse an entire OrganizationTreeType result tree and populate the provided result map with
	 * the organization id and name values as the keys and values in the map.
	 * 
	 * @param orgTreeNode
	 *            organization tree to parse
	 * @param resultPayload
	 *            result map to populate
	 */
	private void parseOrganizationTree(OrganizationTreeType orgTreeNode, Map<String, String> resultPayload) {
		resultPayload.put(orgTreeNode.getOrganizationId(), orgTreeNode.getName());

		if (!orgTreeNode.getOrganization().isEmpty()) {
			for (OrganizationTreeType childNode : orgTreeNode.getOrganization()) {
				parseOrganizationTree(childNode, resultPayload);
			}
		}
	}

	public LicensedProductExceptionFactory getExceptionFactory() {
		return exceptionFactory;
	}

	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

}

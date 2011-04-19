package com.pearson.ed.lp.stub.impl;

import java.util.Map;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
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

	public static final String ORG_DISPLAY_NAME_ATTR_KEY = "ORG_DISPLAY_NAME";

	private WebServiceTemplate serviceClient;

	/**
	 * Get all DisplayNames associated with the given organization id by calling the OrganizationLifeCycle service.
	 * 
	 * @param request
	 *            OrganizationId
	 * @return OrganizationDisplayNamesResponse mapping Display Name strings to associated organization id
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
			// TODO
		} catch (Exception exception) {
			// TODO
			// throw new ExternalServiceCallException(exception.getMessage());
		}

		if ((organizationResponse != null) && (organizationResponse.getOrganization().getAttributes() != null)) {
			for (ReadAttributeType attribute : organizationResponse.getOrganization().getAttributes()
					.getAttribute()) {
				if(attribute.getAttributeKey().value().equals(ORG_DISPLAY_NAME_ATTR_KEY)) {
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
	 * 
	 * @param request
	 *            OrganizationId
	 * @return OrganizationDisplayNamesResponse mapping Child Tree Display Name strings to associated organization id
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
			// TODO
		} catch (Exception exception) {
			// TODO
			// throw new ExternalServiceCallException(exception.getMessage());
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
	 * 
	 * @param request
	 *            OrganizationId
	 * @return OrganizationDisplayNamesResponse mapping Parent Tree Display Name strings to associated organization id
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
			// TODO
		} catch (Exception exception) {
			// TODO
			// throw new ExternalServiceCallException(exception.getMessage());
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
	 * Recursive funtion to parse an entire OrganizationTreeType result tree and populate the provided
	 * result map with the organization id and name values as the keys and values in the map.
	 * 
	 * @param orgTreeNode organization tree to parse
	 * @param resultPayload result map to populate
	 */
	private void parseOrganizationTree(OrganizationTreeType orgTreeNode, Map<String,String> resultPayload) {
		resultPayload.put(orgTreeNode.getOrganizationId(), orgTreeNode.getName());
		
		if(!orgTreeNode.getOrganization().isEmpty()) {
			for(OrganizationTreeType childNode : orgTreeNode.getOrganization()) {
				parseOrganizationTree(childNode, resultPayload);
			}
		}
	}

}

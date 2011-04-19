package com.pearson.ed.lp.stub.impl;
import java.util.Map;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lp.stub.api.OrganizationLifeCycleClient;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationResponse;
import com.pearson.rws.organization.doc._2009._07._01.ReadAttributeType;
import com.pearson.rws.organization.doc._2009._07._01.GetOrganizationByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetChildrenByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetParentsByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationIdRequestType;

/**
 * Web Service Client stub implementation of the {@link OrganizationLifeCycleClient} interface.
 * Wraps an instance of the {@link WebServiceTemplate} class pointing to the OrganizationLifeCycle service.
 * 
 * @author VALAGNA
 *
 */
public class OrganizationLifeCycleClientImpl implements OrganizationLifeCycleClient{
	
	public static final String ORG_DISPLAY_NAME_ATTR_KEY = "ORG_DISPLAY_NAME";

	private WebServiceTemplate serviceClient;
	
	/**
	 * Get all DisplayNames associated with the given organization id by calling the
	 * OrganizationLifeCycle service.
	 * @param request OrganizationId 
	 * @return OrganizationDisplayNamesResponse mapping Display Name strings to associated organization id
	 */
	
	public OrganizationDisplayNamesResponse getOrganizationDisplayName(String organizationId) 
		throws AbstractRumbaException {
		
		OrganizationDisplayNamesResponse response = new OrganizationDisplayNamesResponse();
		Map<String,String> responsePayload = response.getOrganizationDisplayNamesByIds();
		
			GetOrganizationByIdRequest getOrganizationbyIdRequest = new GetOrganizationByIdRequest();
			OrganizationIdRequestType organizationIdRequestType = new OrganizationIdRequestType();
			organizationIdRequestType.setOrganizationId(organizationId);
			getOrganizationbyIdRequest.setOrganizationIdRequestType(organizationIdRequestType);
			OrganizationResponse organizationResponse = null;
		
			try {
				organizationResponse = (OrganizationResponse)serviceClient
						.marshalSendAndReceive(getOrganizationbyIdRequest);
			} catch (SoapFaultClientException exception) {
				// TODO
			} catch (Exception exception) {
				// TODO
//				throw new ExternalServiceCallException(exception.getMessage());
			}
						
			if((organizationResponse != null) && (organizationResponse.getOrganization().getAttributes() != null)) {
				for(ReadAttributeType organizationDisplayName : organizationResponse.getOrganization().getAttributes().getAttribute()) {
					responsePayload.put(organizationResponse.getOrganization().getOrganizationId(), organizationDisplayName.getAttributeValue());
				}			 
			} 
			
		return response;
	}
	
	/**
	 * Get all ChildTreeDisplayNames associated with the given organization id by calling the
	 * OrganizationLifeCycle service.
	 * @param request OrganizationId 
	 * @return OrganizationDisplayNamesResponse mapping Child Tree Display Name strings to associated organization id
	 */
	
	public OrganizationDisplayNamesResponse getChildTreeDisplayNamesByOrganizationId(String organizationId) 
		throws AbstractRumbaException {
		
		OrganizationDisplayNamesResponse response = new OrganizationDisplayNamesResponse();
		Map<String,String> responsePayload = response.getOrganizationDisplayNamesByIds();
		
			GetChildrenByIdRequest getChildrenbyIdRequest = new GetChildrenByIdRequest();
			OrganizationIdRequestType organizationIdRequestType = new OrganizationIdRequestType();
			organizationIdRequestType.setOrganizationId(organizationId);
			getChildrenbyIdRequest.setOrganizationIdRequestType(organizationIdRequestType);
			OrganizationResponse organizationResponse = null;
		
			try {
				organizationResponse = (OrganizationResponse)serviceClient
						.marshalSendAndReceive(getChildrenbyIdRequest);
			} catch (SoapFaultClientException exception) {
				// TODO
			} catch (Exception exception) {
				// TODO
//				throw new ExternalServiceCallException(exception.getMessage());
			}
						
			if((organizationResponse != null) && (organizationResponse.getOrganization().getAttributes() != null)) {
				 for(ReadAttributeType organizationDisplayName : organizationResponse.getOrganization().getAttributes().getAttribute()) {
					responsePayload.put(organizationResponse.getOrganization().getOrganizationId(), organizationDisplayName.getAttributeValue());
				}
							 
			} 
			
		return response;
	}
	
	/**
	 * Get all ParentTreeDisplayNames associated with the given organization id by calling the
	 * OrganizationLifeCycle service.
	 * @param request OrganizationId 
	 * @return OrganizationDisplayNamesResponse mapping Parent Tree Display Name strings to associated organization id
	 */
	
	public OrganizationDisplayNamesResponse getParentTreeDisplayNamesByOrganizationId(String organizationId) 
		throws AbstractRumbaException {
		
		OrganizationDisplayNamesResponse response = new OrganizationDisplayNamesResponse();
		Map<String,String> responsePayload = response.getOrganizationDisplayNamesByIds();
			
			GetParentsByIdRequest getParentsbyIdRequest = new GetParentsByIdRequest();
			OrganizationIdRequestType organizationIdRequestType = new OrganizationIdRequestType();
			organizationIdRequestType.setOrganizationId(organizationId);
			getParentsbyIdRequest.setOrganizationIdRequestType(organizationIdRequestType);
			OrganizationResponse organizationResponse = null;
		
			try {
				organizationResponse = (OrganizationResponse)serviceClient
						.marshalSendAndReceive(getParentsbyIdRequest);
			} catch (SoapFaultClientException exception) {
				// TODO
			} catch (Exception exception) {
				// TODO
//				throw new ExternalServiceCallException(exception.getMessage());
			}
						
			if((organizationResponse != null) && (organizationResponse.getOrganization().getAttributes() != null)) {
				 for(ReadAttributeType organizationDisplayName : organizationResponse.getOrganization().getAttributes().getAttribute()) {
					 responsePayload.put(organizationResponse.getOrganization().getOrganizationId(), organizationDisplayName.getAttributeValue());
				}
							 
			} 
		return response;
	}

	public WebServiceTemplate getServiceClient() {
		return serviceClient;
	} 

	public void setServiceClient(WebServiceTemplate serviceClient) {
		this.serviceClient = serviceClient;
	}

}

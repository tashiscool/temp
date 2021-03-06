package com.pearson.ed.lplc.stub.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.axiom.soap.SOAPFaultText;
import org.apache.log4j.Logger;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.axiom.AxiomSoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.pearson.ed.lplc.stub.api.OrganizationServiceClient;
import com.pearson.ed.lplc.stub.dto.OrganizationDTO;
import com.pearson.rws.organization.doc._2009._07._01.GetChildTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetParentTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeType;

/**
 * This class is a Client stub class and implements methods to be invoked on Organization Life Cycle service.
 * 
 * @author vtirura
 * 
 */
public class OrganizationServiceClientImpl implements OrganizationServiceClient {

	private static final Logger logger = Logger.getLogger(OrganizationServiceClientImpl.class);

	private WebServiceTemplate webServiceTemplate;

	/**
	 * @return the webServiceTemplate
	 */
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	/**
	 * @param webServiceTemplate
	 *            the webServiceTemplate to set
	 */
	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	/**
	 * Method to get the child organizations for supplied orgId.
	 * 
	 * @param organizationId
	 * @return List of OrganizationDTOs.
	 */
	@Override
	public List<OrganizationDTO> getChildOrganizations(String organizationId) {

		List<OrganizationDTO> organizationDTOList = new ArrayList<OrganizationDTO>();
		String faultMessage = null;
		try {
			OrganizationTreeResponse organizationTreeResponse = getOrgHierarchy(organizationId, true);
			OrganizationTreeType organizationTreeType = organizationTreeResponse.getOrganization();
			OrganizationDTO organizationDTO = new OrganizationDTO();

			organizationDTO.setOrgId(organizationTreeType.getOrganizationId());
			organizationDTO.setOrgLevel(organizationTreeType.getLevel());
			organizationDTOList.addAll(getOrganizationChildTree(organizationTreeType.getOrganization(),
					organizationDTOList));
			organizationDTOList.add(organizationDTO);

		} catch (SoapFaultClientException soapFaultClientException) {
			// FIXME: These needs to be removed once error codes are defined.
			faultMessage = getFaultMessage(soapFaultClientException.getWebServiceMessage());
			if (faultMessage.contains("Invalid Organization Id")) {
				throw new OrganizationNotValidException("No Organization found for Organization ID: " + organizationId);
			}
			if (faultMessage.contains("No child organizations found for organization Id ")) {
				return organizationDTOList;
			}
			throw new ExternalServiceCallException(soapFaultClientException.getMessage());
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage());
		}

		return organizationDTOList;
	}

	protected OrganizationTreeResponse getOrgHierarchy(String organizationId, boolean child) throws SoapFaultClientException {
		Object request = null;
		if (child)
			request = getChildTreeByOrganizationIdRequest(organizationId);
		else
			request = getParentTreeByOrganizationIdRequest(organizationId);
		OrganizationTreeResponse organizationTreeResponse = (OrganizationTreeResponse) webServiceTemplate
				.marshalSendAndReceive(request);
		return organizationTreeResponse;
	}

	private Object getParentTreeByOrganizationIdRequest(String organizationId) {
		GetParentTreeByOrganizationIdRequest request = new GetParentTreeByOrganizationIdRequest();
		request.setOrganizationId(organizationId);
		return request;
	}

	/**
	 * Private method to create a GetOrganizationByIdRequest object.
	 * 
	 * @param organizationId
	 * @return request
	 */
	private Object getChildTreeByOrganizationIdRequest(String organizationId) {
		GetChildTreeByOrganizationIdRequest request = new GetChildTreeByOrganizationIdRequest();
		request.setOrganizationId(organizationId);
		return request;
	}

	/**
	 * Method to get list of all child organizations.
	 * 
	 * @param organizationTreeTypes
	 * @param organizationDTOList
	 * 
	 * @return organization DTO list
	 */
	private List<OrganizationDTO> getOrganizationChildTree(List<OrganizationTreeType> organizationTreeTypes,
			List<OrganizationDTO> organizationDTOList) {
		OrganizationDTO organizationDTO = null;

		for (int i = 0; i < organizationTreeTypes.size(); i++) {
			OrganizationTreeType organizationTreeType = organizationTreeTypes.get(i);
			organizationDTO = new OrganizationDTO();
			organizationDTO.setOrgId(organizationTreeType.getOrganizationId());
			organizationDTO.setOrgLevel(organizationTreeType.getLevel());
			organizationDTOList.add(organizationDTO);
			if (organizationTreeType.getOrganization().size() > 0)
				getOrganizationChildTree(organizationTreeType.getOrganization(), organizationDTOList);
		}
		return organizationDTOList;
	}

	/**
	 * Method to get fault message.
	 * 
	 * @param message
	 * 
	 * @return fault message
	 */
	private String getFaultMessage(WebServiceMessage message) {
		AxiomSoapMessage soapMessage = (AxiomSoapMessage) message;
		SOAPFaultText sOAPFaultText = soapMessage.getAxiomMessage().getSOAPEnvelope().getBody().getFault().getReason()
				.getSOAPFaultText("en");
		return sOAPFaultText.getText().toString();
	}
	
	public ArrayList<OrganizationDTO> getChildTree(GetChildTreeByOrganizationIdRequest request)
	{
		ArrayList<OrganizationDTO> organizationDTOList = new ArrayList<OrganizationDTO>();
		String faultMessage = null;
		try {
			OrganizationTreeResponse organizationTreeResponse = (OrganizationTreeResponse) webServiceTemplate
					.marshalSendAndReceive(request);
			OrganizationTreeType organizationTreeType = organizationTreeResponse.getOrganization();
			OrganizationDTO organizationDTO = new OrganizationDTO();

			organizationDTO.setOrgId(organizationTreeType.getOrganizationId());
			organizationDTO.setOrgLevel(organizationTreeType.getLevel());
			organizationDTOList.addAll(getOrganizationChildTree(organizationTreeType.getOrganization(),
					organizationDTOList));
			organizationDTOList.add(organizationDTO);

		} catch (SoapFaultClientException soapFaultClientException) {
			// FIXME: These needs to be removed once error codes are defined.
			faultMessage = getFaultMessage(soapFaultClientException.getWebServiceMessage());
			if (faultMessage.contains("Invalid Organization Id")) {
				throw new OrganizationNotValidException("No Organization found for Organization ID: " +  request.getOrganizationId());
			}
			if (faultMessage.contains("No child organizations found for organization Id ")) {
				return organizationDTOList;
			}
			throw new ExternalServiceCallException(soapFaultClientException.getMessage());
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage());
		}

		return organizationDTOList;
	}
	public LinkedList<OrganizationDTO> getParentTree(GetParentTreeByOrganizationIdRequest request)
	{
		LinkedList<OrganizationDTO> organizationDTOList = new LinkedList<OrganizationDTO>();
		String faultMessage = null;
		try {
			OrganizationTreeResponse organizationTreeResponse = (OrganizationTreeResponse) webServiceTemplate
					.marshalSendAndReceive(request);
			OrganizationTreeType organizationTreeType = organizationTreeResponse.getOrganization();
			OrganizationDTO organizationDTO = new OrganizationDTO();

			organizationDTO.setOrgId(organizationTreeType.getOrganizationId());
			organizationDTO.setOrgLevel(organizationTreeType.getLevel());
			organizationDTOList.addAll(getOrganizationChildTree(organizationTreeType.getOrganization(),
					organizationDTOList));
			organizationDTOList.add(organizationDTO);

		} catch (SoapFaultClientException soapFaultClientException) {
			// FIXME: These needs to be removed once error codes are defined.
			faultMessage = getFaultMessage(soapFaultClientException.getWebServiceMessage());
			if (faultMessage.contains("Invalid Organization Id")) {
				throw new OrganizationNotValidException("No Organization found for Organization ID: " + request.getOrganizationId());
			}
			if (faultMessage.contains("No child organizations found for organization Id ")) {
				return organizationDTOList;
			}
			throw new ExternalServiceCallException(soapFaultClientException.getMessage());
		} catch (Exception exception) {
			throw new ExternalServiceCallException(exception.getMessage());
		}

		return organizationDTOList;
	}
	class ExternalServiceCallException extends RuntimeException
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ExternalServiceCallException(String string, Exception exception) {
			super(string, exception);
		}

		public ExternalServiceCallException(String message) {
			super(message);
		}
		
	}
	class OrganizationNotValidException extends RuntimeException
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public OrganizationNotValidException(String string, Exception exception) {
			super(string, exception);
		}

		public OrganizationNotValidException(String string) {
			super(string);
		}
		
	}
}

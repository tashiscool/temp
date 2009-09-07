package com.pearson.ed.lplc.stub.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.pearson.ed.lplc.exception.ExternalServiceCallException;
import com.pearson.ed.lplc.exception.OrganizationNotValidException;
import com.pearson.ed.lplc.stub.api.OrganizationServiceClient;
import com.pearson.ed.lplc.stub.dto.OrganizationDTO;
import com.pearson.ed.lplc.ws.schema.GetChildTreeByOrganizationIdRequest;
import com.pearson.ed.lplc.ws.schema.OrganizationTreeResponse;
import com.pearson.ed.lplc.ws.schema.OrganizationTreeType;

/**
 * This class is a Client stub class and implements methods to be invoked on
 * Organization Life Cycle service.
 * 
 * @author vtirura
 * 
 */
public class OrganizationServiceClientImpl implements OrganizationServiceClient {

	private static final Logger logger = Logger.getLogger(OrganizationServiceClientImpl.class);

	private WebServiceTemplate webServiceTemplate;

	private String modeUnitTest = "N";

	/**
	 * @param modeUnitTest
	 *            the modeUnitTest to set
	 */
	public void setModeUnitTest(String modeUnitTest) {
		this.modeUnitTest = modeUnitTest;
	}

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
	 * @param organizationId
	 * @return List of OrganizationDTOs. 
	 */
	public List<OrganizationDTO> getChildOrganizations(String organizationId) {

		List<OrganizationDTO> organizationDTOList = new ArrayList<OrganizationDTO>();
		if (!modeUnitTest.equals("Y")) {
			Object request = getChildTreeByOrganizationIdRequest(organizationId);
			try {
				OrganizationTreeResponse organizationTreeResponse = (OrganizationTreeResponse) webServiceTemplate
						.marshalSendAndReceive(request);
				OrganizationTreeType organizationTreeType = organizationTreeResponse.getOrganization();
				if (null != organizationTreeType && organizationTreeType.getOrganization().size() > 0) {
					for (OrganizationTreeType orgTreeType : organizationTreeType.getOrganization()) {
						OrganizationDTO organizationDTO = new OrganizationDTO();
						organizationDTO.setOrg_id(orgTreeType.getOrganizationId());
						organizationDTO.setOrg_level(orgTreeType.getLevel());
						organizationDTOList.add(organizationDTO);
					}
				} else {
					throw new OrganizationNotValidException("No Organization found for Organization ID: "
							+ organizationId);
				}
			} catch (Exception exception) {
				if (exception instanceof OrganizationNotValidException)
					throw new OrganizationNotValidException("No Organization found for Organization ID: "
							+ organizationId);
				else {
					logger
							.error("Call to Organization Service failed when fetching ChildOrganizaitons for Organization Id: "
									+ organizationId);
					throw new ExternalServiceCallException();
				}

			}
		} else {
			OrganizationDTO organizationDTO = new OrganizationDTO();
			organizationDTO.setOrg_id("DummyChildOrg1");
			organizationDTO.setOrg_level(1);
			organizationDTOList.add(organizationDTO);
		}

		return organizationDTOList;
	}

	/**
	 * Private method to create a GetOrganizationByIdRequest object.
	 * 
	 * @param organizationId
	 * @return
	 */
	private Object getChildTreeByOrganizationIdRequest(String organizationId) {
		GetChildTreeByOrganizationIdRequest request = new GetChildTreeByOrganizationIdRequest();
		request.setOrganizationId(organizationId);
		return request;
	}
}
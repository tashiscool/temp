/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.TestHelperUtils.marshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Source;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.rws.organization.doc._2009._07._01.AttributeKeyType;
import com.pearson.rws.organization.doc._2009._07._01.GetChildTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetOrganizationByIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.GetParentTreeByOrganizationIdRequest;
import com.pearson.rws.organization.doc._2009._07._01.Organization;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationIdRequestType;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeResponse;
import com.pearson.rws.organization.doc._2009._07._01.OrganizationTreeType;
import com.pearson.rws.organization.doc._2009._07._01.ReadAttributeType;
import com.pearson.rws.organization.doc._2009._07._01.ReadAttributesListType;

/**
 * Unit test of {@link } using Spring WS mock objects.
 * 
 * @author ULLOYNI
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-lp-clients.xml",
		"classpath:applicationContext-test-lplc-ws.xml", "classpath:applicationContext-lplc.xml" })
public class OrganizationLifeCycleClientImplTest {

	private static enum OrgRequestType {
		ROOT_ONLY, PARENT_TREE, CHILD_TREE;
	}

	@Autowired(required = true)
	private OrganizationLifeCycleClientImpl testClient;

	@Autowired(required = true)
	private Jaxb2Marshaller marshaller;

	private MockWebServiceServer mockServer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();

		mockServer = MockWebServiceServer.createServer(testClient.getServiceClient());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getChildTreeDisplayNamesByOrganizationId(String)}
	 * .
	 */
	@Test
	public void testGetChildTreeDisplayNamesByOrganizationId() {
		String dummyOrgId = "dummy-org-id";
		String dummyOrgDisplayName = "dummy-display-name";

		Map<String, String> dummyChildTree = new Hashtable<String, String>();
		dummyChildTree.put(dummyOrgId, dummyOrgDisplayName);
		dummyChildTree.put("child-1", "child-display-name-1");
		dummyChildTree.put("child-2", "child-display-name-2");
		dummyChildTree.put("child-3", "child-display-name-3");

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.CHILD_TREE))).andRespond(
				withPayload(generateDummyGetOrgResponseData(dummyChildTree, OrgRequestType.CHILD_TREE)));

		OrganizationDisplayNamesResponse response = testClient.getChildTreeDisplayNamesByOrganizationId(dummyOrgId);

		mockServer.verify();

		assertEquivalent(dummyChildTree, response);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getParentTreeDisplayNamesByOrganizationId(String)}
	 * .
	 */
	@Test
	public void testGetParentTreeDisplayNamesByOrganizationId() {
		String dummyOrgId = "dummy-org-id";
		String dummyOrgDisplayName = "dummy-display-name";

		Map<String, String> dummyParentTree = new Hashtable<String, String>();
		dummyParentTree.put("parent-1", "parent-display-name-1");
		dummyParentTree.put("parent-2", "parent-display-name-2");
		dummyParentTree.put("parent-3", "parent-display-name-3");
		dummyParentTree.put(dummyOrgId, dummyOrgDisplayName);

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.PARENT_TREE))).andRespond(
				withPayload(generateDummyGetOrgResponseData(dummyParentTree, OrgRequestType.PARENT_TREE)));

		OrganizationDisplayNamesResponse response = testClient.getParentTreeDisplayNamesByOrganizationId(dummyOrgId);

		mockServer.verify();

		assertEquivalent(dummyParentTree, response);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(String)}.
	 */
	@Test
	public void testGetOrganizationDisplayName() {
		String dummyOrgId = "dummy-org-id";
		String dummyOrgDisplayName = "dummy-display-name";

		Map<String, String> dummyOrgData = new Hashtable<String, String>();
		dummyOrgData.put(dummyOrgId, dummyOrgDisplayName);

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.ROOT_ONLY))).andRespond(
				withPayload(generateDummyGetOrgResponseData(dummyOrgData, OrgRequestType.ROOT_ONLY)));

		OrganizationDisplayNamesResponse response = testClient.getOrganizationDisplayName(dummyOrgId);

		mockServer.verify();

		assertEquivalent(dummyOrgData, response);
	}

	/**
	 * Compare with assertions the contents of the seed data with the response object. Wraps a series of assertions.
	 * 
	 * @param seedData
	 *            seed data
	 * @param responseData
	 *            {@link OrganizationDisplayNamesResponse} instance to compare against
	 */
	private void assertEquivalent(Map<String, String> seedData, OrganizationDisplayNamesResponse responseData) {
		assertNotNull(seedData);
		assertNotNull(responseData);

		Map<String, String> actuals = responseData.getOrganizationDisplayNamesByIds();

		for (Entry<String, String> expected : seedData.entrySet()) {
			assertTrue(String.format("Missing DisplayName for org id: %s", expected.getKey()),
					actuals.containsKey(expected.getKey()));
			assertEquals(String.format("Mismatched DisplayName for org id: %s", expected.getKey()),
					expected.getValue(), actuals.get(expected.getKey()));
		}
	}

	/**
	 * Helper function to generate a dummy GetOrganizationById, GetChildTreeByOrganizationId, or
	 * GetParentTreeByOrganizationId service request.
	 * 
	 * @param orgId
	 *            dummy organization id
	 * @param requestType
	 *            what type of request to generate
	 * @return {@link Source} instance
	 */
	private Source generateDummyGetOrgRequest(String orgId, OrgRequestType requestType) {
		Object request = null;

		switch (requestType) {
		case CHILD_TREE:
			GetChildTreeByOrganizationIdRequest getChildTreeRequest = new GetChildTreeByOrganizationIdRequest();
			getChildTreeRequest.setOrganizationId(orgId);
			request = getChildTreeRequest;
			break;
		case PARENT_TREE:
			GetParentTreeByOrganizationIdRequest getParentTreeRequest = new GetParentTreeByOrganizationIdRequest();
			getParentTreeRequest.setOrganizationId(orgId);
			request = getParentTreeRequest;
			break;
		case ROOT_ONLY:
			GetOrganizationByIdRequest getOrgRequest = new GetOrganizationByIdRequest();
			getOrgRequest.setOrganizationIdRequestType(new OrganizationIdRequestType());
			getOrgRequest.getOrganizationIdRequestType().setOrganizationId(orgId);
			request = getOrgRequest;
			break;
		}

		return marshal(marshaller, request);
	}

	/**
	 * Helper function to generate a dummy GetOrganizationById, GetChildTreeByOrganizationId, or
	 * GetParentTreeByOrganizationId service response using the provided seed data.
	 * 
	 * For the GetOrganizationById response only the first entry of the provided map of seed data is used. For the
	 * Get*TreeByOrganizationId responses, the seed data is turned into a depth-only tree with each subsequent entry in
	 * the seed data map being the child/parent organization of the preceding entry.
	 * 
	 * @param dummyOrgDisplayNamesByOrgId
	 *            seed data, map of organization ids to organization display names
	 * @param requestType
	 *            what type of request we need a response to
	 * @return {@link Source} instance
	 */
	private Source generateDummyGetOrgResponseData(Map<String, String> dummyOrgDisplayNamesByOrgId,
			OrgRequestType requestType) {
		Object response = null;

		int levelCounter = 0;

		switch (requestType) {
		case CHILD_TREE:
			OrganizationTreeResponse childTreeResponse = new OrganizationTreeResponse();

			OrganizationTreeType lastChildOrg = null;
			levelCounter = 0;
			for (Entry<String, String> dummyOrgData : dummyOrgDisplayNamesByOrgId.entrySet()) {
				if (lastChildOrg == null) {
					lastChildOrg = new OrganizationTreeType();
					childTreeResponse.setOrganization(lastChildOrg);
					lastChildOrg.setOrganizationId(dummyOrgData.getKey());
					lastChildOrg.setName(dummyOrgData.getValue());
					lastChildOrg.setLevel(levelCounter);
				} else {
					OrganizationTreeType childOrg = new OrganizationTreeType();
					lastChildOrg.getOrganization().add(childOrg);
					lastChildOrg = childOrg;

					childOrg.setOrganizationId(dummyOrgData.getKey());
					childOrg.setName(dummyOrgData.getValue());
					childOrg.setLevel(levelCounter);
				}
				levelCounter++;
			}

			response = childTreeResponse;
			break;
		case PARENT_TREE:
			OrganizationTreeResponse parentTreeResponse = new OrganizationTreeResponse();

			OrganizationTreeType lastParentOrg = null;
			levelCounter = 0;
			for (Entry<String, String> dummyOrgData : dummyOrgDisplayNamesByOrgId.entrySet()) {
				if (lastParentOrg == null) {
					lastParentOrg = new OrganizationTreeType();
					parentTreeResponse.setOrganization(lastParentOrg);
					lastParentOrg.setOrganizationId(dummyOrgData.getKey());
					lastParentOrg.setName(dummyOrgData.getValue());
					lastParentOrg.setLevel(levelCounter);
				} else {
					OrganizationTreeType parentOrg = new OrganizationTreeType();
					lastParentOrg.getOrganization().add(parentOrg);
					lastParentOrg = parentOrg;

					parentOrg.setOrganizationId(dummyOrgData.getKey());
					parentOrg.setName(dummyOrgData.getValue());
					parentOrg.setLevel(levelCounter);
				}
				levelCounter++;
			}
			response = parentTreeResponse;
			break;
		case ROOT_ONLY:
			OrganizationResponse orgResponse = new OrganizationResponse();

			Organization org = new Organization();
			orgResponse.setOrganization(org);

			Entry<String, String> justOneDummyOrg = dummyOrgDisplayNamesByOrgId.entrySet().iterator().next();

			org.setOrganizationId(justOneDummyOrg.getKey());
			org.setAttributes(new ReadAttributesListType());
			ReadAttributeType attribute = new ReadAttributeType();
			org.getAttributes().getAttribute().add(attribute);
			attribute.setAttributeKey(AttributeKeyType.ORG_DISPLAY_NAME);
			attribute.setAttributeValue(justOneDummyOrg.getValue());

			response = orgResponse;
			break;
		}

		return marshal(marshaller, response);
	}

}

/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyGetOrgRequest;
import static com.pearson.ed.lp.LicensedProductTestHelper.generateDummyGetOrgResponseData;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withClientOrSenderFault;
import static org.springframework.ws.test.client.ResponseCreators.withException;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.LicensedProductTestHelper.OrgRequestType;
import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;

/**
 * Unit test of {@link OrganizationLifeCycleClientImpl} using Spring WS mock objects.
 * 
 * @author ULLOYNI
 * 
 */
public class OrganizationLifeCycleClientImplTest extends BaseLicensedProductClientStubTest {

	@Autowired(required = true)
	private OrganizationLifeCycleClientImpl testClient;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
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
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getChildTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetChildTreeDisplayNamesByOrganizationIdInvalidOrganization() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.CHILD_TREE))).andRespond(
				withClientOrSenderFault("Invalid Organization Id", Locale.ENGLISH));

		try {
			testClient.getChildTreeDisplayNamesByOrganizationId(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getChildTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetChildTreeDisplayNamesByOrganizationIdNoChildOrganizations() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.CHILD_TREE))).andRespond(
				withClientOrSenderFault("No child organizations found", Locale.ENGLISH));

		try {
			OrganizationDisplayNamesResponse response = testClient.getChildTreeDisplayNamesByOrganizationId(dummyOrgId);
			assertEquals(0, response.getOrganizationDisplayNamesByIds().size());
		} catch (Exception e) {
			fail("I should not get here: " + e.getMessage());
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getChildTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetChildTreeDisplayNamesByOrganizationIdNonSpecificClientSoapFault() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.CHILD_TREE))).andRespond(
				withClientOrSenderFault("Stupid mock! No cookie!", Locale.ENGLISH));

		try {
			testClient.getChildTreeDisplayNamesByOrganizationId(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getChildTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetChildTreeDisplayNamesByOrganizationIdNonSpecificIOException() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.CHILD_TREE))).andRespond(
				withException(new IOException("Stupid mock! No cookie!")));

		try {
			testClient.getChildTreeDisplayNamesByOrganizationId(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getParentTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetParentTreeDisplayNamesByOrganizationIdInvalidOrganization() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.PARENT_TREE))).andRespond(
				withClientOrSenderFault("Invalid Organization Id", Locale.ENGLISH));

		try {
			testClient.getParentTreeDisplayNamesByOrganizationId(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getParentTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetParentTreeDisplayNamesByOrganizationIdNoParentOrganizations() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.PARENT_TREE))).andRespond(
				withClientOrSenderFault("No parent organizations found", Locale.ENGLISH));

		try {
			OrganizationDisplayNamesResponse response = testClient.getParentTreeDisplayNamesByOrganizationId(dummyOrgId);
			assertEquals(0, response.getOrganizationDisplayNamesByIds().size());
		} catch (Exception e) {
			fail("I should not get here: " + e.getMessage());
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getParentTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetParentTreeDisplayNamesByOrganizationIdNonSpecificClientSoapFault() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.PARENT_TREE))).andRespond(
				withClientOrSenderFault("Stupid mock! No cookie!", Locale.ENGLISH));

		try {
			testClient.getParentTreeDisplayNamesByOrganizationId(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getParentTreeDisplayNamesByOrganizationId(String)}.
	 */
	@Test
	public void testGetParentTreeDisplayNamesByOrganizationIdNonSpecificIOException() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.PARENT_TREE))).andRespond(
				withException(new IOException("Stupid mock! No cookie!")));

		try {
			testClient.getParentTreeDisplayNamesByOrganizationId(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(String)}.
	 */
	@Test
	public void testGetOrganizationDisplayNameInvalidOrganization() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.ROOT_ONLY))).andRespond(
				withClientOrSenderFault("No Organization with Organization Id", Locale.ENGLISH));

		try {
			testClient.getOrganizationDisplayName(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(String)}.
	 */
	@Test
	public void testGetOrganizationDisplayNameNonSpecificClientSoapFault() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.ROOT_ONLY))).andRespond(
				withClientOrSenderFault("Stupid mock! No cookie!", Locale.ENGLISH));

		try {
			testClient.getOrganizationDisplayName(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(String)}.
	 */
	@Test
	public void testGetOrganizationDisplayNameNonSpecificIOException() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId, OrgRequestType.ROOT_ONLY))).andRespond(
				withException(new IOException("Stupid mock! No cookie!")));

		try {
			testClient.getOrganizationDisplayName(dummyOrgId);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
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

}

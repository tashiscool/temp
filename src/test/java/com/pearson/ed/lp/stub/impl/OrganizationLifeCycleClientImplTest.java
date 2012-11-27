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
import static org.junit.Assert.fail;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withClientOrSenderFault;
import static org.springframework.ws.test.client.ResponseCreators.withException;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.io.IOException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.test.client.MockWebServiceServer;

import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.message.OrganizationDisplayNameRequest;
import com.pearson.ed.lp.message.OrganizationDisplayNameResponse;

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
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(@link OrganizationDisplayNameRequest)}.
	 */
	@Test
	public void testGetOrganizationDisplayName() {
		String dummyOrgId = "dummy-org-id";
		String dummyOrgDisplayName = "dummy-display-name";
		
		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId))).andRespond(
				withPayload(generateDummyGetOrgResponseData(dummyOrgId, dummyOrgDisplayName)));


		OrganizationDisplayNameRequest request = new OrganizationDisplayNameRequest();
		request.setOrganizationId(dummyOrgId);
		
		OrganizationDisplayNameResponse response = testClient.getOrganizationDisplayName(request);

		mockServer.verify();

		assertNotNull(response);
		assertEquals(dummyOrgId, response.getOrganizationId());
		assertEquals(dummyOrgDisplayName, response.getOrganizationDisplayName());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(@link OrganizationDisplayNameRequest)}.
	 */
	@Test
	public void testGetOrganizationDisplayNameInvalidOrganization() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId))).andRespond(
				withClientOrSenderFault("No Organization with Organization Id", Locale.ENGLISH));

		OrganizationDisplayNameRequest request = new OrganizationDisplayNameRequest();
		request.setOrganizationId(dummyOrgId);
		
		try {
			testClient.getOrganizationDisplayName(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(@link OrganizationDisplayNameRequest)}.
	 */
	@Test
	public void testGetOrganizationDisplayNameNonSpecificClientSoapFault() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId))).andRespond(
				withClientOrSenderFault("Stupid mock! No cookie!", Locale.ENGLISH));

		OrganizationDisplayNameRequest request = new OrganizationDisplayNameRequest();
		request.setOrganizationId(dummyOrgId);
		
		try {
			testClient.getOrganizationDisplayName(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.stub.impl.OrganizationLifeCycleClientImpl#getOrganizationDisplayName(@link OrganizationDisplayNameRequest)}.
	 */
	@Test
	public void testGetOrganizationDisplayNameNonSpecificIOException() {
		String dummyOrgId = "dummy-org-id";

		mockServer.expect(payload(generateDummyGetOrgRequest(dummyOrgId))).andRespond(
				withException(new IOException("Stupid mock! No cookie!")));

		OrganizationDisplayNameRequest request = new OrganizationDisplayNameRequest();
		request.setOrganizationId(dummyOrgId);
		
		try {
			testClient.getOrganizationDisplayName(request);
			fail("Must throw exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}

		mockServer.verify();
	}

}

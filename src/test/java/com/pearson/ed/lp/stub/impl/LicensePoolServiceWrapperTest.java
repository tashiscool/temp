/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static com.pearson.ed.lp.LicensedProductTestHelper.configureMockLicensePoolService;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pearson.ed.lp.exception.ExternalServiceCallException;
import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.services.api.LicensePoolService;

/**
 * @author ULLOYNI
 *
 */
public class LicensePoolServiceWrapperTest extends BaseLicensedProductClientStubTest {

	@Autowired(required = true)
	private LicensePoolService mockWrappedService;
	
	@Autowired(required = true)
	private LicensePoolServiceWrapper testServiceWrapper;
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testServiceWrapper.setLicensePoolService(mockWrappedService);
		testServiceWrapper.setExceptionFactory(exceptionFactory);
	}
	
	/**
	 * Test method for {@link com.pearson.ed.lp.stub.impl.LicensePoolServiceWrapper#getLicensePoolsByOrganizationId(com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest)}.
	 * 
	 * Simple test with mocks to verify happy-path flow.
	 */
	@Test
	public void testGetLicensePoolsByOrganizationIdHappyPath() {
		List<OrganizationLPMapping> licensePools = Arrays.asList(
				new OrganizationLPMapping[]{new OrganizationLPMapping()});
		configureMockLicensePoolService(mockWrappedService, licensePools);
		
		LicensePoolResponse response = testServiceWrapper.getLicensePoolsByOrganizationId(
				new LicensePoolByOrganizationIdRequest("test", "test"));
		
		verify(mockWrappedService);
		
		assertNotNull(response);
		assertEquals(1, response.getLicensePools().size());
	}

	/**
	 * Test method for {@link com.pearson.ed.lp.stub.impl.LicensePoolServiceWrapper#getLicensePoolsByOrganizationId(com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest)}.
	 */
	@Test
	public void testGetLicensePoolsByOrganizationIdNoLicensePools() {
		List<OrganizationLPMapping> licensePools = Arrays.asList(
				new OrganizationLPMapping[]{});
		configureMockLicensePoolService(mockWrappedService, licensePools);
		
		try {
			testServiceWrapper.getLicensePoolsByOrganizationId(
					new LicensePoolByOrganizationIdRequest("test", "test"));
			fail("Must throw an exception!");
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}
		
		verify(mockWrappedService);
	}

	/**
	 * Test method for {@link com.pearson.ed.lp.stub.impl.LicensePoolServiceWrapper#getLicensePoolsByOrganizationId(com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest)}.
	 */
	@Test
	public void testGetLicensePoolsByOrganizationIdUnexpectedException() {
		configureMockLicensePoolService(mockWrappedService, 
				new RuntimeException());
		
		try {
			testServiceWrapper.getLicensePoolsByOrganizationId(
					new LicensePoolByOrganizationIdRequest("test", "test"));
			fail("Must throw an exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}
		
		verify(mockWrappedService);
	}
	
}

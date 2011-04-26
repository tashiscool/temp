/**
 * 
 */
package com.pearson.ed.lp.stub.impl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.easymock.EasyMock.*;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
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
	
	private LicensePoolService mockWrappedService = createStrictMock(LicensePoolService.class);
	
	private LicensePoolServiceWrapper testServiceWrapper = new LicensePoolServiceWrapper();
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();

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
		generateMockLicensePoolService(licensePools);
		
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
		generateMockLicensePoolService(licensePools);
		
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
		generateMockLicensePoolService(null);
		
		try {
			testServiceWrapper.getLicensePoolsByOrganizationId(
					new LicensePoolByOrganizationIdRequest("test", "test"));
			fail("Must throw an exception!");
		} catch (Exception e) {
			assertThat(e, is(ExternalServiceCallException.class));
		}
		
		verify(mockWrappedService);
	}
	
	/**
	 * Generate the mock LicensePoolService implementation with behavior defined by the
	 * input parameters.
	 * 
	 * @param resultSet a {@link List} of {@link OrganizationLPMapping} instances to return,
	 * 		or null to throw an Exception instead.
	 */
	private void generateMockLicensePoolService(List<OrganizationLPMapping> resultSet) {
		reset(mockWrappedService);
		
		if(resultSet == null) {
			expect(mockWrappedService.getLicensePoolByOrganizationId(
					isA(String.class), isA(String.class)))
					.andThrow(new RuntimeException("Bad service! No cookie!"));
		} else {
			expect(mockWrappedService.getLicensePoolByOrganizationId(
					isA(String.class), isA(String.class)))
					.andReturn(resultSet);
		}
		
		replay(mockWrappedService);
	}

}

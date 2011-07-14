/**
 * 
 */
package com.pearson.ed.lp.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pearson.ed.lp.exception.InvalidOrganizationException;
import com.pearson.ed.lp.exception.LicensedProductExceptionFactory;
import com.pearson.ed.lp.exception.ProductNotFoundException;
import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * @author ULLOYNI
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-lp-exception.xml",
		"classpath:applicationContext-test-lplc.xml"
})
public class LicensedProductsDataAggregatorTest {

	private LicensedProductsDataAggregator aggregator = new LicensedProductsDataAggregator();
	
	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;
	
	/**
	 * Setup test logging.
	 */
	@BeforeClass
	public static void setUpClass() {
		BasicConfigurator.configure();
	}
	
	/**
	 * Setup test.
	 */
	@Before
	public void setUp() {
		aggregator.setExceptionFactory(exceptionFactory);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseEmptyResponseObjectsNoOrgDisplayNamesResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));

		try {
			aggregator.aggregateResponse(input);
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseEmptyResponseObjectsOneOrgDisplayNamesResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));
		input.add(new OrganizationDisplayNamesResponse());

		try {
			aggregator.aggregateResponse(input);
		} catch (Exception e) {
			assertThat(e, is(InvalidOrganizationException.class));
		}
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseOneLicensePoolResponseManyEmptyOrgDisplayNamesResponses() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(Arrays.asList(new OrganizationLPMapping[]{new OrganizationLPMapping()})));
		input.add(new OrganizationDisplayNamesResponse());
		input.add(new OrganizationDisplayNamesResponse());

		try {
			aggregator.aggregateResponse(input);
		} catch (Exception e) {
			assertThat(e, is(ProductNotFoundException.class));
		}
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseOneOrgDisplayNamesResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(Arrays.asList(new OrganizationLPMapping[]{new OrganizationLPMapping()})));
		input.add(generateOrgDisplayNameDummyData());

		LicensedProductDataCollection result = aggregator.aggregateResponse(input);

		assertNotNull(result.getLicensePools());
		assertNotNull(result.getLicensePools().getLicensePools());
		assertNotNull(result.getOrganizationDisplayNames());
		assertEquals(3, result.getOrganizationDisplayNames().getOrganizationDisplayNamesByIds().size());
		assertEquals(generateOrgDisplayNameDummyData().getOrganizationDisplayNamesByIds(), result
				.getOrganizationDisplayNames().getOrganizationDisplayNamesByIds());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseMultipleSameOrgDisplayNamesResponses() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(Arrays.asList(new OrganizationLPMapping[]{new OrganizationLPMapping()})));
		input.add(generateOrgDisplayNameDummyData());
		input.add(generateOrgDisplayNameDummyData());

		LicensedProductDataCollection result = aggregator.aggregateResponse(input);

		assertNotNull(result.getLicensePools());
		assertNotNull(result.getLicensePools().getLicensePools());
		assertNotNull(result.getOrganizationDisplayNames());
		assertEquals(3, result.getOrganizationDisplayNames().getOrganizationDisplayNamesByIds().size());
		assertEquals(generateOrgDisplayNameDummyData().getOrganizationDisplayNamesByIds(), result
				.getOrganizationDisplayNames().getOrganizationDisplayNamesByIds());
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseMultipleDifferentOrgDisplayNamesResponses() {
		OrganizationDisplayNamesResponse diffResponse = generateOrgDisplayNameDummyData();
		diffResponse.getOrganizationDisplayNamesByIds().put("and-no-for-something", "completely-different");

		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(Arrays.asList(new OrganizationLPMapping[]{new OrganizationLPMapping()})));
		input.add(generateOrgDisplayNameDummyData());
		input.add(diffResponse);

		LicensedProductDataCollection result = aggregator.aggregateResponse(input);

		assertNotNull(result.getLicensePools());
		assertNotNull(result.getLicensePools().getLicensePools());
		assertNotNull(result.getOrganizationDisplayNames());
		assertEquals(4, result.getOrganizationDisplayNames().getOrganizationDisplayNamesByIds().size());
		assertEquals(diffResponse.getOrganizationDisplayNamesByIds(), result.getOrganizationDisplayNames()
				.getOrganizationDisplayNamesByIds());
	}

	/**
	 * Helper function to generate dummy OrganizationDisplayNamesResponse data.
	 * 
	 * @return
	 */
	private OrganizationDisplayNamesResponse generateOrgDisplayNameDummyData() {
		OrganizationDisplayNamesResponse orgDisplayNamesData = new OrganizationDisplayNamesResponse();

		orgDisplayNamesData.getOrganizationDisplayNamesByIds().put("1", "dummy-1");
		orgDisplayNamesData.getOrganizationDisplayNamesByIds().put("2", "dummy-2");
		orgDisplayNamesData.getOrganizationDisplayNamesByIds().put("3", "dummy-3");

		return orgDisplayNamesData;
	}

}

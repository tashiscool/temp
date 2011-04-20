/**
 * 
 */
package com.pearson.ed.lp.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * @author ULLOYNI
 * 
 */
public class LicensedProductsDataAggregatorTest {

	private LicensedProductsDataAggregator aggregator = new LicensedProductsDataAggregator();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseEmptyResponseObjectsNoOrgDisplayNamesResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));

		LicensedProductDataCollection result = aggregator.aggregateResponse(input);

		assertEmptyResult(result);
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

		LicensedProductDataCollection result = aggregator.aggregateResponse(input);

		assertEmptyResult(result);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseEmptyResponseObjectsManyOrgDisplayNamesResponses() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));
		input.add(new OrganizationDisplayNamesResponse());
		input.add(new OrganizationDisplayNamesResponse());

		LicensedProductDataCollection result = aggregator.aggregateResponse(input);

		assertEmptyResult(result);
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsDataAggregator#aggregateResponse(java.util.List)}.
	 */
	@Test
	public void testAggregateResponseOneOrgDisplayNamesResponse() {
		List<Object> input = new ArrayList<Object>();
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));
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
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));
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
		input.add(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>()));
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

	/**
	 * Helper empty result set assertion function.
	 * 
	 * @param result
	 */
	private void assertEmptyResult(LicensedProductDataCollection result) {
		assertNotNull(result.getLicensePools());
		assertNotNull(result.getLicensePools().getLicensePools());
		assertEquals(0, result.getLicensePools().getLicensePools().size());
		assertNotNull(result.getOrganizationDisplayNames());
		assertEquals(0, result.getOrganizationDisplayNames().getOrganizationDisplayNamesByIds().size());
	}

}

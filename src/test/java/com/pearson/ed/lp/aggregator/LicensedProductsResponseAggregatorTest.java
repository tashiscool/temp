/**
 * 
 */
package com.pearson.ed.lp.aggregator;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author ULLOYNI
 * 
 */
public class LicensedProductsResponseAggregatorTest {

	private LicensedProductsResponseAggregator aggregator = new LicensedProductsResponseAggregator();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
	}

	/**
	 * Test method for
	 * {@link com.pearson.ed.lp.aggregator.LicensedProductsResponseAggregator#aggregateResponse(java.util.List)}.
	 */
	@Ignore
	@Test
	public void testAggregateResponse() {
		// TODO
		fail("Not yet implemented");
	}

}

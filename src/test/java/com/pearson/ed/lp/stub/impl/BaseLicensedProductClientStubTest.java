/**
 * 
 */
package com.pearson.ed.lp.stub.impl;


import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.client.MockWebServiceServer;

/**
 * Base class holding common configuration for all LicensedProduct client stub tests.
 * 
 * @author ULLOYNI
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-lp-clients.xml",
		"classpath:applicationContext-test-lp-client-mocks.xml",
		"classpath:applicationContext-lp-exception.xml",
		"classpath:applicationContext-test-lplc-ws.xml",
		"classpath:applicationContext-lplc.xml"
}, inheritLocations = true)
public abstract class BaseLicensedProductClientStubTest {

	protected MockWebServiceServer mockServer;
	
	/**
	 * Setup test logging.
	 */
	@BeforeClass
	public static void setUpClass() {
		BasicConfigurator.configure();
	}

}

/**
 * 
 */
package com.pearson.ed.lp.stub.impl;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
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
		"classpath:applicationContext-lp-exception.xml",
		"classpath:applicationContext-test-lplc-ws.xml",
		"classpath:applicationContext-lplc.xml"
}, inheritLocations = true)
public abstract class BaseLicensedProductClientStubTest {

	@Autowired(required = true)
	protected Jaxb2Marshaller marshaller;

	protected MockWebServiceServer mockServer;

}

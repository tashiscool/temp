package com.pearson.ed.test.lplc.common;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.pearson.ed.lplc.dao.api.LicensePoolDAO;
import com.pearson.ed.lplc.dao.api.OrganizationLPDAO;
import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.services.api.LicensePoolService;



/**
 * A base class for all integration tests that load a spring application context
 * and use dependency injection to populate fields in the test class. Unlike
 * AbstractDependencyInjectionSpringContextTests, this class can be used as a
 * base class for JUnit 4 tests.
 * 
 * RunWith is required to force what would otherwise look like a JUnit 3.x test
 * to run with the JUnit 4 test runner.
 * 
 */
@RunWith(JUnit4ClassRunner.class)
public abstract class BaseIntegrationTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(BaseIntegrationTest.class);
	

	/**
	 * pass through to the junit 3 calls, which are not annotated. JUnit 4 only
	 * cares about annotations and won't run the old setUp and tearDown methods
	 * defined by the Spring classes. To get around this, add methods with
	 * Before and After annotations to call setUp and tearDown
	 * 
	 * @throws Exception -
	 *             Exception
	 */
	@Before
	final public void callSetup() throws Exception {
		super.setUp();
		
	}

	/**
	 * pass through to the junit 3 calls, which are not annotated. JUnit 4 only
	 * cares about annotations and won't run the old setUp and tearDown methods
	 * defined by the Spring classes. To get around this, add methods with
	 * Before and After annotations to call setUp and tearDown
	 * 
	 * @throws Exception -
	 *             Exception
	 */
	@After
	public void callTearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Return the locations of the config files. A plain path will be treated as
	 * class path location. E.g.: "org/springframework/whatever/foo.xml". Note
	 * however that you may prefix path locations with standard Spring resource
	 * prefixes. Therefore, a config location path prefixed with "classpath:"
	 * with behave the same as a plain path, but a config location such as
	 * "file:/some/path/path/location/appContext.xml" will be treated as a
	 * filesystem location.
	 * 
	 * @return locations of the config files.
	 */
	protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext-lplc.xml",
				"classpath:applicationContext-lplc-dao.xml",
				"classpath:applicationContext-lplc-hibernate.xml",
				"classpath:applicationContext-lplc-service.xml",
				"classpath:applicationContext-test-lplc-services.xml"};
	}
	
	protected LicensePoolService loadLicensePoolService() {
		return (LicensePoolService) applicationContext.getBean("licensepoolService");
	}

	protected LicensePoolDTO loadLicensePool() {
		return (LicensePoolDTO) applicationContext.getBean("serviceTestLicensePool");
	}

	protected LicensePoolDAO loadLicensePoolDAO() {
		return (LicensePoolDAO) applicationContext.getBean("licensepoolDAO");
	}

	protected OrganizationLPDAO loadOrganizationLPDAO() {
		return (OrganizationLPDAO) applicationContext.getBean("organizationLPDAO");
	}

	protected LicensePoolDTO loadLicensePool2() {
		return (LicensePoolDTO) applicationContext.getBean("serviceTestLicensePool2");
	}
	
	/**
	 * Loading the bean for TestDenyNewSubscriptions.
	 */
	protected LicensePoolDTO loadDenyNewSubscription() {
		return (LicensePoolDTO) applicationContext.getBean("serviceTestDenyNewSubscription");
	}
	
	/**
	 * Loading the test data for createdBy value.
	 */
	protected String getCreatedBy() {
		return "System";
	}
	
	/**
	 * Loading the test data for cancellation of license pool.
	 */
	protected int getCancelationRequest() {
		return 1;
	}
}
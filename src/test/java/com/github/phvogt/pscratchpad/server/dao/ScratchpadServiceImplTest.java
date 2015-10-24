package com.github.phvogt.pscratchpad.server.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import org.datanucleus.enhancer.DataNucleusEnhancer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.phvogt.pscratchpad.server.TestUtils;
import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Tests {@link com.github.phvogt.pscratchpad.server.dao.ScratchpadServiceImpl}.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ScratchpadServiceImplTest {

    /** Logger. */
    private final Logger logger = Logger.getLogger(ScratchpadServiceImplTest.class.getName());

    /** google app engine gaeHelper */
    private final LocalServiceTestHelper gaeHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    private ScratchPadService scratchPadService;

    @Autowired
    private ScratchPadRepository scratchPadRepository;

    /**
     * Setup before test class
     */
    @BeforeClass
    public static void setUpClass() {

	// setup data nucleus to enhance DAO classes
	final DataNucleusEnhancer enhancer = new DataNucleusEnhancer("JPA", null);
	enhancer.setVerbose(true);
	enhancer.addPersistenceUnit("pscratchpad");
	enhancer.enhance();
    }

    /**
     * Setup MVC, services and GAE.
     */
    @Before
    public void setUp() {

	// We have to reset our mock between tests because the mock objects
	// are managed by the Spring container. If we would not reset them,
	// stubbing and verified behavior would "leak" from one test to another.
	Mockito.reset(scratchPadRepository);

	gaeHelper.setUp();

    }

    /**
     * Tear down GAE
     */
    @After
    public void tearDown() {
	gaeHelper.tearDown();
    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.dao.ScratchpadServiceImpl#getScratchPad(String)}
     * .
     */
    @Test
    public void testGetScratchpad() {

	final String testName = "default";
	final String testData = "testData";
	final Date testTime = new Date();

	final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

	Mockito.when(scratchPadRepository.findByName(testName))
	.thenReturn(Arrays.asList(new ScratchPad[] { returnData }));

	final ScratchPad result = scratchPadService.getScratchPad(testName);

	logger.info("result = " + result);
	Assert.assertEquals(returnData.getId(), result.getId());
	Assert.assertEquals(returnData.getName(), result.getName());
	Assert.assertEquals(returnData.getData(), result.getData());
	Assert.assertEquals(returnData.getLastChange(), result.getLastChange());

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.dao.ScratchpadServiceImpl#saveScratchPad(String, String)}
     * .
     */
    @Test
    public void testSaveScratchpad() {

	final String testName = "default";
	final String testData = "testData";
	final Date testTime = new Date();

	final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

	Mockito.when(scratchPadRepository.findByName(testName))
	.thenReturn(Arrays.asList(new ScratchPad[] { returnData }));
	Mockito.when(scratchPadRepository.save(Mockito.any(ScratchPad.class))).thenReturn(returnData);

	final ScratchPad result = scratchPadService.saveScratchPad(testName, testData);

	logger.info("result = " + result);
	Assert.assertEquals(returnData.getId(), result.getId());
	Assert.assertEquals(returnData.getName(), result.getName());
	Assert.assertEquals(returnData.getData(), result.getData());
	Assert.assertEquals(returnData.getLastChange(), result.getLastChange());

    }

    /**
     * Test configuration setup of beans.
     */
    @Configuration
    public static class TestConfiguration {

	/**
	 * Returned mocked repository.
	 * 
	 * @return repository
	 */
	@Bean
	public ScratchPadRepository scratchPadRepository() {
	    return Mockito.mock(ScratchPadRepository.class);
	}

	/**
	 * Return mocked scratchPadService.
	 * 
	 * @return scratchPadService
	 */
	@Bean
	public ScratchPadService scratchPadService() {
	    return new ScratchpadServiceImpl();
	}

    }
}

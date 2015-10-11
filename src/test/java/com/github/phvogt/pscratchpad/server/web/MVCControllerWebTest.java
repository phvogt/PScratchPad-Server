// (c) 2015 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.web;

import java.util.Date;
import java.util.logging.Logger;

import org.datanucleus.enhancer.DataNucleusEnhancer;
import org.hamcrest.Matchers;
import org.junit.After;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.phvogt.pscratchpad.server.TestUtils;
import com.github.phvogt.pscratchpad.server.dao.ScratchPadService;
import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Tests {@link com.github.phvogt.pscratchpad.server.web.MVCControllerWeb}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class MVCControllerWebTest {

    /** Logger. */
    private final Logger logger = Logger.getLogger(MVCControllerWebTest.class.getName());

    /** mock for MVC. */
    private MockMvc mockMvc;

    /** google app engine gaeHelper */
    private final LocalServiceTestHelper gaeHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    private ScratchPadService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
        Mockito.reset(service);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
     * {@link com.github.phvogt.pscratchpad.server.web.MVCControllerWeb#doIndex()}
     * .
     * @throws Exception if an assertion fails
     */
    @Test
    public void testDoIndex() throws Exception {

        logger.info("start");

        mockMvc.perform(MockMvcRequestBuilders.get("/")).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/load/default"));

        logger.info("end");
    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.web.MVCControllerWeb#doLoad(String, org.springframework.ui.Model)}
     * .
     * @throws Exception if an assertion fails
     */
    @Test
    public void testDoLoad() throws Exception {

        final String testName = "default";
        final String testData = "testData";
        final Date testTime = new Date();

        final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

        Mockito.when(service.getScratchPad(testName)).thenReturn(returnData);

        mockMvc.perform(MockMvcRequestBuilders.get("/" + IConstantsRequest.URL_LOAD + "/" + testName))
        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("index")).andExpect(MockMvcResultMatchers.forwardedUrl("index"))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_NAME, Matchers.is(testName)))
        .andExpect(
                MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_EDITOR_TEXT, Matchers.is(testData)))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_EDITOR_FILE_TIMESTAMP,
                Matchers.is(testTime.getTime())));

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.web.MVCControllerWeb#doSave(String, String, org.springframework.ui.Model)}
     * .
     * @throws Exception if an assertion fails
     */
    @Test
    public void testDoSave() throws Exception {

        final String testName = "default";
        final String testData = "testData";
        final Date testTime = new Date();

        final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

        Mockito.when(service.saveScratchPad(testName, testData)).thenReturn(returnData);

        mockMvc.perform(MockMvcRequestBuilders.post("/" + IConstantsRequest.URL_SAVE + "/" + testName)
                .param(IConstantsRequest.REQUEST_PARAM_EDITOR_FORM_SCRATCHPAD, testData)).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("index"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("index"))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_NAME, Matchers.is(testName)))
        .andExpect(
                MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_EDITOR_TEXT, Matchers.is(testData)))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_EDITOR_FILE_TIMESTAMP,
                Matchers.is(testTime.getTime())))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_EDITOR_CHANGED_MESSAGE,
                Matchers.is("changed.saved")));

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.web.MVCControllerWeb#doDownload(String, org.springframework.ui.Model, javax.servlet.http.HttpServletResponse)}
     * .
     * @throws Exception if an assertion fails
     */
    @Test
    public void testDoDownload() throws Exception {

        final String testName = "default";
        final String testData = "testData";
        final Date testTime = new Date();

        final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

        Mockito.when(service.getScratchPad(testName)).thenReturn(returnData);

        mockMvc.perform(MockMvcRequestBuilders.get("/" + IConstantsRequest.URL_DOWNLOAD + "/" + testName))
        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("download")).andExpect(MockMvcResultMatchers.forwardedUrl("download"))
        .andExpect(MockMvcResultMatchers.header().string("Content-Disposition",
                "attachment; filename=\"" + testName + ".txt\""))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_NAME, Matchers.is(testName)))
        .andExpect(MockMvcResultMatchers.model().attribute(IConstantsRequest.REQUEST_ATTR_TEXT, Matchers.is(testData)));

    }

    /**
     * Test configuration setup of beans.
     */
    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        /**
         * Controller to test
         * @return controller
         */
        @Bean
        public MVCControllerWeb mvcControllerWeb() {
            return new MVCControllerWeb();
        }

        /**
         * Retunr mocked service.
         * @return service
         */
        @Bean
        public ScratchPadService scratchPadService() {
            return Mockito.mock(ScratchPadService.class);
        }

    }

}

// (c) 2015 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.rest;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.phvogt.pscratchpad.server.dao.ScratchPadService;
import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Tests {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class MVCControllerRestTest {

    /** Logger. */
    private final Logger logger = Logger.getLogger(MVCControllerRestTest.class.getName());

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
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#getData(String)}
     * .
     * @throws Exception if an assertion fails
     */
    @Test
    public void testGetData() throws Exception {

        final ScratchPad data = new ScratchPad();
        final Key key = KeyFactory.createKey("test", 1L);
        data.setId(key);
        data.setName("test");
        data.setData("testdata");
        data.setLastChange(new Date());

        Mockito.when(service.getScratchPad("default")).thenReturn(data);

        // final MvcResult result =
        // mockMvc.perform(MockMvcRequestBuilders.get("/rest/default").accept(MediaType.APPLICATION_JSON))
        // .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.status",
        // Matchers.is("OK")))
        // .andExpect(MockMvcResultMatchers.jsonPath("$.data",
        // Matchers.is("testdata"))).andReturn();

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/default").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OK")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is("testdata"))).andReturn();

        final String contentType = result.getResponse().getContentType();
        logger.info("contentType = " + contentType);
        final String content = result.getResponse().getContentAsString();
        logger.info("content = " + content);

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
        public MVCControllerRest mvcControllerRest() {
            return new MVCControllerRest();
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

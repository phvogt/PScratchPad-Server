package com.github.phvogt.pscratchpad.server.rest;

import java.io.IOException;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.phvogt.pscratchpad.server.TestUtils;
import com.github.phvogt.pscratchpad.server.dao.ScratchPadService;
import com.github.phvogt.pscratchpad.server.dao.ScratchpadModifiedException;
import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.google.appengine.repackaged.org.joda.time.DateTime;
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
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#getData(String, String)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testGetData() throws Exception {

	final String testName = "default";
	final String testData = "testData";
	final Date testTime = new Date();

	final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

	Mockito.when(service.getScratchPad(testName)).thenReturn(returnData);

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders.get("/" + IConstantsREST.URL_REST + "/" + testName)
			.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OK")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(testData))).andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#getData(String, String)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testGetDataBefore() throws Exception {

	final String testName = "default";
	final String testData = "testData";
	final Date testTime = new Date();

	final ScratchPad returnDataBefore = TestUtils.createTestData(testName, testData,
		new DateTime(testTime).minusMinutes(1).toDate());

	Mockito.when(service.getScratchPadBefore(testName, testTime)).thenReturn(returnDataBefore);

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
			.get("/" + IConstantsREST.URL_REST + "/" + testName + "?before=" + testTime.getTime())
			.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OK")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(testData))).andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#getData(String, String)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testGetDataNotFound() throws Exception {

	final String testName = "default";

	Mockito.when(service.getScratchPad(testName)).thenReturn(null);

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders.get("/" + IConstantsREST.URL_REST + "/" + testName)
			.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NOT_FOUND"))).andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#getData(String, String)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testGetDataNotFoundBefore() throws Exception {

	final String testName = "default";
	final Date testTime = new Date();

	Mockito.when(service.getScratchPadBefore(testName, testTime)).thenReturn(null);

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
			.get("/" + IConstantsREST.URL_REST + "/" + testName + "?before=" + testTime.getTime())
			.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NOT_FOUND"))).andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#saveData(String, ScratchPadRestRequest)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testSetData() throws Exception {

	final String testName = "default";
	final String testData = "testData";
	final Date testTime = new Date();

	final ScratchPadRestRequest requestData = new ScratchPadRestRequest();
	requestData.setData(testData);

	final ScratchPad returnData = TestUtils.createTestData(testName, testData, testTime);

	Mockito.when(service.saveScratchPad(testName, testTime, testData)).thenReturn(returnData);

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
			.post("/" + IConstantsREST.URL_REST + "/" + testName + "/" + testTime.getTime())
			.contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(requestData)))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("OK"))).andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#saveData(String, ScratchPadRestRequest)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testSetDataLastChangeError() throws Exception {

	final String testName = "default";
	final String testData = "testData";

	final ScratchPadRestRequest requestData = new ScratchPadRestRequest();
	requestData.setData(testData);

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders.post("/" + IConstantsREST.URL_REST + "/" + testName + "/asdf")
			.contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(requestData)))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(IConstantsREST.STATUS_ERROR)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message",
			Matchers.is(IConstantsREST.MESSAGE_ERROR_LASTCHANGE_FORMAT)))
		.andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Tests
     * {@link com.github.phvogt.pscratchpad.server.rest.MVCControllerRest#saveData(String, ScratchPadRestRequest)}
     * .
     * 
     * @throws Exception
     *             if an assertion fails
     */
    @Test
    public void testSetDataModified() throws Exception {

	final String testName = "default";
	final String testData = "testData";
	final Date testTime = new Date();

	final String testData2 = "testData\ntestData2";
	final Date testTime2 = new Date(testTime.getTime() - 1000);
	final ScratchPadRestRequest requestData = new ScratchPadRestRequest();
	requestData.setData(testData2);

	Mockito.when(service.saveScratchPad(testName, testTime2, testData2))
		.thenThrow(new ScratchpadModifiedException("modified", testTime, testData));

	final MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
			.post("/" + IConstantsREST.URL_REST + "/" + testName + "/" + testTime2.getTime())
			.contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(requestData)))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(IConstantsREST.STATUS_MODIFIED)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message",
			Matchers.is(IConstantsREST.MESSAGE_ERROR_SCRATCHPAD_MODIFIED)))
		.andReturn();

	final String contentType = result.getResponse().getContentType();
	logger.info("contentType = " + contentType);
	final String content = result.getResponse().getContentAsString();
	logger.info("content = " + content);

    }

    /**
     * Converts the object to JSON as bytes.
     * 
     * @param object
     *            object to convert
     * @return byte[] with JSON
     * @throws IOException
     *             if an error occurs
     */
    private static byte[] convertObjectToJsonBytes(final Object object) throws IOException {
	final ObjectMapper mapper = new ObjectMapper();
	mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	return mapper.writeValueAsBytes(object);
    }

    /**
     * Converts the object from JSON as Object.
     * 
     * @param json
     *            JSON string
     * @param type
     *            type of object
     * @throws IOException
     *             if an error occurs
     */
    private static <E> E convertObjectToJsonBytes(final String json, final Class<E> type) throws IOException {
	final ObjectMapper mapper = new ObjectMapper();
	mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	return mapper.readValue(json, type);
    }

    /**
     * Test configuration setup of beans.
     */
    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

	/**
	 * Controller to test
	 * 
	 * @return controller
	 */
	@Bean
	public MVCControllerRest mvcControllerRest() {
	    return new MVCControllerRest();
	}

	/**
	 * Return mocked service.
	 * 
	 * @return service
	 */
	@Bean
	public ScratchPadService scratchPadService() {
	    return Mockito.mock(ScratchPadService.class);
	}

    }

}

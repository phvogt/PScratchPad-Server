package com.github.phvogt.pscratchpad.server.text;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link com.github.phvogt.pscratchpad.server.text.Textmerger}.
 */
public class TextmergerTest {

    /** logger. */
    private final Logger logger = Logger.getLogger(TextmergerTest.class.getName());

    /**
     * Test
     * {@link com.github.phvogt.pscratchpad.server.text.Textmerger#mergeText(String, String)}
     * .
     * 
     * @throws Exception
     *             if an error occurs
     */
    @Test
    public void testMergeText() throws Exception {

	final String methodname = "testMergeText(): ";

	logger.info(methodname);
	final String original = IOUtils.toString(new FileInputStream(new File("src/test/resources/diff/text1.txt")));
	final String revised = IOUtils.toString(new FileInputStream(new File("src/test/resources/diff/text2.txt")));

	final Textmerger dut = new Textmerger();
	final String result = dut.mergeText(original, revised);

	logger.info(methodname + "result = " + result);
	Assert.assertNotNull(result);

    }

    /**
     * Test
     * {@link com.github.phvogt.pscratchpad.server.text.Textmerger#mergeText(String, String)}
     * .
     * 
     * @throws Exception
     *             if an error occurs
     */
    @Test
    public void testMergeTextShort() throws Exception {

	final String methodname = "testMergeTextShort(): ";

	logger.info(methodname);

	final Textmerger dut = new Textmerger();

	Assert.assertEquals("testData\n+testData2\n", dut.mergeText("testData", "testData\ntestData2"));
	Assert.assertEquals("-testData\n+testData2\n", dut.mergeText("testData", "testData2"));
	Assert.assertEquals("", dut.mergeText("", ""));
	Assert.assertEquals("+testData\ntestData2\n", dut.mergeText("testData2", "testData\ntestData2"));
	Assert.assertEquals("testData\n+testData3\ntestData2\n",
		dut.mergeText("testData\ntestData2", "testData\ntestData3\ntestData2"));

    }

    /**
     * Test
     * {@link com.github.phvogt.pscratchpad.server.text.Textmerger#mergeText(String, String)}
     * .
     * 
     * @throws Exception
     *             if an error occurs
     */
    @Test
    public void testMergeTextNull() throws Exception {

	final String methodname = "testMergeTextNull(): ";

	logger.info(methodname);
	final String original = IOUtils.toString(new FileInputStream(new File("src/test/resources/diff/text1.txt")));
	final String revised = IOUtils.toString(new FileInputStream(new File("src/test/resources/diff/text2.txt")));

	final Textmerger dut = new Textmerger();
	final String result1 = dut.mergeText(null, revised);

	logger.info(methodname + "result1 = " + result1);
	Assert.assertNotNull(result1);
	Assert.assertEquals("", result1);

	final String result2 = dut.mergeText(original, null);

	logger.info(methodname + "result2 = " + result2);
	Assert.assertNotNull(result2);
	Assert.assertEquals("", result2);

    }

    /**
     * Test
     * {@link com.github.phvogt.pscratchpad.server.text.Textmerger#mergeText(List
     * <String>, List<String>)}.
     * 
     * @throws Exception
     *             if an error occurs
     */
    @Test
    public void testMergeTextList() throws Exception {

	final String methodname = "testMergeTextList(): ";

	logger.info(methodname);
	final List<String> original = IOUtils
		.readLines(new FileInputStream(new File("src/test/resources/diff/text1.txt")));
	final List<String> revised = IOUtils
		.readLines(new FileInputStream(new File("src/test/resources/diff/text2.txt")));

	final Textmerger dut = new Textmerger();
	final String result = dut.mergeText(original, revised);

	logger.info(methodname + "result = " + result);
	Assert.assertNotNull(result);

    }

}

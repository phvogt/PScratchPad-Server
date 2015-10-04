// (c) 2014 by Philipp Vogt
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
     * @throws Exception if an error occurs
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
     * @throws Exception if an error occurs
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
     * @throws Exception if an error occurs
     */
    @Test
    public void testMergeTextList() throws Exception {

        final String methodname = "testMergeTextList(): ";

        logger.info(methodname);
        final List<String> original = IOUtils.readLines(new FileInputStream(new File("src/test/resources/diff/text1.txt")));
        final List<String> revised = IOUtils.readLines(new FileInputStream(new File("src/test/resources/diff/text2.txt")));

        final Textmerger dut = new Textmerger();
        final String result = dut.mergeText(original, revised);

        logger.info(methodname + "result = " + result);
        Assert.assertNotNull(result);

    }

}

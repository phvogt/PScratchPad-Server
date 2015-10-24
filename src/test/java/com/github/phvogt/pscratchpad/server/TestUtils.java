package com.github.phvogt.pscratchpad.server;

import java.util.Date;

import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Test utilities.
 */
public class TestUtils {

    /**
     * Creates the test data.
     * @param testName test name
     * @param testData test data
     * @param testTime test time
     * @return request object
     */
    public static ScratchPad createTestData(final String testName, final String testData, final Date testTime) {

        final ScratchPad returnData = new ScratchPad();
        final Key key = KeyFactory.createKey("test", 1L);
        returnData.setId(key);
        returnData.setName(testName);
        returnData.setData(testData);
        returnData.setLastChange(testTime);

        return returnData;
    }

}

package com.github.phvogt.pscratchpad.server.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.phvogt.pscratchpad.server.config.IConstants;
import com.github.phvogt.pscratchpad.server.dao.ScratchPadService;
import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;

/**
 * Controller for REST calls.
 */
@RestController
public class MVCControllerRest {

    /** Logger. */
    private final Logger logger = Logger.getLogger(MVCControllerRest.class.getName());

    /** Service for ScratchPad. */
    @Autowired
    private ScratchPadService service;

    /**
     * Get data of ScratchPad.
     * @param name name of the scratchpad
     * @return response
     */
    @RequestMapping(value = "/" + IConstantsREST.URL_REST + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME
            + "}", method = RequestMethod.GET)
    @ResponseBody
    public ScratchPadRestResponse getData(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name) {

        final String methodname = "getData(): ";
        logger.log(Level.INFO, methodname + "name = " + name);

        final ScratchPadRestResponse result = new ScratchPadRestResponse();
        result.setStatus(IConstantsREST.STATUS_OK);
        result.setMessage(IConstantsREST.MESSAGE_OK);

        final ScratchPad scratchpad = service.getScratchPad(name);
        final String data = scratchpad.getData();
        result.setData(data);

        logger.log(Level.INFO, methodname + "result = " + result);

        return result;
    }

    /**
     * Saves the data of ScratchPad.
     * @param name name of the scratchpad
     * @param data data to save
     * @return response
     */
    @RequestMapping(value = "/" + IConstantsREST.URL_REST + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME
            + "}", method = RequestMethod.POST)
    @ResponseBody
    public ScratchPadRestBaseResponse saveData(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name,
            @RequestBody final ScratchPadRestRequest data) {

        final String methodname = "saveData(): ";
        logger.log(Level.INFO, methodname + "name = " + name + " data = " + data);

        final String text = data.getData();

        final ScratchPad scratchpad = service.saveScratchPad(name, text);
        logger.log(Level.INFO, methodname + "scratchpad = " + scratchpad);

        final ScratchPadRestBaseResponse result = new ScratchPadRestBaseResponse();
        result.setStatus(IConstantsREST.STATUS_OK);
        result.setMessage(IConstantsREST.MESSAGE_OK);

        return result;
    }
}

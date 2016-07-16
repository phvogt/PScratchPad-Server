package com.github.phvogt.pscratchpad.server.rest;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.phvogt.pscratchpad.server.config.IConstants;
import com.github.phvogt.pscratchpad.server.dao.ScratchPadService;
import com.github.phvogt.pscratchpad.server.dao.ScratchpadModifiedException;
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
     * 
     * @param name
     *            name of the scratchpad
     * @param before
     *            optional parameter with timestamp to get the ScratchPad before
     *            this value
     * @return response
     */
    @RequestMapping(value = "/" + IConstantsREST.URL_REST + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME
	    + "}", method = RequestMethod.GET)
    @ResponseBody
    public ScratchPadRestResponse getData(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name,
	    @RequestParam(value = "before", required = false) final String before) {

	final String methodname = "getData(): ";
	logger.log(Level.INFO, methodname + "name = " + name + " before = " + before);

	final ScratchPadRestResponse result = new ScratchPadRestResponse();
	result.setStatus(IConstantsREST.STATUS_OK);
	result.setMessage(IConstantsREST.MESSAGE_OK);

	ScratchPad scratchpad;
	if (StringUtils.isNotEmpty(before)) {
	    scratchpad = service.getScratchPadBefore(name, new Date(Long.valueOf(before)));
	} else {
	    scratchpad = service.getScratchPad(name);
	}

	if (scratchpad == null) {
	    result.setStatus(IConstantsREST.STATUS_NOT_FOUND);
	    result.setMessage(IConstantsREST.MESSAGE_ERROR_STATUS_NOT_FOUND);
	} else {
	    final String data = scratchpad.getData();
	    result.setData(data);
	    result.setLastChange(scratchpad.getLastChange().getTime());
	}

	logger.log(Level.INFO, methodname + "result = " + result);

	return result;
    }

    /**
     * Saves the data of ScratchPad.
     * 
     * @param name
     *            name of the scratchpad
     * @param lastChange
     *            last change date of the old ScratchPad
     * @param data
     *            data of the new ScratchPad
     * @return response
     */
    @RequestMapping(value = "/" + IConstantsREST.URL_REST + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME + "}/{"
	    + IConstants.PATH_PARAM_SCRATCHPAD_LAST_CHANGE + "}", method = RequestMethod.POST)
    @ResponseBody
    public ScratchPadRestResponse saveData(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name,
	    @PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_LAST_CHANGE) final String lastChange,
	    @RequestBody final ScratchPadRestRequest data) {

	final String methodname = "saveData(): ";
	logger.log(Level.INFO, methodname + "name = " + name + " lastChange = " + lastChange + " data = " + data);

	final String text = data.getData();

	ScratchPad scratchpad;
	try {
	    scratchpad = service.saveScratchPad(name, new Date(Long.valueOf(lastChange)), text);
	} catch (final NumberFormatException e) {
	    final ScratchPadRestResponse result = new ScratchPadRestResponse();
	    result.setStatus(IConstantsREST.STATUS_ERROR);
	    result.setMessage(IConstantsREST.MESSAGE_ERROR_LASTCHANGE_FORMAT);
	    return result;
	} catch (final ScratchpadModifiedException e) {
	    // inform client
	    final ScratchPadRestResponse result = new ScratchPadRestResponse();
	    result.setStatus(IConstantsREST.STATUS_MODIFIED);
	    result.setMessage(IConstantsREST.MESSAGE_ERROR_SCRATCHPAD_MODIFIED);
	    result.setLastChange(e.getCurrentLastChange().getTime());
	    result.setData(e.getCurrentData());
	    return result;
	}

	// no merging necessary, return result
	logger.log(Level.INFO, methodname + "scratchpad = " + scratchpad);

	final ScratchPadRestResponse result = new ScratchPadRestResponse();
	result.setStatus(IConstantsREST.STATUS_OK);
	result.setMessage(IConstantsREST.MESSAGE_OK);
	result.setLastChange(scratchpad.getLastChange().getTime());
	result.setData(scratchpad.getData());
	return result;
    }

}

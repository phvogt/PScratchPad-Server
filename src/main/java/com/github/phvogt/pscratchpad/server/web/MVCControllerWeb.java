// (c) 2015 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.github.phvogt.pscratchpad.server.config.IConstants;
import com.github.phvogt.pscratchpad.server.dao.ScratchPadService;
import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.github.phvogt.pscratchpad.server.utils.RequestHelper;

@Controller
public class MVCControllerWeb {

    /** Logger. */
    private Logger logger = Logger.getLogger(MVCControllerWeb.class.getName());

    /** Service for ScratchPad. */
    @Autowired
    private ScratchPadService service;

    /**
     * Show start page.
     * @return new View
     */
    @RequestMapping(value = "/")
    private View doIndex() {
        return new RedirectView("/" + IConstantsRequest.URL_LOAD + "/" + IConstants.DEFAULT_NAME);
    }

    /**
     * Load text.
     * @param model model
     * @param name name of the scratchpad
     * @return dispatch target
     */
    @RequestMapping(value = "/" + IConstantsRequest.URL_LOAD + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME + "}")
    private String doLoad(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name, final Model model) {

        final String methodname = "doLoad(): ";
        logger.log(Level.INFO, methodname + "name = " + name);

        final String nameToUse = RequestHelper.getName(name);
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_NAME, name);

        final ScratchPad scratchpad = service.getScratchPad(nameToUse);
        final String daten = scratchpad.getData();
        final Long saveTime = scratchpad.getLastChange().getTime();

        model.addAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_TEXT, daten);
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_FILE_TIMESTAMP, saveTime);

        logger.log(Level.INFO, methodname + "name = " + name + " saveTime = " + saveTime + " daten = " + daten);

        return IConstantsRequest.MVC_TARGET_INDEX;
    }

    /**
     * Save text.
     * @param name name of the scratchpad
     * @param data data to save
     * @param model model
     * @return dispatch target
     */
    @RequestMapping(value = "/" + IConstantsRequest.URL_SAVE + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME + "}")
    private String doSave(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name,
            @RequestParam(value = IConstantsRequest.REQUEST_PARAM_EDITOR_FORM_SCRATCHPAD, required = false) final String data,
            final Model model) {

        final String methodname = "doSave(): ";
        logger.log(Level.INFO, methodname + "name = " + name + " data = " + data);

        if (data == null) {
            throw new ParameterMissingException(IConstantsRequest.REQUEST_PARAM_EDITOR_FORM_SCRATCHPAD);
        }

        String result = IConstantsRequest.MVC_TARGET_ERROR;

        final String nameToUse = RequestHelper.getName(name);
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_NAME, name);

        final ScratchPad scratchpad = service.saveScratchPad(nameToUse, data);

        final long saveTime = scratchpad.getLastChange().getTime();
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_TEXT, data);
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_FILE_TIMESTAMP, saveTime);
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_CHANGED_MESSAGE, "changed.saved");

        result = IConstantsRequest.MVC_TARGET_INDEX;

        return result;
    }

    /**
     * Download the file.
     * @param name name of the scratchpad
     * @param model model
     * @param response response
     * @return dispatch target
     */
    @RequestMapping(value = "/" + IConstantsRequest.URL_DOWNLOAD + "/{" + IConstants.PATH_PARAM_SCRATCHPAD_NAME + "}")
    public String doDownload(@PathVariable(IConstants.PATH_PARAM_SCRATCHPAD_NAME) final String name, final Model model,
            final HttpServletResponse response) {

        final String methodname = "doDownload(): ";
        logger.log(Level.INFO, methodname + "name = " + name);

        final String nameToUse = RequestHelper.getName(name);
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_NAME, name);

        final String jsp = IConstantsRequest.MVC_TARGET_DOWNLOAD;

        final ScratchPad scratchpad = service.getScratchPad(nameToUse);
        final String filename = scratchpad.getName();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".txt\"");

        final String text = scratchpad.getData();
        model.addAttribute(IConstantsRequest.REQUEST_ATTR_TEXT, text);

        logger.log(Level.INFO, methodname + "nameToUse = " + nameToUse + " text = " + text);

        return jsp;

    }

}

// (c) 2015 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;

/**
 * Service for ScratchPad.
 */
@Service
public class ScratchpadServiceImpl implements ScratchPadService {

    /** Logger. */
    private Logger logger = Logger.getLogger(ScratchpadServiceImpl.class.getName());

    /** Repository to use. */
    @Resource
    private ScratchPadRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ScratchPad getScratchPad(final String name) {

        final String methodname = "getScratchPad(): ";
        logger.log(Level.INFO, methodname + "name = " + name);

        ScratchPad result = new ScratchPad();
        result.setName(name);
        result.setData("");
        result.setLastChange(new Date());

        final List<ScratchPad> scratchpads = repository.findByName(name);
        if (scratchpads != null && !scratchpads.isEmpty()) {
            result = scratchpads.get(0);
        }

        logger.log(Level.INFO, methodname + "name = " + name + " result = " + result);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScratchPad saveScratchPad(final String name, final String data) {

        final String methodname = "saveScratchPad(): ";
        logger.log(Level.INFO, methodname + "name = " + name + " data = " + data);

        final ScratchPad result = getScratchPad(name);
        result.setData(data);
        result.setLastChange(new Date());
        repository.save(result);

        return result;
    }

}

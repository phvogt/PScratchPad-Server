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
	result.setLastChange(new Date(0));

	final List<ScratchPad> scratchpads = repository.findByNameOrderByLastChangeDesc(name);
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
    public ScratchPad saveScratchPad(final String name, final Date lastChange, final String data)
	    throws ScratchpadModifiedException {

	final String methodname = "saveScratchPad(): ";
	logger.log(Level.INFO, methodname + "name = " + name + " data = " + data);

	final ScratchPad lastPad = getScratchPad(name);
	if (lastPad.getLastChange().after(lastChange)) {
	    throw new ScratchpadModifiedException(name + " modified", lastPad.getLastChange(), lastPad.getData());
	}

	final ScratchPad result = new ScratchPad();
	result.setName(name);
	result.setData(data);
	result.setLastChange(new Date());
	final ScratchPad savedResult = repository.save(result);

	return savedResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countScratchPads(final String name) {

	final String methodname = "countScratchPads(): ";
	logger.log(Level.INFO, methodname + "name = " + name);

	final Long result = repository.countByName(name);

	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScratchPad getScratchPadBefore(final String name, final Date lastChange) {

	final String methodname = "getScratchPadBefore(): ";
	logger.log(Level.INFO, methodname + "name = " + name + " lastChange = " + lastChange);

	ScratchPad result = null;

	final List<ScratchPad> pads = repository.findByNameAndLastChangeLessThanOrderByLastChangeDesc(name, lastChange);
	if (pads != null && !pads.isEmpty()) {
	    result = pads.get(0);
	}

	return result;
    }

}

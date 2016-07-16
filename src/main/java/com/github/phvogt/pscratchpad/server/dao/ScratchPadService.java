package com.github.phvogt.pscratchpad.server.dao;

import java.util.Date;

import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;

/**
 * ScratchPad service.
 */
public interface ScratchPadService {

    /**
     * Gets the ScratchPad from the database or an empty one.
     * 
     * @param name
     *            name of the scratchpad
     * @return the ScratchPad
     */
    ScratchPad getScratchPad(final String name);

    /**
     * Saves data of ScratchPad.
     * 
     * @param name
     *            name of the scratchpad
     * @param lastChange
     *            last change of old ScratchPad with this name
     * @param data
     *            data to save
     * @return the ScratchPad
     * @throws ScratchpadModifiedException
     *             if the Scratchpad has been modified since lastChange
     */
    ScratchPad saveScratchPad(final String name, final Date lastChange, final String data)
	    throws ScratchpadModifiedException;

    /**
     * Gets the number of ScratchPads from the database.
     * 
     * @param name
     *            name of the scratchpad
     * @return number of ScratchPad
     */
    Long countScratchPads(final String name);

    /**
     * Get the previous ScratchPad.
     * 
     * @param name
     *            name
     * @param lastChange
     *            last change of the ScratchPad after the one to get
     * @return the previous ScratchPad
     */
    ScratchPad getScratchPadBefore(final String name, final Date lastChange);

}

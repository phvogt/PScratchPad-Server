package com.github.phvogt.pscratchpad.server.dao;

import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;

/**
 * ScratchPad service.
 */
public interface ScratchPadService {

    /**
     * Gets the ScratchPad from the database or an empty one.
     * @param name name of the scratchpad
     * @return the ScratchPad
     */
    ScratchPad getScratchPad(final String name);

    /**
     * Saves data of ScratchPad.
     * @param name name of the scratchpad
     * @param data data to save
     * @return the ScratchPad
     */
    ScratchPad saveScratchPad(final String name, final String data);

}

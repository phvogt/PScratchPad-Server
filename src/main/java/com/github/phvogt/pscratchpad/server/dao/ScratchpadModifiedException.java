package com.github.phvogt.pscratchpad.server.dao;

import java.util.Date;

/**
 * Exception that the Scratchpad was modified.
 */
public class ScratchpadModifiedException extends Exception {

    /** serial version UID. */
    private static final long serialVersionUID = -4162310000026555011L;

    /** current last change in database. */
    private final Date currentLastChange;

    /** current data in database. */
    private final String currentData;

    /**
     * Constructor.
     * 
     * @param message
     *            message
     * @param currentLastChange
     *            current last change
     * @param currentData
     *            current data
     */
    public ScratchpadModifiedException(final String message, final Date currentLastChange, final String currentData) {
	super(message);
	this.currentLastChange = currentLastChange;
	this.currentData = currentData;
    }

    /**
     * Get the currentLastChange.
     * 
     * @return the currentLastChange
     */
    public Date getCurrentLastChange() {
	return currentLastChange;
    }

    /**
     * Get the currentData.
     * 
     * @return the currentData
     */
    public String getCurrentData() {
	return currentData;
    }

}

package com.github.phvogt.pscratchpad.server.rest;

/**
 * Bean with REST response.
 */
public class ScratchPadRestResponse extends ScratchPadRestBaseResponse {

    /** contains data. */
    private String data;

    /** last change. */
    private long lastChange;

    /**
     * Get the data.
     * 
     * @return the data
     */
    public String getData() {
	return data;
    }

    /**
     * Sets the data.
     * 
     * @param data
     *            the data to set
     */
    public void setData(final String data) {
	this.data = data;
    }

    /**
     * Get the lastChange.
     * 
     * @return the lastChange
     */
    public long getLastChange() {
	return lastChange;
    }

    /**
     * Sets the lastChange.
     * 
     * @param lastChange
     *            the lastChange to set
     */
    public void setLastChange(final long lastChange) {
	this.lastChange = lastChange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	final StringBuilder builder = new StringBuilder();
	builder.append("ScratchPadRestResponse [super = ");
	builder.append(super.toString());
	builder.append(" data=");
	builder.append(data);
	builder.append(", lastChange=");
	builder.append(lastChange);
	builder.append("]");
	return builder.toString();
    }

}

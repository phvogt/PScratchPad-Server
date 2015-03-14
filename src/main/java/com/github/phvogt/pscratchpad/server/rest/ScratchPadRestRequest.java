// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.rest;

/**
 * Bean with REST response.
 */
public class ScratchPadRestRequest {

    /** contains data. */
    private String data;

    /**
     * Get the data.
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data.
     * @param data the data to set
     */
    public void setData(final String data) {
        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ScratchPadRestRequest [super=");
        builder.append(super.toString());
        builder.append(", data=");
        builder.append(data);
        builder.append("]");
        return builder.toString();
    }

}

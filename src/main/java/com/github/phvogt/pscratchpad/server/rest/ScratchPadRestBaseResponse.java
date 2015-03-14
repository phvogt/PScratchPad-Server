// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.rest;

/**
 * Bean with REST response.
 */
public class ScratchPadRestBaseResponse {

    /** Contains a status. */
    private String status;

    /** Contains an optional message. */
    private String message;

    /**
     * Get the status.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Get the message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ScratchPadRestBaseResponse [super=");
        builder.append(super.toString());
        builder.append(", status=");
        builder.append(status);
        builder.append(", message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }

}

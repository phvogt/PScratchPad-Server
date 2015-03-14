// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.exceptions;

/**
 * Exception if a message was not found.
 */
public class MessageNotFoundException extends BaseRuntimeException {

    /** serial version UID. */
    private static final long serialVersionUID = -4768727384182284598L;

    /**
     * Constructor.
     * @param messageKey key of the message that was not found
     */
    public MessageNotFoundException(final String messageKey) {
        super("could not find message with key \"" + messageKey + "\"");
    }

}

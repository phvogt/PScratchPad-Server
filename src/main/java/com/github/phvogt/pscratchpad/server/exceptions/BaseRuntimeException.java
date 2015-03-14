// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.exceptions;

/**
 * Base class for runtime exceptions.
 */
public class BaseRuntimeException extends RuntimeException {

    /** serial version UID. */
    private static final long serialVersionUID = 1804205585265317763L;

    /**
     * Constructor.
     */
    public BaseRuntimeException() {
        super();
    }

    /**
     * Constructor.
     * @param message message
     */
    public BaseRuntimeException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param cause cause
     */
    public BaseRuntimeException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * @param message message
     * @param cause cause
     */
    public BaseRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

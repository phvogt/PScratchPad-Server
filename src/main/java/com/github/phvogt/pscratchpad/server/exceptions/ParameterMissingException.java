// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.exceptions;

/**
 * Exception if a parameter was missing.
 */
public class ParameterMissingException extends BaseRuntimeException {

    /** serial version UID. */
    private static final long serialVersionUID = -8286210354085930073L;

    /**
     * Constructor.
     * @param parameter name of the parameter that was not found
     */
    public ParameterMissingException(final String parameter) {
        super("could not find parameter with name \"" + parameter + "\"");
    }

}

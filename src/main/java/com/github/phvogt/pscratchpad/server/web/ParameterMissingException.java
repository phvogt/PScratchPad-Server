package com.github.phvogt.pscratchpad.server.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.phvogt.pscratchpad.server.exceptions.BaseRuntimeException;

/**
 * Exception if a parameter was missing.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
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

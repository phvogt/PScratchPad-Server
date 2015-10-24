package com.github.phvogt.pscratchpad.server.utils;

import javax.servlet.http.HttpServletRequest;

import com.github.phvogt.pscratchpad.server.config.IConstants;

/**
 * Helper for request handling.
 */
public final class RequestHelper {

    /**
     * Hide constructor.
     */
    private RequestHelper() {
        // intentionally blank
    }

    /**
     * Checks if the request contains the parameter.
     * @param request request
     * @param paramName name of parameter
     * @return true if parameter is present
     */
    public static boolean containsParameter(final HttpServletRequest request, final String paramName) {

        boolean result = false;

        final String parameter = request.getParameter(paramName);
        if (parameter != null) {
            result = true;
        }

        return result;
    }

    /**
     * Gets the name or default if provided name is null.
     * @param name name of scratchpad
     * @return name
     */
    public static String getName(final String name) {

        String result = name;
        if (result == null) {
            result = IConstants.DEFAULT_NAME;
        }
        return result;
    }
}

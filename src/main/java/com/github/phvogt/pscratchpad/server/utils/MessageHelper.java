// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.github.phvogt.pscratchpad.server.config.IConstants;
import com.github.phvogt.pscratchpad.server.exceptions.MessageNotFoundException;

/**
 * Helper for localization.
 */
public final class MessageHelper {

    /** messages. */
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle(IConstants.MESSAGES_RESSOURCE);

    /**
     * Hide constructor.
     */
    private MessageHelper() {
        // intentionally blank
    }

    /**
     * Localize key with params.
     * @param key key
     * @param params params
     * @return returns message
     */
    public static String get(final String key, final String[] params) {

        final String message = MESSAGES.getString(key);
        if (message == null) {
            throw new MessageNotFoundException(key);
        }

        final MessageFormat formatter = new MessageFormat(message);
        final String result = formatter.format(params);

        return result;
    }

}

// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.config;

/**
 * Constants for requests.
 */
public interface IConstantsRequest {

    /** request parameter for form field. */
    String REQUEST_PARAM_EDITOR_FORM_SCRATCHPAD = "scratchpad";

    /** request attribute for text. */
    String REQUEST_ATTR_EDITOR_TEXT = "ATTR_TEXT";
    /** request attribute for file time stamp. */
    String REQUEST_ATTR_EDITOR_FILE_TIMESTAMP = "ATTR_FILE_TIMESTAMP";
    /** request parameter for the changed message. */
    String REQUEST_ATTR_EDITOR_CHANGED_MESSAGE = "REQUEST_ATTR_EDITOR_CHANGED_MESSAGE";
    /** request attribute for name. */
    String REQUEST_ATTR_NAME = "ATTR_NAME";

    /** DIV tag for changed. */
    String HTML_ID_DIV_CHANGED = "changed";

    // download
    /** download request attribute for text. */
    String REQUEST_ATTR_TEXT = "REQUEST_ATTR_TEXT";

    // error
    /** requst attribute for error message. */
    String REQUEST_ATTR_ERROR_MESSAGE = "REQUEST_ATTR_ERROR_MESSAGE";

}

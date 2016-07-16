package com.github.phvogt.pscratchpad.server.rest;

/**
 * Constants for REST api.
 */
public interface IConstantsREST {

    /** URL for REST. */
    String URL_REST = "rest";

    /** Status ok. */
    String STATUS_OK = "OK";
    /** Status error. */
    String STATUS_ERROR = "ERROR";
    /** Status modified. */
    String STATUS_MODIFIED = "MODIFIED";
    /** Status not found. */
    String STATUS_NOT_FOUND = "NOT_FOUND";

    /** message. */
    String MESSAGE_OK = "OK";
    /** message if the last change format is not ok. */
    String MESSAGE_ERROR_LASTCHANGE_FORMAT = "Last change format error.";
    /** message if the Scratchpad is already modified. */
    String MESSAGE_ERROR_SCRATCHPAD_MODIFIED = "Scrachpad modfied.";
    /** message if no Scratchpad was found. */
    String MESSAGE_ERROR_STATUS_NOT_FOUND = "Scratchpad not found.";

}

// (c) 2013 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.config;

import java.io.File;

/**
 * Constants.
 */
public interface IConstants {

    /** configuration base path. */
    String CONFIG_BASEPATH = "pscratchpad" + File.separator;

    /** log4j properties file. */
    String LOG4J_PROPERTIES_FILE = CONFIG_BASEPATH + "log4j.properties";
    /** configuration file name. */
    String CONFIG_FILENAME = CONFIG_BASEPATH + "pscratchpad.properties";

    /** parameter for save file in config file. */
    String CONFIG_FILE_PARAMETER_SAVE_FILE = "savefile";

    /** Default name. */
    String DEFAULT_NAME = "default";

    /** URL for load. */
    String URL_LOAD = "load";
    /** URL for save. */
    String URL_SAVE = "save";
    /** URL for REST. */
    String URL_REST = "rest";
    /** URL for download. */
    String URL_DOWNLOAD = "download";

    /** Path parameter for name */
    String PATH_PARAM_SCRATCHPAD_NAME = "name";

    /** MVC target to index.jsp. */
    String MVC_TARGET_INDEX = "index";
    /** MVC target to error.jsp. */
    String MVC_TARGET_ERROR = "error";
    /** MVC target to download.jsp. */
    String MVC_TARGET_DOWNLOAD = "download";

    /** ressource with messages. */
    String MESSAGES_RESSOURCE = "messages";
}

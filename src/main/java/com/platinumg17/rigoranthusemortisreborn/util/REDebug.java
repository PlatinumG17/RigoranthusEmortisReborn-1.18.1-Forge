package com.platinumg17.rigoranthusemortisreborn.util;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public class REDebug {

    public static Logger logger = LogManager.getLogger(EmortisConstants.MOD_ID);

    public static void error(Object text) {logger.error(text);}
    public static void errorf(String text, Object... args) {logger.error(String.format(text, args));}
    public static void warn(Object text) {logger.warn(text);}
    public static void warnf(String text, Object... args) {logger.warn(String.format(text, args));}
    public static void info(Object text) {logger.info(text);}
    public static void infof(String text, Object... args) {logger.info(String.format(text, args));}
    public static void debug(Object text) {logger.debug(text);}
    public static void debugf(String text, Object... args) {logger.debug(String.format(text, args));}
}

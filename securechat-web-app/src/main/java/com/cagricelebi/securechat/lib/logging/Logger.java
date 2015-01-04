/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Cagri Celebi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.cagricelebi.securechat.lib.logging;

import java.util.logging.Level;

/**
 * Wrapper for java util logging.
 *
 * @author azureel
 */
public class Logger {

    private final java.util.logging.Logger logger;

    private Logger(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    public static Logger getLogger(String className) {
        return new Logger(java.util.logging.Logger.getLogger(className));
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(java.util.logging.Logger.getLogger(clazz.getName()));
    }

    public void log(Object obj) {
        if (obj == null) {
            logger.log(Level.INFO, "null");
        } else {
            logger.log(Level.INFO, obj.toString());
        }
    }

    public void log(String message) {
        logger.info(message);
    }

    public void log(String message, Object o1, Object o2) {
        logger.log(Level.INFO, message, new Object[]{o1, o2});
    }

    public void log(String message, Object... os) {
        logger.log(Level.INFO, message, os);
    }

    public void log(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }

    public void log(Throwable t) {
        logger.log(Level.SEVERE, t.getMessage(), t);
    }

    public void debug(String message) {
        logger.fine(message);
    }

    public void debug(String message, Object o1, Object o2) {
        logger.log(Level.FINE, message, new Object[]{o1, o2});
    }

    public void debug(String message, Object... os) {
        logger.log(Level.FINE, message, os);
    }

    public void info(String message) {
        logger.log(Level.INFO, message);
    }

    public void info(String message, Object o1, Object o2) {
        logger.log(Level.INFO, message, new Object[]{o1, o2});
    }

    public void info(String message, Object... os) {
        logger.log(Level.INFO, message, os);
    }

    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    public void error(String message, Object o1, Object o2) {
        logger.log(Level.SEVERE, message, new Object[]{o1, o2});
    }

    public void error(String message, Object... os) {
        logger.log(Level.SEVERE, message, os);
    }

    public void error(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }

    public void error(Throwable t) {
        logger.log(Level.SEVERE, t.getMessage(), t);
    }

}

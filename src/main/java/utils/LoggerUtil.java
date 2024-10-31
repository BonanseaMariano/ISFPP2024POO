package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging messages using Apache Log4j.
 */
public class LoggerUtil {
    /**
     * Logger instance for this class.
     */
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    /**
     * Logs an informational message.
     *
     * @param message the message to log
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Logs an error message along with a throwable.
     *
     * @param message   the error message to log
     * @param throwable the throwable to log
     */
    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Logs a debug message.
     *
     * @param message the message to log
     */
    public static void logDebug(String message) {
        logger.debug(message);
    }
}
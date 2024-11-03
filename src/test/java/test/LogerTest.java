package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LogerTest is a test class for demonstrating the usage of Log4j logging.
 * It includes various logging levels and simulates an error to show how exceptions are logged.
 */
public class LogerTest {
    /**
     * Logger instance for logging messages.
     */
    private static final Logger logger = LogManager.getLogger(LogerTest.class);

    /**
     * Demonstrates logging at different levels and simulates an error.
     * Logs messages at TRACE, DEBUG, INFO, ERROR, and FATAL levels.
     */
    public void miMetodo() {
        logger.trace("Este es un mensaje TRACE - no se mostrará si el nivel es INFO");
        logger.debug("Este es un mensaje DEBUG - no se mostrará si el nivel es INFO");

        logger.info("Este es un mensaje INFO - se mostrará");

        try {
            // Simulación de un error
            int x = 1 / 0;
            throw new RuntimeException("Error simulado");
        } catch (RuntimeException e) {
            logger.error("Se ha producido un error: {}", e.getMessage());
        }

        logger.fatal("Este es un mensaje FATAL - se mostrará");
    }

    /**
     * Main method to run the LogerTest.
     * Creates an instance of LogerTest and calls the miMetodo method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        LogerTest instancia = new LogerTest();
        instancia.miMetodo();
    }
}
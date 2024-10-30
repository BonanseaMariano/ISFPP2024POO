package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogerTest {
    private static final Logger logger = LogManager.getLogger(LogerTest.class);

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

    public static void main(String[] args) {
        LogerTest instancia = new LogerTest();
        instancia.miMetodo();
    }
}

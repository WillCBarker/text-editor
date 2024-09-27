package com.willcb.projects.texteditor;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;

public class LoggerUtil {

    private static Logger logger;

    // Private constructor to prevent instantiation
    private LoggerUtil() {}

    // Method to initialize and return the logger instance
    public static Logger getLogger() {
        if (logger == null) {
            try {
                // Create a logger instance
                logger = Logger.getLogger("GlobalLogger");

                // Remove the default console handler (if present)
                Logger rootLogger = Logger.getLogger("");
                Handler[] handlers = rootLogger.getHandlers();
                for (Handler handler : handlers) {
                    if (handler instanceof ConsoleHandler) {
                        rootLogger.removeHandler(handler);
                    }
                }

                // Add a file handler to write to "debug.log"
                FileHandler fileHandler = new FileHandler("debug.log", true); // Append to the log file
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
                
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false); // Disable logging to parent handlers (console)

                // Set the logging level (FINE for detailed logging)
                logger.setLevel(java.util.logging.Level.FINE);

            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
}

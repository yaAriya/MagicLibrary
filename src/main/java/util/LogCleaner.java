package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.IOException;

public class LogCleaner {

    public static void clearLogFile(String logFilePath) {
        try {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            context.stop();

            clearFileContent(logFilePath);

            context.reconfigure();
        } catch (Exception e) {
            System.err.println("Error with cleaning logs file " + e.getMessage());
        }
    }

    private static void clearFileContent(String filePath) throws IOException {
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filePath, "rw")) {
            raf.setLength(0);
        }
    }
}
package edu.upenn.cit594.logging;

import javax.naming.OperationNotSupportedException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private static Logger logger = null;
    private static String filePath = null;
    private PrintWriter out;

    public static Logger getInstance() throws OperationNotSupportedException, IOException {
        if (filePath == null) {
            throw new OperationNotSupportedException("Cannot create instance without the file path");
        }
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public static void setFilePath(String path) {
        filePath = path;
    }

    private Logger() throws IOException {
    	out = new PrintWriter(new FileWriter(filePath, false));
    }

    public void log(String string) throws IOException {
    	out.println(string);
    	out.flush();
    }
}
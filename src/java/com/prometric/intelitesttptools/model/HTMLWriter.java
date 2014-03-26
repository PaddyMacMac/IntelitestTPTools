package com.prometric.intelitesttptools.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Patrick.MacCnaimhin
 */
public class HTMLWriter extends IWriter {
    private static final Logger LOGGER = Logger.getLogger(HTMLWriter.class.getName());
    
    @Override
    public void writeFileContent(File htmlFile, String content) {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write(content);
        } 
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to copy file contents");
        } 
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Unable to close writer");
            }
        }
    }    
}

package com.prometric.intelitesttptools.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
/**
 * @author Patrick.MacCnaimhin
 */
public abstract class IWriter {
    private static final Logger LOGGER = Logger.getLogger(IWriter.class.getName());
    
    public abstract void writeFileContent(File htmlFile, String content);

    public boolean makeDirs(String path) {
        return new File(path).mkdirs();
    }
    
    public void deleteDir(File file) {
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Unable to delete file: {0}, as it is locked", file);
        }
    }

    public void copyZipFile(String src, String dest)  {
        try {
            FileUtils.copyFile(new File(src), new File(dest));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Unable to to copy files from: {0} to: {1}", new Object[]{src, dest});
        }
    }
}

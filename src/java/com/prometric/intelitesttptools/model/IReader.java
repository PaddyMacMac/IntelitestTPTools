package com.prometric.intelitesttptools.model;

import com.prometric.intelitesttptools.utils.PropertiesFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * @author Patrick Mac Cnaimhin
 */
public abstract class IReader {
    private static final Logger LOGGER = Logger.getLogger(IReader.class.getName());
    private static final int MEGA_BYTE = 1024;
    private Properties props = PropertiesFactory.getProperties();
    protected Map<String, File> htmlFiles;

    public void unzip(String zipSource, String zipDestination) throws ZipException {
        ZipFile zipFile = new ZipFile(zipSource);
        zipFile.extractAll(zipDestination);
        new File(zipSource).delete();
    }

    public void zip(final String zipPath) {
        try {
            ZipFile zipFile = new ZipFile(zipPath + props.getProperty("ZIP_EXTENSION"));
            String folderToAdd = zipPath;
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFolder(folderToAdd, parameters);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to Zip file...");
        }
    }

    public void collectHTMLFiles(File folder) {
        if (folder != null) {
            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    collectHTMLFiles(fileEntry);
                } else {
                    if (fileEntry.getName().endsWith(props.getProperty("HTML_EXTENSION"))) {
                        htmlFiles.put(fileEntry.getName(), fileEntry);
                    }
                }
            }
        }
    }

    public String getHTMLContent(File file) {
        StringBuilder content = new StringBuilder(MEGA_BYTE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String s;
                while ((s = br.readLine()) != null) {
                    content.append(s);
                }
                br.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Unable to Read HTML content/line...");
            }
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Unable to Find File to get html content from...");
        }
        return content.toString();
    }

    public Map<String, File> getHtmlFiles() {
        return htmlFiles;
    }
}

package com.prometric.intelitesttptools.service;

import com.prometric.intelitesttptools.model.HTMLCleaner;
import com.prometric.intelitesttptools.model.HTMLWriter;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;
/**
 * @author Patrick.MacCnaimhin
 */
public class InspectItemService extends IUserService {
    private static final Logger LOGGER = Logger.getLogger(InspectItemService.class.getName());
    private static final String JUNK_CHARS = "\\P{Print}";
    private static final String EMPTY_STRING = "";
    private static final String LT = "&lt;";
    private static final String GT = "&gt;";
    private static final String LESS_THAN_ARROW = "<";
    private static final String GREATER_THAN_ARROW = ">";
    private static final String GREATER_THAN = "&amp;gt;";
    private static final String LESS_THAN = "&amp;lt;";
    private static final String SLASH = "/";
    private File htmlFile;
    private String destination;
    private HTMLCleaner htmlCleaner;
    private Map<String, String> optionHTML;
    private Map<String, String> optionText;

    public InspectItemService(String userName, UploadedFile file) {
        setCurrentUser(userName);
        htmlCleaner = new HTMLCleaner();
        copyUploadedFile(file);
        setCurrentHTMLFile();
        optionHTML = htmlCleaner.getOptionsHTML();
        optionText = htmlCleaner.getOptionsText();
    }

    public String getStemHTML() {
        return replaceHTMLArrows(htmlCleaner.getStemHTML());
    }

    public String getStemText() {
        return htmlCleaner.getStemText();
    }
    
    public String getDestination() {
        return destination;
    }

    public void saveStem(String stem) {
        new HTMLWriter().writeFileContent(new File(destination), replaceJunkChars(htmlCleaner.replaceStem(replaceLTandGT(stem))));
    }

    public void saveOption(String optionIndex, String optionText) {
        new HTMLWriter().writeFileContent(new File(destination), replaceJunkChars(htmlCleaner.replaceOption(optionIndex, optionText)));
    }

    public String getOptionHTML(String optionIndex) {
        return replaceHTMLArrows(optionHTML.get(optionIndex));
    }

    public String getOptionText(String optionIndex) {
        return optionText.get(optionIndex);
    }

    private void setCurrentHTMLFile() {
        htmlCleaner.setHTMLDocument(htmlFile);
        destination = props.getProperty("TMP_DIR") + getCurrentUser() + SLASH + htmlFile.getName();
    }

    private void copyUploadedFile(UploadedFile file) {
        htmlFile = new File(file.getFileName());
        try {
            FileUtils.copyInputStreamToFile(file.getInputstream(), htmlFile);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error copying the file...");
        }
    }
    
    private String replaceJunkChars(String content) {
        return content.replaceAll(JUNK_CHARS, EMPTY_STRING);
    }
    
    private String replaceHTMLArrows(String html) {
        return html.replaceAll(LESS_THAN_ARROW, LESS_THAN).replaceAll(GREATER_THAN_ARROW, GREATER_THAN);
    }
    
    private String replaceLTandGT(String text) {
        return text.replaceAll(LT, LESS_THAN_ARROW).replaceAll(GT, GREATER_THAN_ARROW);
    }
}

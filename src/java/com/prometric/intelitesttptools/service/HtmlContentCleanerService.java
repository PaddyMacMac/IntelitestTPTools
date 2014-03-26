package com.prometric.intelitesttptools.service;

import com.prometric.intelitesttptools.model.HTMLCleaner;
import com.prometric.intelitesttptools.model.HTMLReader;
import com.prometric.intelitesttptools.model.HTMLWriter;
import com.prometric.intelitesttptools.model.UniCodeConverter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.lingala.zip4j.exception.ZipException;
/**
 * @author Patrick.MacCnaimhin
 */
public class HtmlContentCleanerService extends IUserService implements IZipService {
    private static final String SCENARIO = "scenario";
    private static final String OPEN_ARROW = "<";
    private static final String CLOSE_ARROW = ">";
    private static final String EMPTY_STRING = "";
    private static final String SLASH = "/";
    private static final String BR = "<br>";
    private static final String BR_PROPER = "<br />";
    private String zipDestPat;
    private String zipSrcPath;
    private String downloadFolder;
    private String zipSourceCopyPath;
    private Map<String, File> htmlFiles;
    private HTMLCleaner htmlCleaner;
    private HTMLReader htmlReader;
    private boolean unicodeConvert;
    
    public HtmlContentCleanerService(String userName) {
        setCurrentUser(userName);
        zipSourceCopyPath = props.getProperty("TMP_DIR") + getCurrentUser() +  SLASH + props.getProperty("ZIP_EXTENSION");
    }

    public HtmlContentCleanerService(String userName, List<String> htmlTags) throws ZipException {
        htmlReader = new HTMLReader();
        htmlCleaner = new HTMLCleaner(cleanTags(htmlTags));
        setCurrentUser(userName);
        initialize();
    }

    public String getStemText() {
        return htmlCleaner.getStemText();
    }

    public String getStemHTML() {
        return htmlCleaner.getStemHTML();
    }

    public int cleanAndCopyAll() {
        HTMLWriter htmlWriter = new HTMLWriter();
        htmlWriter.makeDirs(downloadFolder);

        for (File htmlFile : htmlFiles.values()) {
            if (htmlFile.getName().contains(SCENARIO)) {
                continue;
            }
            setHTMLDocument(htmlFile);
            htmlWriter.writeFileContent(new File(downloadFolder + htmlFile.getName()), cleanCurrentHTMLDoc());
        }
        htmlReader.zip(downloadFolder);
        htmlWriter.copyZipFile(downloadFolder + props.getProperty("ZIP_EXTENSION"), zipSourceCopyPath);
        return htmlFiles.size();
    }
     
    public Map<String, File> getHtmlFiles() {
        return htmlFiles;
    }

    public File getCleanContentZipFile() {
        return new File(zipSourceCopyPath);
    }
    
    @Override
    public void unpackHTMLFiles() {
        File unzipFolder = new File(zipDestPat + SLASH + props.getProperty("HTML_FOLDER"));
        htmlReader.collectHTMLFiles(unzipFolder);
        unzipFolder.delete();
        htmlFiles = htmlReader.getHtmlFiles();
    }

    @Override
    public void processZippedContent() throws ZipException {
        htmlReader.unzip(zipSrcPath, zipDestPat);
    }
    
       public void setUnicodeConvert(boolean unicodeConvert) {
        this.unicodeConvert = unicodeConvert;
    }

    public boolean isUnicodeConvert() {
        return unicodeConvert;
    }

    private void initialize() throws ZipException {
        String userDir = props.getProperty("TMP_DIR") + getCurrentUser();
        zipSrcPath = userDir + props.getProperty("HTML_ZIP");
        zipDestPat = userDir + SLASH;
        downloadFolder = userDir + props.getProperty("COPY_FOLDER");
        zipSourceCopyPath = userDir + SLASH + props.getProperty("ZIP_EXTENSION");
        
        HTMLWriter htmlWriter = new HTMLWriter();
        htmlWriter.makeDirs(zipDestPat);
        htmlWriter.makeDirs(downloadFolder);
        processZippedContent();
        unpackHTMLFiles();
    }

    private void setHTMLDocument(File htmlFile) {
        htmlCleaner.setHTMLDocument(htmlFile);
    }

    private String cleanCurrentHTMLDoc() {
        htmlCleaner.whiteListCleanStem();
        String html = htmlCleaner.whiteListCleanOptions();
        html = htmlCleaner.parseHTML(html);
        if(unicodeConvert) {
            html = new UniCodeConverter().unicodeClean(html);
        }
        return html.replace(BR, BR_PROPER);
    }
    
    private List<String> cleanTags(List<String> htmlTags) {
        List<String> newTags = new ArrayList<>();
        for (String tag : htmlTags) {
            newTags.add(tag.replace(OPEN_ARROW, EMPTY_STRING).replace(CLOSE_ARROW, EMPTY_STRING).replace(SLASH, EMPTY_STRING));
        }
        return newTags;
    }
}

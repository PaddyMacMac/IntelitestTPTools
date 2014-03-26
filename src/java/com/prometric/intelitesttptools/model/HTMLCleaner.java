package com.prometric.intelitesttptools.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
/**
 * @author Patrick Mac Cnaimhin
 */
public class HTMLCleaner implements ICleaner {
    private static final Logger LOGGER = Logger.getLogger(HTMLCleaner.class.getName());
    private static final String CHAR_SET = "UTF-8";
    private static final String PROMETRIC = "http://prometric.com";
    private static final String FONT = "font";
    private static final String FACE = "face";
    private static final String SIZE = "size";
    private static final String COLOR = "color";
    private static final String DIV = "div";
    private static final String CLASS = "class";
    private static final String ID = "id";
    private static final String INPUT = "input";
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private static final String ONCLICK = "onclick";
    private static final String IMG = "img";
    private static final String SRC = "src";
    private static final String ALT = "alt";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String SCRIPT = "script";
    private static final String LANGUAGE = "language";
    private static final String BR = "br";
    private static final String BOLD = "b";
    private static final String ITALICS = "i";
    private static final String UNDERLINE = "u";
    private static final String PARAGRAPH = "p";
    private static final String SPAN = "span";
    private static final String STYLE = "style";
    private static final String LABEL = "label";
    private static final String FOR = "for";
    private static final String ACCESS_KEY = "accesskey";
    private static final String STEM_CONTAINER = "div.proint_stemcontainer";
    private static final String OPTION = "div.proint_option";
    private static final String SPACE = "&nbsp;";
    private static final String ONE_SPACE = " ";
    private static final String EMPTY_STRING = "";     
    private static final int ASCII_A = 65;
    private Whitelist whiteList;
    private Document htmlDoc;
    private boolean removeSpaces;

    public HTMLCleaner(List<String> htmlTags) {
        whiteList = new Whitelist();
        tagsToInclude(htmlTags);
        removeSpaces = true;
    }
    
    public HTMLCleaner() {
        removeSpaces = true;
    }

    @Override
    public String cleanContent(String htmlContent) {
        String cleanedHtml = Jsoup.clean(htmlContent, whiteList);
        if (removeSpaces) {
            cleanedHtml = cleanedHtml.replace(SPACE, ONE_SPACE);
        }
        return cleanedHtml;
    }
    
    public String parseHTML(String html) {
        return Jsoup.parse(html).html();
    }

    public void setHTMLDocument(File htmlFile) {
        try {
            htmlDoc = Jsoup.parse(htmlFile, CHAR_SET, PROMETRIC);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public String replaceStem(String newStem) {
        Elements stem = htmlDoc.select(STEM_CONTAINER);
        stem.html(newStem);
        return htmlDoc.html();
    }
    
    private int realIndex(String optionIndex) {
        return Integer.parseInt(optionIndex) - ASCII_A;
    }
    
    public String replaceOption(String optionIndex, String optionText) {
         Elements options = htmlDoc.select(OPTION);
         options.get(realIndex(optionIndex)).text(optionText);
         return htmlDoc.html();
    }
    
    public String whiteListCleanOptions() {
        Elements options = getOptions();
        for (Element option : options) {
            option.html(cleanContent(option.html()));
        }
        return htmlDoc.html();
    }

    public String whiteListCleanStem() {
        Elements stem = htmlDoc.select(STEM_CONTAINER);
        stem.html(cleanContent(getStemHTML()));
        return htmlDoc.html();
    }

    public String getStemHTML() {
        try {
             return getStemContainer().html();
        } catch (NullPointerException ex) {
            LOGGER.log(Level.SEVERE, "Error Trying to get Stem HTML...", ex);
            throw new NullPointerException();
        }
    }

    public String getStemText() {
        try {
             return getStemContainer().text();
        } catch (NullPointerException ex) {
            LOGGER.log(Level.SEVERE, "Error Trying to get Stem Text...", ex);
            throw new NullPointerException();
        }
    }

    public Map<String, String> getOptionsHTML() {
        return getOtions(true);
    }
    
    public Map<String, String> getOptionsText() {
        return getOtions(false);
    }
    
    private Map<String, String> getOtions(boolean getHTML) {
        Map<String, String> options = new HashMap<>();
        Elements ops = getOptions();
        int key = ASCII_A;
        for (Element op : ops) {         
            if (getHTML) {
                options.put(key + EMPTY_STRING, op.html());              
            }
            else {
                options.put(key + EMPTY_STRING, op.text());
            }         
            key++;
        }
        return options;
    }
    
    private void tagsToInclude(List<String> htmlTags) {
        for (String tag : htmlTags) {
            switch (tag.trim()) {
                case BOLD:
                    whiteList.addTags(BOLD);
                    break;
                case ITALICS:
                    whiteList.addTags(ITALICS);
                    break;
                case UNDERLINE:
                    whiteList.addTags(UNDERLINE);
                    break;
                case BR:
                    whiteList.addTags(BR);
                case PARAGRAPH:
                    whiteList.addAttributes(PARAGRAPH, STYLE);
                    break;
                case FONT:
                    whiteList.addAttributes(FONT, FACE, SIZE, COLOR);
                    break;
                case SPAN:
                    whiteList.addAttributes(SPAN, STYLE);
                    break;
                case DIV:
                    whiteList.addAttributes(DIV, CLASS, ID);
                    break;
                case INPUT:
                    whiteList.addAttributes(INPUT, TYPE, TITLE, ONCLICK);
                    break;
                case SCRIPT:
                    whiteList.addAttributes(SCRIPT, TYPE, LANGUAGE, SRC);
                    break;
                case IMG:
                    whiteList.addAttributes(IMG, SRC, ALT, HEIGHT, WIDTH);
                    break;
                case LABEL:
                    whiteList.addAttributes(LABEL, FOR, ACCESS_KEY);
                    break;
                case SPACE:
                    removeSpaces = false;
                    break;
            }
        }
    }

    private Element getStemContainer() {
        return htmlDoc.select(STEM_CONTAINER).first();
    }

    private Elements getOptions() {
        return htmlDoc.select(OPTION);
    }
}

package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.Repository.UserManager;
import com.prometric.intelitesttptools.service.HtmlContentCleanerService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import org.primefaces.model.DualListModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@ViewScoped
public class CleanContentBean extends IUserManagerClient implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CleanContentBean.class.getName());
    private static final String BR = "<br />";
    private static final String BOLD = "<b>";
    private static final String ITALICS = "<i>";
    private static final String UNDERLINE = "<u>";
    private static final String PARAGRAPH = "<p>";
    private static final String FONT = "<font>";
    private static final String SPAN = "<span>";
    private static final String DIV = "<div>";
    private static final String INPUT = "<input>";
    private static final String SCRIPT = "<script>";
    private static final String IMG = "<img>";
    private static final String SPACE = "&nbsp;";
    private DualListModel<String> htmlTags;
    private boolean showDownLoad;
    private boolean showPickButton;
    private UserManager userManager;
    private boolean unicodeConvert;
   
    public CleanContentBean() {
        initialize();
        populateHTMLTags();
        showPickButton = true;
    }

    public DualListModel<String> getHtmlTags() {
        return htmlTags;
    }

    public void setHtmlTags(DualListModel<String> htmlTags) {
        this.htmlTags = htmlTags;
    }

    public void tryGetHTMLFiles(ActionEvent actionEvent) {
        showDownLoad = false;
        if (getUserManager().isContentUploaded()) {
            setVisible(true);
            showPickButton = false;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can not obtain HTML Files... Please upload a HTML.zip file."));
        }
    }

    public void cleanHTMLContent(ActionEvent actionEvent) { 
        FacesMessage message;
        try {
            String loggerMessage = userManager.getUsername() + " accessed the Clean HTML Content Page. ";
            HtmlContentCleanerService service = new HtmlContentCleanerService(getUserManager().getUsername(), htmlTags.getTarget());
            if(unicodeConvert) {
                service.setUnicodeConvert(unicodeConvert);
                loggerMessage += "Cleaning and performing UniCode Conversion on Content in ";
            }
            int items = service.cleanAndCopyAll();
            loggerMessage += items + " Items.";
            LOGGER.log(Level.INFO, loggerMessage);
            showDownLoad = true;
            visible = false;
            showPickButton = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Item's cleaned of specified HTML...");
        } catch (ZipException ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to Clean Items, as HTML.zip has been cleared, please upload items again...");
        } catch (NullPointerException ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to Clean Items, Please ensure your HTML has correct Intelitest Format with Stem and Options...");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }

    public boolean isShowDownLoad() {
        return showDownLoad;
    }
    
    @Override
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = (UserManager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userManager");
        }
        return userManager;
    }

    public boolean isShowPickButton() {
        return showPickButton;
    }

    public void setShowPickButton(boolean showPickButton) {
        this.showPickButton = showPickButton;
    }
    
    private void populateHTMLTags() {
        List<String> htmlTagsSource = new ArrayList<>();
        List<String> htmlTagsTarget = new ArrayList<>();
        htmlTagsSource.add(BR);
        htmlTagsSource.add(BOLD);
        htmlTagsSource.add(ITALICS);
        htmlTagsSource.add(UNDERLINE);
        htmlTagsSource.add(PARAGRAPH);
        htmlTagsSource.add(FONT);
        htmlTagsSource.add(SPAN);
        htmlTagsSource.add(DIV);
        htmlTagsSource.add(INPUT);
        htmlTagsSource.add(IMG);
        htmlTagsSource.add(SCRIPT);
        htmlTagsSource.add(SPACE);
        htmlTags = new DualListModel<>(htmlTagsSource, htmlTagsTarget);
    }

    public boolean isUnicodeConvert() {
        return unicodeConvert;
    }

    public void setUnicodeConvert(boolean unicodeConvert) {
        this.unicodeConvert = unicodeConvert;
    }
}

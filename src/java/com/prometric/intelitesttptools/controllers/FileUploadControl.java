package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.model.HTMLWriter;
import com.prometric.intelitesttptools.Repository.UserManager;
import com.prometric.intelitesttptools.utils.PropertiesFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
/**
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@ViewScoped
public class FileUploadControl extends IUserManagerClient implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(FileUploadControl.class.getName());
    private static final String HTML_ZIP = "HTML.zip";
    private static final String HTML_EXTENSION = ".htm";
    private static final String SLASH = "/";
    private static final int BACK_SLASH_SHIFT = 1;
    private String destination;
    private UploadedFile file;
    private UserManager userManager;
    private Properties props;

    public FileUploadControl() {
        props = PropertiesFactory.getProperties();
        destination = props.getProperty("TMP_DIR") + getUserManager().getUsername() + SLASH;
        new HTMLWriter().makeDirs(destination);
    }
    
    @Override
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = (UserManager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userManager");
        }
        return userManager;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void handleZipFileUpload(FileUploadEvent event) {
        file = event.getFile();
        uploadZip();
    }
    
    public void handleHTMLFileUpload(FileUploadEvent event) {
        file = event.getFile();
        uploadHTML();
    }

    private void TransferFile(String fileName, InputStream in) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(destination + fileName));
            int reader;
            byte[] bytes = new byte[(int) getFile().getSize()];
            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }
            out.flush();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Problem Reading the File");
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Problem Closing the File");
            }
        }
    }
    
    private void uploadZip() {
        upload(HTML_ZIP, "Only Folders Called HTML.zip can be uploaded!!!");
    }
    
    private void uploadHTML() {
        upload(HTML_EXTENSION, "Only Files that end in .htm can be uploaded!!!");
    }
    
    private void upload(String extension, String errMessage) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (getFile() != null) {
            String fileName = getFile().getFileName();
            if (fileName.contains(SLASH)) {
                File f = new File(fileName);
                fileName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(SLASH) + BACK_SLASH_SHIFT);
            }
            if (fileName.toLowerCase().endsWith(extension.toLowerCase())) {
                try {
                    TransferFile(fileName, getFile().getInputstream());
                } catch (IOException ex) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong", "Error uploading file!!!"));
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", fileName + " uploaded..."));
                getUserManager().setContentUploaded(true);
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "WRONG File Format", errMessage));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong", "Select a file!!!"));
        }
    }
}
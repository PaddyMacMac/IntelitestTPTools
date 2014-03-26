package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.Repository.UserManager;
import com.prometric.intelitesttptools.service.HtmlContentCleanerService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.DefaultStreamedContent;
/**
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@ViewScoped
public class FileDownloadController extends IUserManagerClient implements Serializable {

    private DefaultStreamedContent download;
    private UserManager userManager;

    public FileDownloadController() {
    }

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public DefaultStreamedContent getDownload() {
        return download;
    }

    public void prepFileDownload(String filePath) {
        try {
            File file = new File(filePath);
            InputStream input = new FileInputStream(file);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
            file.delete();
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to download files..."));
        }
    }

    public void prepZipDownload(ActionEvent actionEvent) {
        HtmlContentCleanerService service = new HtmlContentCleanerService(getUserManager().getUsername());
        File file = service.getCleanContentZipFile();

        if (file != null) {
            InputStream input;
            try {
                input = new FileInputStream(file);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
                file.delete();
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to download files..."));
            } 
        }
    }

    @Override
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = (UserManager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userManager");
        }
        return userManager;
    }
}

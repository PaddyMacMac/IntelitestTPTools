package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.Repository.UserManager;
import com.prometric.intelitesttptools.service.InspectItemService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.UploadedFile;
/**
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@ViewScoped
public class InspectItemBean extends IUserManagerClient implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(InspectItemBean.class.getName());
    private static final String INDEX_A = "65";
    private static final String INDEX_B = "66";
    private static final String INDEX_C = "67";
    private static final String INDEX_D = "68";
    private UserManager userManager;
    private InspectItemService service;
    private String stem;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private boolean showDownload;
    private String filePath;

    public InspectItemBean() {
       initialize();
    }

    @Override
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = (UserManager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userManager");
        }
        return userManager;
    }

    public void setHtmlFile(UploadedFile htmlFile) {
        if (getUserManager().isContentUploaded()) {
            showDownload = true;
            service = new InspectItemService(getUserManager().getUsername(), htmlFile);
            filePath = service.getDestination();
            stem = service.getStemHTML();
            optionA = service.getOptionHTML(INDEX_A);
            optionB = service.getOptionHTML(INDEX_B);
            optionC = service.getOptionHTML(INDEX_C);
            optionD = service.getOptionHTML(INDEX_D);
            setVisible(true);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can not obtain HTML File... Please upload a html or htm file."));
        }
        LOGGER.log(Level.INFO, "{0} edited an Item on the Inspect Item Page.", new Object[]{userManager.getUsername()});
    }

    public void stemHTML(ActionEvent actionEvent) {
        stem = service.getStemHTML();
    }

    public void setText(ActionEvent actionEvent) {
        stem = service.getStemText();
    }

    public void saveStem(ActionEvent actionEvent) {
        service.saveStem(stem);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Updated Item text, content saved to file..."));
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void optionAHTML(ActionEvent actionEvent) {
        optionA = service.getOptionHTML(INDEX_A);
    }

    public void optionAText(ActionEvent actionEvent) {
        optionA = service.getOptionText(INDEX_A);
    }

    public void saveOptionA(ActionEvent actionEvent) {
        service.saveOption(INDEX_A, optionA);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Updated Option A, content saved to file..."));
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void optionBHTML(ActionEvent actionEvent) {
        optionB = service.getOptionHTML(INDEX_B);
    }

    public void optionBText(ActionEvent actionEvent) {
        optionA = service.getOptionText(INDEX_B);
    }

    public void saveOptionB(ActionEvent actionEvent) {
        service.saveOption(INDEX_B, optionB);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Updated Option B, content saved to file..."));
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void optionCHTML(ActionEvent actionEvent) {
        optionC = service.getOptionHTML(INDEX_C);
    }

    public void optionCText(ActionEvent actionEvent) {
        optionC = service.getOptionText(INDEX_C);
    }

    public void saveOptionC(ActionEvent actionEvent) {
        service.saveOption(INDEX_C, optionC);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Updated Option C, content saved to file..."));
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void optionDHTML(ActionEvent actionEvent) {
        optionD = service.getOptionHTML(INDEX_D);
    }

    public void optionDText(ActionEvent actionEvent) {
        optionD = service.getOptionText(INDEX_D);
    }

    public void saveOptionD(ActionEvent actionEvent) {
        service.saveOption(INDEX_D, optionD);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Updated Option D, content saved to file..."));
    }  

    public boolean isShowDownload() {
        return showDownload;
    }

    public String getFilePath() {
        return filePath;
    }
}

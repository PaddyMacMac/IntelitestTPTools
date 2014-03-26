package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.model.ItemImage;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.prometric.intelitesttptools.Repository.UserManager;
import com.prometric.intelitesttptools.service.ImageListService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@ViewScoped
public class ImageListBean extends IUserManagerClient implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ImageListBean.class.getName());
    private List<ItemImage> itemImages;
    private int itemsWithImages;
    private UserManager userManager;
    private boolean onlyImages;

    public ImageListBean() {
        initialize();
    }

    public void tryGetItemImages(ActionEvent actionEvent) {
        try {
            ImageListService service = new ImageListService(onlyImages, getUserManager().getUsername());
            itemImages = service.getItemImages();
            itemsWithImages = service.itemsWithImages();
            if (getUserManager().isContentUploaded()) {
                setVisible(true);
            }
        } catch (ZipException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can not obtain Image List... Please upload a HTML.zip file."));
        }
        LOGGER.log(Level.INFO, "{0} created an Images Figure List from: " + itemImages.size() + " items, on the Export Image List Page.", new Object[]{userManager.getUsername()});
    }
    public List<ItemImage> getItemImages() {
        return itemImages;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
    }

    public int getItemsWithImages() {
        return itemsWithImages;
    }

    @Override
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = (UserManager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userManager");
        }
        return userManager;
    }

    public boolean isOnlyImages() {
        return onlyImages;
    }

    public void setOnlyImages(boolean onlyImages) {
        this.onlyImages = onlyImages;
    }
}

package com.prometric.intelitesttptools.model;
/**
 * @author Patrick.MacCnaimhin
 */
public class ItemImage {
    private String masterCode;
    private String images;

    public ItemImage(String masterCode, String images) {
        this.masterCode = masterCode;
        this.images = images;
    }

    public String getMasterCode() {
        return masterCode;
    }
    public void setMasterCode(String masterCode) {
        this.masterCode = masterCode;
    }
    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images;
    }
}

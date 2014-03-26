package com.prometric.intelitesttptools.service;

import com.prometric.intelitesttptools.model.HTMLWriter;
import com.prometric.intelitesttptools.model.ImageReader;
import com.prometric.intelitesttptools.model.ItemImage;
import java.io.File;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
/**
 * @author Patrick.MacCnaimhin
 */
public class ImageListService extends IUserService implements IZipService {
    private static final String SLASH = "/";
    private String zipSrcPath;
    private String zipDestPat;
    private ImageReader imgReader;

    public ImageListService(boolean onlyImages, String userName) throws ZipException {
        imgReader = new ImageReader(onlyImages);
        setCurrentUser(userName);
        initialize();
    }

    private void initialize() throws ZipException {
        String userDir = props.getProperty("TMP_DIR") + getCurrentUser();
        zipSrcPath = userDir + props.getProperty("HTML_ZIP");
        zipDestPat = userDir + SLASH;
        HTMLWriter htmlWriter = new HTMLWriter();
        htmlWriter.makeDirs(zipDestPat);
        processZippedContent();
        unpackHTMLFiles();
    }

    public List<ItemImage> getItemImages() {
        return imgReader.getItemImages();
    }

    public int itemsWithImages() {
        return imgReader.getItemsWithImages();
    }

    @Override
    public void unpackHTMLFiles() {
        imgReader.collectHTMLFiles(new File(zipDestPat + props.getProperty("HTML_FOLDER")));
        imgReader.packItemImages();
    }

    @Override
    public void processZippedContent() throws ZipException {
        imgReader.unzip(zipSrcPath, zipDestPat);
    }
}
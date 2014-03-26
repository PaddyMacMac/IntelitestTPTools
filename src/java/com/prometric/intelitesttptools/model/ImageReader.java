package com.prometric.intelitesttptools.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Patrick Mac Cnaimhin
 */
public class ImageReader extends IReader {
    private static final String IMG_PATTERN = "<img[^>]*src=[\"']([^\"^']*)";
    private static final String CSV = ", ";
    private static final String EMPTY_STRING = "";
    private static final int RS_SPACES = 3;
    private static final int IMG_SPACE = 1;
    private int itemsWithImages;
    private List<ItemImage> itemImages;
    private boolean onlyImages;

    public ImageReader(boolean onlyImages) {
        this.onlyImages = onlyImages;
        htmlFiles = new HashMap<>();
        itemImages = new ArrayList<>();
    }
    
    public List<ItemImage> getItemImages() {
        return itemImages;
    }

    public int getItemsWithImages() {
        return itemsWithImages;
    }

    public void packItemImages() {
        if (!htmlFiles.isEmpty()) {
            for (String item : htmlFiles.keySet()) {
                File nextFile = htmlFiles.get(item);
                Matcher matcher = Pattern.compile(IMG_PATTERN, Pattern.CASE_INSENSITIVE).matcher(getHTMLContent(nextFile));
                String images = EMPTY_STRING;
                while (matcher.find()) {
                    images += matcher.group(IMG_SPACE).substring(RS_SPACES) + CSV;
                }
                if(!images.isEmpty()) {
                    itemsWithImages++;
                }
                if (onlyImages) {
                    if (!images.equals(EMPTY_STRING)) {
                        itemImages.add(new ItemImage(nextFile.getName(), images));
                    }
                }
                else {
                    itemImages.add(new ItemImage(nextFile.getName(), images));
                }
            }
        }
    }
}
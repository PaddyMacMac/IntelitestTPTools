package com.prometric.intelitesttptools.service;

import net.lingala.zip4j.exception.ZipException;
/**
 * @author Patrick.MacCnaimhin
 */
public interface IZipService {
    public void processZippedContent() throws ZipException;
    public void unpackHTMLFiles();
}

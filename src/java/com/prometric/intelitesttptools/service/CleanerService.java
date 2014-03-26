package com.prometric.intelitesttptools.service;

import com.prometric.intelitesttptools.model.HTMLWriter;
import java.io.File;
/**
 * @author Patrick.MacCnaimhin
 */
public class CleanerService extends IUserService {
    public void deleteDir(String path) {
        new HTMLWriter().deleteDir(new File(path));
    }
}

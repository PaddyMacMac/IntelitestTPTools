package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.Repository.UserManager;
/**
 * @author Patrick.MacCnaimhin
 */
public abstract class IUserManagerClient {
    protected boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public abstract UserManager getUserManager();
    
    protected void initialize() {
        getUserManager().setContentUploaded(false);
        try {
            getUserManager().deleteDir();
        }
        catch(IllegalArgumentException ex) {
            //DO NOTHING
        }
    }
}

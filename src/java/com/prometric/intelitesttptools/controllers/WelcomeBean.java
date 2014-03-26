package com.prometric.intelitesttptools.controllers;

import com.prometric.intelitesttptools.Repository.UserManager;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@ViewScoped
public class WelcomeBean extends IUserManagerClient implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(WelcomeBean.class.getName());
    private UserManager userManager;
    
    public WelcomeBean() {
        initialize();
        LOGGER.log(Level.INFO, "{0} accessed the Welcome Page.", new Object[]{userManager.getUsername()});
    }
    
    public void cleanDir(ComponentSystemEvent event) {
        initialize();
    }

     @Override
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = (UserManager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userManager");
        }
        return userManager;
    }
}

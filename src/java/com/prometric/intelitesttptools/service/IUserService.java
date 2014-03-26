package com.prometric.intelitesttptools.service;

import com.prometric.intelitesttptools.utils.PropertiesFactory;
import java.util.Properties;
/**
 * @author Patrick.MacCnaimhin
 */
public abstract class IUserService {
    private  String currentUser;
    protected Properties props;

    public IUserService() {
        this.props = PropertiesFactory.getProperties();
    }
    
    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}

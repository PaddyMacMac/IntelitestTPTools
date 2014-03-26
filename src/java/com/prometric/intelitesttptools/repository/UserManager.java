package com.prometric.intelitesttptools.Repository;

import com.prometric.intelitesttptools.service.CleanerService;
import com.prometric.intelitesttptools.utils.PropertiesFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
/**
 * @author Patrick.MacCnaimhin
 */
@ManagedBean
@SessionScoped
public class UserManager implements Serializable {
    private static final String USER_SESSION_KEY = "user";
    @PersistenceContext(unitName = "IntelitestTPToolsPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private String username;
    private String password;
    private String passwordv;
    private String fname;
    private String lname;
    private boolean contentUploaded;
    private boolean authenticated;

    public void authenticated(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (authenticated == false) {
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("/content/Login.xhtml");
        }
    }

    public void deleteDir() {
        Properties props = PropertiesFactory.getProperties();
        new CleanerService().deleteDir(props.getProperty("TMP_DIR") + username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordv() {
        return passwordv;
    }

    public void setPasswordv(String passwordv) {
        this.passwordv = passwordv;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @PreDestroy
    public void finish() {
            deleteDir();
    }

    public String validateUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        TPUser user = getUser();
        if (user != null) {
            if (!user.getPassword().equals(password)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "The password specified is not correct.");
                context.addMessage(null, message);
                return null;
            }
            context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, user);
            authenticated = true;
            return "app-main";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed!",
                    "Username '"
                    + username
                    + "' does not exist.");
            context.addMessage(null, message);
            return null;
        }
    }

    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        TPUser user = getUser();
        if (user == null) {
            if (!password.equals(passwordv)) {
                FacesMessage message = new FacesMessage("The specified passwords do not match.  Please try again");
                context.addMessage(null, message);
                return null;
            }
            user = new TPUser();
            user.setFirstname(fname);
            user.setLastname(lname);
            user.setPassword(password);
            user.setUsername(username);
            user.setSince(new Date());
            try {
                utx.begin();
                em.persist(user);
                utx.commit();
                return "login";
            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error creating user!",
                        "Unexpected error when creating your account.  Please contact the system Administrator");
                context.addMessage(null, message);
                Logger.getAnonymousLogger().log(Level.SEVERE,
                        "Unable to create new user",
                        e);
                return null;
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Username '"
                    + username
                    + "' already exists!  ",
                    "Please choose a different username.");
            context.addMessage(null, message);
            return null;
        }
    }

    public String logout() {
        authenticated = false;
        contentUploaded = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        try {
            deleteDir();
        } catch (IllegalArgumentException ex) {
            //DO NOTHING
        }
        return "logout";
    }

    private TPUser getUser() {
        try {
            TPUser user = (TPUser) em.createNamedQuery("TPUser.findByUsername").setParameter("username", username).getSingleResult();
            return user;
        } catch (NoResultException nre) {
            return null;
        }
    }

    public boolean isContentUploaded() {
        return contentUploaded;
    }

    public void setContentUploaded(boolean contentUploaded) {
        this.contentUploaded = contentUploaded;
    }
}

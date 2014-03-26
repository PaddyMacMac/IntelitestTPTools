package com.prometric.intelitesttptools.utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 * @author Patrick.MacCnaimhin
 */
public class PropertiesFactory {
    private static final Logger LOGGER = Logger.getLogger(PropertiesFactory.class.getName());
    private static final String APP_PROPERTIES = "intelitesttptool.properties";
    private static volatile Properties PROPERTIES;

    private PropertiesFactory() {
    }

    public static Properties getProperties() {
        if (PROPERTIES == null) {
            try {
                InitialContext context = new InitialContext();
                PROPERTIES = (Properties) context.lookup(APP_PROPERTIES);
                context.close();
            } catch (NamingException e) {
                LOGGER.log(Level.SEVERE, "Naming error occurred while initializing properties from JNDI.", e);
            }
        }
        return PROPERTIES;
    }
}

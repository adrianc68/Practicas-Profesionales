package org.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSSProperties {
    private static final String PROPERTIES_PATH = "css.properties";

    /***
     * This method return a Theme from a properties file
     * <p>
     * This method it is used by a user of system when he change the app's look.
     * </p>
     * @return Theme which is selected by a user
     */
    public static Theme readTheme() {
        Properties properties = readProperties();
        return Theme.valueOf( properties.getProperty("selected.theme") );
    }

    /***
     * This method writes a Theme in a properties file
     * <p>
     * This method it is used by a user of system when he has changed the app's look.
     * The purpose is keep configuration persistent.
     * </p>
     * @return boolean true if theme was written and false if not was written.
     */
    public static boolean writeThemeProperties(Theme theme) {
        boolean result = false;
        try (OutputStream output = new FileOutputStream(PROPERTIES_PATH) ) {
            Properties properties = readProperties();
            properties.setProperty( "selected.theme", theme.name() ) ;
            properties.store(output, null);
            result = true;
        } catch (IOException io) {
            Logger.getLogger( CSSProperties.class.getName() ).log(Level.SEVERE, null, io);
        }
        return result;
    }

    /***
     * This method reads the 'first open' property
     * <p>
     * The purpose of this method its to know if the application it opens by first time or not.
     * </p>
     * @return boolean true if the app it opens by first time and false if was not.
     */
    public static boolean readConfiguredAppProperties() {
        Properties properties = readProperties();
        return Boolean.parseBoolean( properties.getProperty("first.open") );
    }

    /***
     * This method writes the initial configuration of application
     * <p>
     * The purpose of this method is write first open property on false and keep configuration persistent.
     * </p>
     * @return boolean true if properties was written and false if properties was not written.
     */
    public static boolean writeConfiguredAppProperties() {
        boolean result = false;
        Properties properties = readProperties();
        Theme theme = Theme.valueOf( properties.getProperty("selected.theme") );
        try (OutputStream output = new FileOutputStream(PROPERTIES_PATH)) {
            properties.clear();
            properties.put( "selected.theme", theme.name() );
            properties.put("first.open", "false" );
            properties.store(output, null);
            result = true;
        } catch (IOException io) {
            Logger.getLogger( CSSProperties.class.getName() ).log(Level.SEVERE, null, io);
        }
        return result;
    }

    private static Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException io) {
            Logger.getLogger( CSSProperties.class.getName() ).log(Level.SEVERE, null, io);
        }
        return properties;
    }

}
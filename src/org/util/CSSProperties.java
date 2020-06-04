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

    public static Theme readTheme() {
        Properties properties = readProperties();
        return Theme.valueOf( properties.getProperty("selected.theme") );
    }

    public static boolean writeThemeProperties(Theme theme) {
        boolean result = false;
        try (OutputStream output = new FileOutputStream(PROPERTIES_PATH)) {
            Properties properties = readProperties();
            properties.setProperty( "selected.theme", theme.name() ) ;
            properties.store(output, null);
            result = true;
        } catch (IOException io) {
            Logger.getLogger( CSSProperties.class.getName() ).log(Level.SEVERE, null, io);
        }
        return result;
    }

    public static boolean readConfiguredAppProperties() {
        Properties properties = readProperties();
        return Boolean.valueOf( properties.getProperty("first.open") );
    }

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
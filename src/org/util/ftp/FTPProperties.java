package org.util.ftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FTPProperties {
    private String path;

    /***
     * FTPProperties constructor
     * Initializes the path.
     */
    public FTPProperties(String path) {
        this.path = path;
    }

    /***
     * This method reads the FTP properties from a properties file
     * <p>
     * The purpose is get the actual configuration from the properties file
     * </p>
     * @return Map representing the properties
     */
    public Map<String, String> readProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        try ( InputStream input = new FileInputStream(path) ) {
            Properties properties = new Properties();
            properties.load(input);
            propertiesMap.put( "ftp.username", properties.getProperty("ftp.username") );
            propertiesMap.put( "ftp.password", properties.getProperty("ftp.password") );
            propertiesMap.put( "ftp.port", properties.getProperty("ftp.port") );
            propertiesMap.put( "ftp.serverName", properties.getProperty("ftp.serverName") );
        } catch (IOException io) {
            Logger.getLogger( FTPProperties.class.getName() ).log(Level.SEVERE, null, io);
        }
        return propertiesMap;
    }

}

package org.util.mail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailProperties {
    private String path;

    public MailProperties(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> readProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        try (InputStream input = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(input);
            propertiesMap.put( "mail.smtp.auth", properties.getProperty("mail.smtp.auth") );
            propertiesMap.put( "mail.smtp.starttls.enable", properties.getProperty("mail.smtp.starttls.enable") );
            propertiesMap.put( "mail.smtp.host", properties.getProperty("mail.smtp.host") );
            propertiesMap.put( "mail.smtp.port", properties.getProperty("mail.smtp.port") );
            propertiesMap.put( "gen.user", properties.getProperty("gen.user") );
            propertiesMap.put( "gen.password", properties.getProperty("gen.password") );
        } catch (IOException io) {
            Logger.getLogger( MailProperties.class.getName() ).log(Level.SEVERE, null, io);
        }
        return propertiesMap;
    }

}
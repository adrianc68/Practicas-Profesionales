package org.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private String path;

    public DatabaseProperties(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean writeProperties(String user, String password, String url) {
        boolean result = false;
        try (OutputStream output = new FileOutputStream(path)) {
            Properties prop = new Properties();
            prop.setProperty("db.url", url);
            prop.setProperty("db.user", user);
            prop.setProperty("db.password", password);
            prop.store(output, null);
            result = true;
        } catch (IOException io) {
            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.WARNING, null, io);
        }
        return result;
    }

    public Map<String, String> readProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        try (InputStream input = new FileInputStream(path)) {
            Properties prop = new Properties();
            prop.load(input);
            propertiesMap.put("db.user", prop.getProperty("db.user"));
            propertiesMap.put("db.password", prop.getProperty("db.password"));
            propertiesMap.put("db.url", prop.getProperty("db.url"));
        } catch (IOException io) {
            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, io);
        }
        return propertiesMap;
    }

}

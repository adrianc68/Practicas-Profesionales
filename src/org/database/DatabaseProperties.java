package org.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    public void writeProperties(String user, String password, String url) {
        try (OutputStream output = new FileOutputStream("database.properties")) {
            Properties prop = new Properties();
            prop.setProperty("db.url", url);
            prop.setProperty("db.user", user);
            prop.setProperty("db.password", password);
            prop.store(output, null);
        } catch (IOException io) {
            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.WARNING, null, io);
        }
    }

    public String[] readProperties() {
        String user = null;
        String password = null;
        String url = null;
        try (InputStream input = new FileInputStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            url = prop.getProperty("db.url");
        } catch (IOException io) {
            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, io);
        }
        String[] properties = {user, password, url};
        return properties;
    }

}

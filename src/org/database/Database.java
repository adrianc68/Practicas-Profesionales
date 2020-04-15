package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static String user;
    private static String pass;
    private static String url;
    private Connection connection;

    public static void setUser(String user) {
        Database.user = user;
    }

    public static void setPass(String pass) {
        Database.pass = pass;
    }

    public static void setUrl(String url) {
        Database.url = url;
    }

    public Connection getConnection() throws SQLException {
        connectToDatabase();
        return connection;
    }

    public void disconnect() {
        if(connection != null){
            try {
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
            }
            connection = null;
        }
    }

    private void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(url,user,pass);
    }

}

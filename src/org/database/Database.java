package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static String user;
    private static String pass;
    private static String url;
    private Connection connection;

    public Database() {
        DatabaseProperties databaseProperties = new DatabaseProperties();
        Map<String, String> propertiesMap = databaseProperties.readProperties();
        user = propertiesMap.get("db.user");
        pass = propertiesMap.get("db.password");
        url = propertiesMap.get("db.url");
    }

    public Connection getConnection() throws SQLException {
        connectToDatabase();
        return connection;
    }

    public Connection disconnect() {
        if(connection != null){
            try {
                connection.close();
            } catch(SQLException e) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
            }
            connection = null;
        }
        return null;
    }

    private void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(url,user,pass);
    }

}

package org.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static String user = "anonimo";
    private static String pass = "12345";
    private static String url = "jdbc:mysql://localhost:3306/practices?useTimezone=true&serverTimezone=UTC";
    private Connection connection;

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

package org.util.ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FTPConnection {
    private String serverName;
    private String user;
    private String password;
    private int port;

    /***
     * FTPConnection constructor
     * It initializes a map with properties files
     */
    public FTPConnection() {
        FTPProperties ftpProperties = new FTPProperties("ftp.properties");
        Map<String, String> propertiesMap = ftpProperties.readProperties();
        user = propertiesMap.get("ftp.username");
        password = propertiesMap.get("ftp.password");
        port = Integer.parseInt( propertiesMap.get("ftp.port") );
        serverName = propertiesMap.get("ftp.serverName");
    }


    public void connect() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(serverName, port);
            ftpClient.login(user, password);
        } catch (IOException e) {
            Logger.getLogger( FTPConnection.class.getName() ).log(Level.SEVERE, null, e);
        }
    }
}

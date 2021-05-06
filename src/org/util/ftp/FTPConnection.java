package org.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnection {
    private String serverName;
    private String user;
    private String password;
    private int port;
    private String workingDirectory;
    private FTPClient ftpClient;

    /***
     * FTPConnection Constructor
     * It initializes the properties from file.
     */
    public FTPConnection() {
        FTPProperties ftpProperties = new FTPProperties("ftp.properties");
        Map<String, String> propertiesMap = ftpProperties.readProperties();
        user = propertiesMap.get("ftp.username");
        password = propertiesMap.get("ftp.password");
        port = Integer.parseInt( propertiesMap.get("ftp.port") );
        serverName = propertiesMap.get("ftp.serverName");
        workingDirectory = propertiesMap.get("ftp.working.directory");
    }

    /***
     * Upload to server a File by FTP
     * <p>
     * This method should be used when you need to save any kind of files
     * in a server by FTP.
     * </p>
     * @param file File to be saved
     * @param filename The name file to be the ID in FTP server
     * @return boolean true if file was uploaded and false if it was not uploaded.
     */
    public boolean uploadFile(File file, String filename) {
        boolean isFileUploaded = false;
        try {
            if ( connectToServer() ) {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                FileInputStream fis = new FileInputStream(file);
                if ( ftpClient.changeWorkingDirectory(workingDirectory) ){
                    isFileUploaded = ftpClient.storeFile(filename, fis);
                }
                disconnectFromServer();
            }
        } catch (IOException e) {
            Logger.getLogger( FTPConnection.class.getName() ).log(Level.SEVERE, null, e);
        }
        return isFileUploaded;
    }

    /***
     * Download a File from FTP server
     * <p>
     * This method should be used when you need to recover a File saved before
     * in a FTP server
     * </p>
     * @param filename the filename which is saved in FTP server
     * @return File representing the file saved in FTP server
     */
    public File downloadFile(String filename) {
        File file = new File(filename);
        try {
            if ( connectToServer() ) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                if( ftpClient.changeWorkingDirectory(workingDirectory) ) {
                    ftpClient.retrieveFile(filename, fileOutputStream);
                    fileOutputStream.close();
                }
                disconnectFromServer();
            }
        } catch (IOException e) {
            Logger.getLogger( FTPConnection.class.getName() ).log(Level.SEVERE, null, e);
        }
        return file;
    }

    /***
     * Download a File from FTP server
     * <p>
     * This method should be used when you need to delete a file or when you
     * have to replace a old file for a new file.
     * </p>
     * @param filename the filename which is saved in FTP server
     * @return boolean true if file was deleted and false if file was not deleted
     */
    public boolean deleteFile(String filename) {
        boolean isFileDeleted = false;
        try {
            if ( connectToServer() ) {
                if ( ftpClient.changeWorkingDirectory(workingDirectory) ) {
                    isFileDeleted = ftpClient.deleteFile(filename);
                }
            }
        } catch (IOException e) {
            Logger.getLogger( FTPConnection.class.getName() ).log(Level.SEVERE, null, e);
        }
        return isFileDeleted;
    }

    private void disconnectFromServer() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    private boolean connectToServer() throws IOException {
        boolean isConnected;
        ftpClient = new FTPClient();
        int respuesta = 0;
        ftpClient.connect(serverName, port);
        respuesta = ftpClient.getReplyCode();
        if(FTPReply.isPositiveCompletion( respuesta) ) {
            ftpClient.login(user, password);
        }
        isConnected = FTPReply.isPositiveCompletion(respuesta);
        return isConnected;
    }

}

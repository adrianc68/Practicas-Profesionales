package org.util;

import org.gui.auth.resources.alerts.OperationAlert;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkAddress {
    /***
     * This method return the actual MacAddress
     * <p>
     * This method it is used to recognize a PC.
     * </p>
     * @return
     */
    public static String getLocalAdress() {
        String macAddress = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            if( mac != null ) {
                for (int i = 0; i < mac.length; i++) {
                    // T-O Ternary Operator here!
                    stringBuilder.append( String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : "") );
                }
            }
            macAddress = stringBuilder.toString();
        } catch (UnknownHostException | SocketException e) {
            Logger.getLogger( NetworkAddress.class.getName() ).log(Level.SEVERE, null, e);
        }
        return macAddress;
    }

}

package org.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkAddress {
    public static String getLocalAdress(){
        String macAddress = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                // T-O Ternary Operator here!
                stringBuilder.append( String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : "") );
            }
            macAddress = stringBuilder.toString();
        } catch (UnknownHostException e) {
            Logger.getLogger( NetworkAddress.class.getName() ).log(Level.SEVERE, null, e);
        } catch (SocketException e){
            Logger.getLogger( NetworkAddress.class.getName() ) .log(Level.SEVERE, null, e);
        }
        return macAddress;
    }

}

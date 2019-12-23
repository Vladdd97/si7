package faf.labs.si.encryption.domain.util;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class InternetUtils {

    public static ConnectionType checkConnectionType() {
        int count = 0;
        try {
            Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
            while (eni.hasMoreElements()) {
                NetworkInterface nii = eni.nextElement();
                if (nii.isUp()) {
                    //System.out.println("you are connected to:" + nii.getDisplayName());
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count < 5 ? ConnectionType.WIFI : ConnectionType.LAN;
    }
}

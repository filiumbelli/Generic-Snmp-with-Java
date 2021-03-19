package snmp;

import snmp.entities.DeviceHolder;

import java.util.Map;
import java.util.Set;

public class Main {
    private static Set<DeviceHolder> deviceInfo;

    static {
        deviceInfo = snmp.FileManager.getDeviceInfo();
    }

    public static void main(String[] args) {

        for (DeviceHolder d : deviceInfo) {
            System.out.println(d);
            listenPortThread(d.getIpAdress());
        }
    }


    public static void listenPortThread(final String ipAddress, final String port) {
        new Thread(new Runnable() {
            public void run() {
                new snmp.SnmpManager().connectSNMP(ipAddress, port);
            }
        }).start();
    }

    public static void listenPortThread(final String ipAddress) {
        new Thread(new Runnable() {
            public void run() {
                new snmp.SnmpManager().connectSNMP(ipAddress);
            }
        }).start();
    }
}

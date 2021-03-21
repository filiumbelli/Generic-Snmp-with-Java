package snmp.test;

import snmp.entities.DeviceHolder;
import snmp.manager.FileManager;
import snmp.manager.SnmpManager;

import java.util.Set;

public class Main {
    private static Set<DeviceHolder> deviceInfo;

    static {
        deviceInfo = FileManager.getDeviceInfo();
    }

    public static void main(String[] args) {

        for (DeviceHolder d : deviceInfo) {
            System.out.println(d);
            listenPortThread(d.getIpAddress());
        }
    }


    public static void listenPortThread(final String ipAddress, final String port) {
        new Thread(new Runnable() {
            public void run() {
                new SnmpManager().connectSNMP(ipAddress, port);
            }
        }).start();
    }

    public static void listenPortThread(final String ipAddress) {
        new Thread(new Runnable() {
            public void run() {
                new SnmpManager().connectSNMP(ipAddress);
            }
        }).start();
    }
}

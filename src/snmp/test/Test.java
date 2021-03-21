package snmp.test;

import snmp.db.entities.Device;
import snmp.db.entities.Oid;
import snmp.manager.db.DeviceDatabase;
import snmp.manager.db.OidDatabase;

public class Test {

    public static void main(String[] args) {
        DeviceDatabase deviceDatabase = new DeviceDatabase();
        OidDatabase oidDatabase = new OidDatabase();
        Device device1 = new Device("192.168.127.250","1.1.1.1.5.1.61.6.16.1.6.1","Mana");
        Device device2 = new Device("192.166.125.55","1.5151.5151.562.627.2","Mantra");
        Oid oid1 = new Oid("1.5.1.51.5.111.55",device1);
        Oid oid2 = new Oid("15.5.15.1.1..1.51",device1);
        oid1.setDevice(device1);
        oid2.setDevice(device1);
        device1.addOid(oid1);
        device1.addOid(oid2);
        deviceDatabase.add(device1);
        deviceDatabase.add(device2);

     }
}

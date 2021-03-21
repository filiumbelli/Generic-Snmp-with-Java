package snmp.manager.db;

import snmp.db.entities.Device;

import java.util.List;

public interface IDeviceDatabase {
    void add(Device device);
    void delete(Device device);
    List<Device> getDevices();
    Device getDeviceById(int id);
}

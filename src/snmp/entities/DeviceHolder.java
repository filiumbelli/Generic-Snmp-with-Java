package snmp.entities;

public class DeviceHolder {

    private String id;
    private String deviceName;
    private String ipAdress;
    private String lifeCheckOid;

    public DeviceHolder(String id, String deviceName, String ipAdress, String lifeCheckOid) {
        this.id = id;
        this.deviceName = deviceName;
        this.ipAdress = ipAdress;
        this.lifeCheckOid = lifeCheckOid;
    }

    public DeviceHolder() {
    }

    public String getLifeCheckOid() {
        return lifeCheckOid;
    }

    public void setLifeCheckOid(String lifeCheckOid) {
        this.lifeCheckOid = lifeCheckOid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public String toString() {
        return "DeviceHolder{" +
                "id='" + id + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", ipAdress='" + ipAdress + '\'' +
                ", lifeCheckOid='" + lifeCheckOid + '\'' +
                '}';
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }
}

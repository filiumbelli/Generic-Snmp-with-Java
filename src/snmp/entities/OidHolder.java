package snmp.entities;

public class OidHolder {

    private String oidValue;
    private String trap;

    public OidHolder() {
    }

    public OidHolder(String oidValue, String trap) {
        this.oidValue = oidValue;
        this.trap = trap;
    }

    public String getOidValue() {
        return oidValue;
    }

    public void setOidValue(String oidValue) {
        this.oidValue = oidValue;
    }

    public String getTrap() {
        return trap;
    }

    public void setTrap(String trap) {
        this.trap = trap;
    }

}

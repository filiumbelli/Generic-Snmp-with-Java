package snmp.db.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "ip_address")
    public String ipAddress;
    @Column(name = "oid")
    public String lifeCheckOid;
    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    public List<Oid> oids;

    public void addOid(Oid oid) {
        if (this.oids == null) {
            oids = new ArrayList<Oid>();
        }
        this.oids.add(oid);
    }

    public Device() {
    }

    public Device(String ipAddress, String lifeCheckOid, String name) {
        this.ipAddress = ipAddress;
        this.lifeCheckOid = lifeCheckOid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLifeCheckOid() {
        return lifeCheckOid;
    }

    public void setLifeCheckOid(String lifeCheckOid) {
        this.lifeCheckOid = lifeCheckOid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

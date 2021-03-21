package snmp.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "oid")
public class Oid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "value")
    public String value;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "device_id")
    public Device device;

    public Oid() {
    }

    public Oid(String value, Device device) {
        this.value = value;
        this.device = device;
    }

    public Oid(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}


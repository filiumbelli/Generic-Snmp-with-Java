package snmp.manager.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import snmp.db.entities.Device;
import snmp.db.entities.Oid;

import javax.persistence.Query;
import java.util.List;

public class DeviceDatabase implements IDeviceDatabase {
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Device.class)
            .addAnnotatedClass(Oid.class)
            .buildSessionFactory();

    @Override
    public void add(Device device) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(device);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Device device) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(device);
        session.getTransaction().commit();

    }


    @Override
    public List<Device> getDevices() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Device");
        List<Device> devices = q.getResultList();
        session.getTransaction().commit();

        return devices;
    }

    @Override
    public Device getDeviceById(int id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Device where id=:id");
        q.setParameter("id", id);
        Device device = (Device) q.getSingleResult();
        session.getTransaction().commit();
        return device;
    }
}

package snmp.manager.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import snmp.db.entities.Device;
import snmp.db.entities.Oid;

import javax.persistence.Query;
import java.util.List;

public class OidDatabase implements IOidDatabase {
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Device.class)
            .addAnnotatedClass(Oid.class)
            .buildSessionFactory();

    @Override
    public void add(Oid oid) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(oid);
        session.flush();
        session.getTransaction().commit();
    }

    @Override
    public void delete(Oid oid) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(oid);
        session.getTransaction().commit();
    }

    @Override
    public List<Oid> getOids() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Oid");
        List<Oid> oids = q.getResultList();
        session.getTransaction().commit();
        return oids;
    }

    @Override
    public Oid getOidById(int id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Oid where id=:id");
        q.setParameter("id", id);
        Oid oid = (Oid) q.getSingleResult();
        session.getTransaction().commit();
        return oid;
    }


}

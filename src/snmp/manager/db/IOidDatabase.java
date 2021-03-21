package snmp.manager.db;

import snmp.db.entities.Oid;

import java.util.List;

public interface IOidDatabase {
    void add(Oid oid);

    void delete(Oid oid);

    List<Oid> getOids();

    Oid getOidById(int id);
}

package it.unisa;

import java.sql.SQLException;
import java.util.Collection;

public interface OrdineModel {
    public void doSave(OrdineBean ordine) throws SQLException;

    public boolean doDelete(int id, String emailUtente) throws SQLException;

    public OrdineBean doRetrieveByKey(int id, String emailUtente) throws SQLException;

    public Collection<OrdineBean> doRetrieveAll(String order) throws SQLException;
}

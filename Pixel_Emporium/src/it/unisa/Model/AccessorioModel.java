package it.unisa.Model;

import java.sql.SQLException;
import java.util.Collection;

public interface AccessorioModel {
    public void doSave(AccessorioBean accessory) throws SQLException;
    public boolean doDelete(int code) throws SQLException;
    public AccessorioBean doRetrieveByKey(int code) throws SQLException;
    public Collection<AccessorioBean> doRetrieveAll(String order) throws SQLException;
    void updateStock(int code, int decremento) throws SQLException;
    void doUpdate(AccessorioBean bean) throws SQLException;
    Collection<AccessorioBean> doRetrieveByNameOrDescription(String term) throws SQLException;
}

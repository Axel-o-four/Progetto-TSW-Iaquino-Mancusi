package it.unisa;

import java.sql.SQLException;
import java.util.Collection;

public interface GiocoModel {
    public void doSave(GiocoBean gioco) throws SQLException;
    public boolean doDelete(int code) throws SQLException;
    public GiocoBean doRetrieveByKey(int code) throws SQLException;
    public Collection<GiocoBean> doRetrieveAll(String order) throws SQLException;
    void updateStock(int code, int decremento) throws SQLException;
}

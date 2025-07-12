package it.unisa.Model;

import java.sql.SQLException;
import java.util.Collection;

public interface ConsoleModel {
    public void doSave(ConsoleBean console) throws SQLException;
    public boolean doDelete(int code) throws SQLException;
    public ConsoleBean doRetrieveByKey(int code) throws SQLException;
    public Collection<ConsoleBean> doRetrieveAll(String order) throws SQLException;
    void updateStock(int code, int decremento) throws SQLException;
    void doUpdate(ConsoleBean bean) throws SQLException;
    public Collection<ConsoleBean> doRetrieveByNameOrDescription(String term) throws SQLException;
}

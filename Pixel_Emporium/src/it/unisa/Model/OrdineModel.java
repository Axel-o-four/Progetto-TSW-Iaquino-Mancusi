package it.unisa.Model;

import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;

public interface OrdineModel {
    public void doSave(OrdineBean ordine) throws SQLException;

    public boolean doDelete(int id, String emailUtente) throws SQLException;

    public OrdineBean doRetrieveByKey(int id, String emailUtente) throws SQLException;

    public Collection<OrdineBean> doRetrieveAll(String order) throws SQLException;
    
    public Collection<OrdineBean> doRetrieveByUser(String emailUtente) throws SQLException;
    
    public Collection<OrdineBean> doRetrieveByUserAndPeriod(String emailUtente, Date from, Date to) throws SQLException;

    public Collection<OrdineBean> doRetrieveByPeriod(Date from, Date to) throws SQLException;
    
    public OrdineBean doRetrieveByKeyAdmin(int id) throws SQLException;
}

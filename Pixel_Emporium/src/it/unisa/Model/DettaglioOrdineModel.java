package it.unisa.Model;

import java.sql.SQLException;
import java.util.Collection;

public interface DettaglioOrdineModel {
    public Collection<DettaglioOrdineBean> doRetrieveByOrder(int orderId, String emailUtente) throws SQLException;
    void doSave(DettaglioOrdineBean dettaglio) throws SQLException;
    Collection<DettaglioOrdineBean> doRetrieveByOrderAdmin(int orderId) throws SQLException;
}

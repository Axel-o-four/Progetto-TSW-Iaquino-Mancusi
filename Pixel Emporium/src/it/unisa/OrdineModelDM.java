package it.unisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;

public class OrdineModelDM implements OrdineModel {

    private static final String TABLE_NAME = "ORDINE";

    @Override
    public synchronized void doSave(OrdineBean ordine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (EMAIL_UTENTE, DATA_ORDINE, QUANTITA, IMPORTO, IVA, TOTALE_IVA, TOTALE_FATTURA, " +
                "PAESE, CITTA, CAP, PROVINCIA, VIA, NUMERO_CIVICO, TIPO_PAGAMENTO, NUMERO_CARTA, " +
                "SCADENZA_CARTA, CVV, EMAIL_PAYPAL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, ordine.getEmailUtente());
            preparedStatement.setDate(2, ordine.getDataOrdine());
            preparedStatement.setInt(3, ordine.getQuantita());
            preparedStatement.setDouble(4, ordine.getImporto());
            preparedStatement.setDouble(5, ordine.getIva());
            preparedStatement.setDouble(6, ordine.getTotaleIva());
            preparedStatement.setDouble(7, ordine.getTotaleFattura());
            preparedStatement.setString(8, ordine.getPaese());
            preparedStatement.setString(9, ordine.getCitta());
            preparedStatement.setString(10, ordine.getCap());
            preparedStatement.setString(11, ordine.getProvincia());
            preparedStatement.setString(12, ordine.getVia());
            preparedStatement.setString(13, ordine.getNumeroCivico());
            preparedStatement.setString(14, String.valueOf(ordine.getTipoPagamento()));
            preparedStatement.setString(15, ordine.getNumeroCarta());
            preparedStatement.setDate(16, ordine.getScadenzaCarta());
            preparedStatement.setString(17, ordine.getCvv());
            preparedStatement.setString(18, ordine.getEmailPaypal());

            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public synchronized boolean doDelete(int id, String emailUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID = ? AND EMAIL_UTENTE = ?";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, emailUtente);
            result = preparedStatement.executeUpdate();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized OrdineBean doRetrieveByKey(int id, String emailUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        OrdineBean ordine = new OrdineBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ? AND EMAIL_UTENTE = ?";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, emailUtente);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ordine.setId(rs.getInt("ID"));
                ordine.setEmailUtente(rs.getString("EMAIL_UTENTE"));
                ordine.setDataOrdine(rs.getDate("DATA_ORDINE"));
                ordine.setQuantita(rs.getInt("QUANTITA"));
                ordine.setImporto(rs.getDouble("IMPORTO"));
                ordine.setIva(rs.getDouble("IVA"));
                ordine.setTotaleIva(rs.getDouble("TOTALE_IVA"));
                ordine.setTotaleFattura(rs.getDouble("TOTALE_FATTURA"));
                ordine.setPaese(rs.getString("PAESE"));
                ordine.setCitta(rs.getString("CITTA"));
                ordine.setCap(rs.getString("CAP"));
                ordine.setProvincia(rs.getString("PROVINCIA"));
                ordine.setVia(rs.getString("VIA"));
                ordine.setNumeroCivico(rs.getString("NUMERO_CIVICO"));
                String tp = rs.getString("TIPO_PAGAMENTO");
                if (tp != null && !tp.isEmpty()) {
                    ordine.setTipoPagamento(tp.charAt(0));
                }
                ordine.setNumeroCarta(rs.getString("NUMERO_CARTA"));
                ordine.setScadenzaCarta(rs.getDate("SCADENZA_CARTA"));
                ordine.setCvv(rs.getString("CVV"));
                ordine.setEmailPaypal(rs.getString("EMAIL_PAYPAL"));
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return ordine;
    }

    @Override
    public synchronized Collection<OrdineBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrdineBean> ordini = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                OrdineBean ordine = new OrdineBean();
                ordine.setId(rs.getInt("ID"));
                ordine.setEmailUtente(rs.getString("EMAIL_UTENTE"));
                ordine.setDataOrdine(rs.getDate("DATA_ORDINE"));
                ordine.setQuantita(rs.getInt("QUANTITA"));
                ordine.setImporto(rs.getDouble("IMPORTO"));
                ordine.setIva(rs.getDouble("IVA"));
                ordine.setTotaleIva(rs.getDouble("TOTALE_IVA"));
                ordine.setTotaleFattura(rs.getDouble("TOTALE_FATTURA"));
                ordine.setPaese(rs.getString("PAESE"));
                ordine.setCitta(rs.getString("CITTA"));
                ordine.setCap(rs.getString("CAP"));
                ordine.setProvincia(rs.getString("PROVINCIA"));
                ordine.setVia(rs.getString("VIA"));
                ordine.setNumeroCivico(rs.getString("NUMERO_CIVICO"));
                String tp = rs.getString("TIPO_PAGAMENTO");
                if (tp != null && !tp.isEmpty()) {
                    ordine.setTipoPagamento(tp.charAt(0));
                }
                ordine.setNumeroCarta(rs.getString("NUMERO_CARTA"));
                ordine.setScadenzaCarta(rs.getDate("SCADENZA_CARTA"));
                ordine.setCvv(rs.getString("CVV"));
                ordine.setEmailPaypal(rs.getString("EMAIL_PAYPAL"));
                ordini.add(ordine);
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return ordini;
    }
}

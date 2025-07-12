package it.unisa.Model;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class DettaglioOrdineModelDM implements DettaglioOrdineModel {
    
    private static final String TABLE_NAME = "DETTAGLIO_ORDINE";
    
    @Override
    public synchronized Collection<DettaglioOrdineBean> doRetrieveByOrder(int orderId, String emailUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<DettaglioOrdineBean> dettagli = new LinkedList<>();
        
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ORDER_ID = ? AND EMAIL_UTENTE = ?";
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, emailUtente);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DettaglioOrdineBean bean = new DettaglioOrdineBean();
                bean.setOrderId(rs.getInt("ORDER_ID"));
                bean.setEmailUtente(rs.getString("EMAIL_UTENTE"));
                bean.setTipoProdotto(rs.getString("TIPO_PRODOTTO").charAt(0));
                bean.setIdProdotto(rs.getInt("ID_PRODOTTO"));
                bean.setQuantita(rs.getInt("QUANTITA"));
                bean.setPrezzoUnitario(rs.getDouble("PREZZO_UNITARIO"));
                
                switch (bean.getTipoProdotto()) {
                    case 'G': {
                        GiocoModel giocoModel = new GiocoModelDM();
                        GiocoBean gioco = giocoModel.doRetrieveByKey(bean.getIdProdotto());
                        if (gioco != null) {
                            bean.setNome(gioco.getName());
                            bean.setImmagine(gioco.getImage());
                            bean.setDescrizione(gioco.getDescription());
                        }
                        break;
                    }
                    case 'C': {
                        ConsoleModel consoleModel = new ConsoleModelDM();
                        ConsoleBean console = consoleModel.doRetrieveByKey(bean.getIdProdotto());
                        if (console != null) {
                            bean.setNome(console.getName());
                            bean.setImmagine(console.getImage());
                            bean.setDescrizione(console.getDescription());
                        }
                        break;
                    }
                    case 'A': {
                        AccessorioModel accessorioModel = new AccessorioModelDM();
                        AccessorioBean accessorio = accessorioModel.doRetrieveByKey(bean.getIdProdotto());
                        if (accessorio != null) {
                            bean.setNome(accessorio.getName());
                            bean.setImmagine(accessorio.getImage());
                            bean.setDescrizione(accessorio.getDescription());
                        }
                        break;
                    }
                }
                
                dettagli.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return dettagli;
    }
    
    private static final String INSERT_SQL =
    		  "INSERT INTO DETTAGLIO_ORDINE "
    		+ "(ORDER_ID, EMAIL_UTENTE, TIPO_PRODOTTO, ID_PRODOTTO, QUANTITA, PREZZO_UNITARIO) "
    		+ "VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public synchronized void doSave(DettaglioOrdineBean d) throws SQLException {
	    try (Connection con = DriverManagerConnectionPool.getConnection();
		    PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
		    ps.setInt(1, d.getOrderId());
		    ps.setString(2, d.getEmailUtente());
		    ps.setString(3, String.valueOf(d.getTipoProdotto()));
		    ps.setInt(4, d.getIdProdotto());
		    ps.setInt(5, d.getQuantita());
		    ps.setDouble(6, d.getPrezzoUnitario());
		    ps.executeUpdate();
	    }
    }

    @Override
    public synchronized Collection<DettaglioOrdineBean> doRetrieveByOrderAdmin(int orderId)
            throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Collection<DettaglioOrdineBean> dettagli = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME
                         + " WHERE ORDER_ID = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, orderId);

            rs = ps.executeQuery();
            while (rs.next()) {
                DettaglioOrdineBean bean = new DettaglioOrdineBean();
                bean.setOrderId(rs.getInt("ORDER_ID"));
                bean.setEmailUtente(rs.getString("EMAIL_UTENTE"));
                String tipo = rs.getString("TIPO_PRODOTTO");
                if (tipo != null && !tipo.isEmpty()) {
                    bean.setTipoProdotto(tipo.charAt(0));
                }
                bean.setIdProdotto(rs.getInt("ID_PRODOTTO"));
                bean.setQuantita(rs.getInt("QUANTITA"));
                bean.setPrezzoUnitario(rs.getDouble("PREZZO_UNITARIO"));

                switch (bean.getTipoProdotto()) {
                    case 'G':
                        GiocoBean gioco = new GiocoModelDS()
                            .doRetrieveByKey(bean.getIdProdotto());
                        if (gioco != null) {
                            bean.setNome(gioco.getName());
                            bean.setImmagine(gioco.getImage());
                            bean.setDescrizione(gioco.getDescription());
                        }
                        break;
                    case 'C':
                        ConsoleBean console = new ConsoleModelDS()
                            .doRetrieveByKey(bean.getIdProdotto());
                        if (console != null) {
                            bean.setNome(console.getName());
                            bean.setImmagine(console.getImage());
                            bean.setDescrizione(console.getDescription());
                        }
                        break;
                    case 'A':
                        AccessorioBean acc = new AccessorioModelDS()
                            .doRetrieveByKey(bean.getIdProdotto());
                        if (acc != null) {
                            bean.setNome(acc.getName());
                            bean.setImmagine(acc.getImage());
                            bean.setDescrizione(acc.getDescription());
                        }
                        break;
                    default:
                }

                dettagli.add(bean);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) DriverManagerConnectionPool.releaseConnection(connection);
        }
        return dettagli;
    }

}

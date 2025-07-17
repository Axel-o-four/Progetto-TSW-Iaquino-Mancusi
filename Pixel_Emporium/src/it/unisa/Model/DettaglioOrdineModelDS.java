package it.unisa.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DettaglioOrdineModelDS implements DettaglioOrdineModel {

    private static DataSource ds;
    
    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx  = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/pixel_emporium");
        } catch (NamingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static final String TABLE_NAME = "DETTAGLIO_ORDINE";
    
    @Override
    public synchronized Collection<DettaglioOrdineBean> doRetrieveByOrder(int orderId, String emailUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<DettaglioOrdineBean> dettagli = new LinkedList<>();
        
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ORDER_ID = ? AND EMAIL_UTENTE = ?";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, emailUtente);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DettaglioOrdineBean bean = new DettaglioOrdineBean();
                bean.setOrderId(rs.getInt("ORDER_ID"));
                bean.setEmailUtente(rs.getString("EMAIL_UTENTE"));
                String tipoProdottoStr = rs.getString("TIPO_PRODOTTO");
                if (tipoProdottoStr != null && !tipoProdottoStr.isEmpty()) {
                    bean.setTipoProdotto(tipoProdottoStr.charAt(0));
                }
                bean.setIdProdotto(rs.getInt("ID_PRODOTTO"));
                bean.setQuantita(rs.getInt("QUANTITA"));
                bean.setPrezzoUnitario(rs.getDouble("PREZZO_UNITARIO"));
                bean.setNome(rs.getString("NOME"));
                bean.setImmagine(rs.getString("IMMAGINE"));
                bean.setDescrizione(rs.getString("DESCRIZIONE"));
                
                dettagli.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
        return dettagli;
    }
    
    private static final String INSERT_SQL =
    		  "INSERT INTO DETTAGLIO_ORDINE "
    		+ "(ORDER_ID, EMAIL_UTENTE, TIPO_PRODOTTO, ID_PRODOTTO, QUANTITA, PREZZO_UNITARIO, NOME, IMMAGINE, DESCRIZIONE) "
    		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public synchronized void doSave(DettaglioOrdineBean d) throws SQLException {
    	switch (d.getTipoProdotto()) {
	        case 'G':
	            GiocoBean gioco = new GiocoModelDS().doRetrieveByKey(d.getIdProdotto());
	            if (gioco != null) {
	                d.setNome(gioco.getName());
	                d.setImmagine(gioco.getImage());
	                d.setDescrizione(gioco.getDescription());
	            }
	            break;
	        case 'C':
	            ConsoleBean console = new ConsoleModelDS().doRetrieveByKey(d.getIdProdotto());
	            if (console != null) {
	                d.setNome(console.getName());
	                d.setImmagine(console.getImage());
	                d.setDescrizione(console.getDescription());
	            }
	            break;
	        case 'A':
	            AccessorioBean acc = new AccessorioModelDS().doRetrieveByKey(d.getIdProdotto());
	            if (acc != null) {
	                d.setNome(acc.getName());
	                d.setImmagine(acc.getImage());
	                d.setDescrizione(acc.getDescription());
	            }
	            break;
	        default:
	    }
    	try (Connection con = ds.getConnection();
		    PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
		   	ps.setInt(1, d.getOrderId());
		  	ps.setString(2, d.getEmailUtente());
		    ps.setString(3, String.valueOf(d.getTipoProdotto()));
		    ps.setInt(4, d.getIdProdotto());
		    ps.setInt(5, d.getQuantita());
		    ps.setDouble(6, d.getPrezzoUnitario());
		    ps.setString(7, d.getNome());
	        ps.setString(8, d.getImmagine());
	        ps.setString(9, d.getDescrizione());
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
            connection = ds.getConnection();
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
                bean.setNome(rs.getString("NOME"));
                bean.setImmagine(rs.getString("IMMAGINE"));
                bean.setDescrizione(rs.getString("DESCRIZIONE"));

                dettagli.add(bean);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
        return dettagli;
    }

}

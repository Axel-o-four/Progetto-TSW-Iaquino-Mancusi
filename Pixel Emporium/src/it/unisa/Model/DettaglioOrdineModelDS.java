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
                
                switch (bean.getTipoProdotto()) {
                    case 'G': {
                        GiocoModel giocoModel = new GiocoModelDS();
                        GiocoBean gioco = giocoModel.doRetrieveByKey(bean.getIdProdotto());
                        if (gioco != null) {
                            bean.setNome(gioco.getName());
                            bean.setImmagine(gioco.getImage());
                            bean.setDescrizione(gioco.getDescription());
                        }
                        break;
                    }
                    case 'C': {
                        ConsoleModel consoleModel = new ConsoleModelDS();
                        ConsoleBean console = consoleModel.doRetrieveByKey(bean.getIdProdotto());
                        if (console != null) {
                            bean.setNome(console.getName());
                            bean.setImmagine(console.getImage());
                            bean.setDescrizione(console.getDescription());
                        }
                        break;
                    }
                    case 'A': {
                        AccessorioModel accessorioModel = new AccessorioModelDS();
                        AccessorioBean accessorio = accessorioModel.doRetrieveByKey(bean.getIdProdotto());
                        if (accessorio != null) {
                            bean.setNome(accessorio.getName());
                            bean.setImmagine(accessorio.getImage());
                            bean.setDescrizione(accessorio.getDescription());
                        }
                        break;
                    }
                    default:
                        break;
                }
                
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
    		+ "(ORDER_ID, EMAIL_UTENTE, TIPO_PRODOTTO, ID_PRODOTTO, QUANTITA, PREZZO_UNITARIO) "
    		+ "VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public synchronized void doSave(DettaglioOrdineBean d) throws SQLException {
	    try (Connection con = ds.getConnection();
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
            if (connection != null) connection.close();
        }
        return dettagli;
    }

}

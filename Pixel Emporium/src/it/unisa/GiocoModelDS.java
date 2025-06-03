package it.unisa;

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

public class GiocoModelDS implements GiocoModel {

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

    private static final String TABLE_NAME = "gioco";

    @Override
    public synchronized void doSave(GiocoBean gioco) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (PREFISSO_ID, NOME, DESCRIZIONE, IMMAGINE, MARCHIO, PREZZO, ANNO_DI_RILASCIO, GENERE, PEGI, FORMATO, QUANTITA) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, gioco.getPrefissoId());
            preparedStatement.setString(2, gioco.getName());
            preparedStatement.setString(3, gioco.getDescription());
            preparedStatement.setString(4, gioco.getImage());
            preparedStatement.setString(5, gioco.getBrand());
            preparedStatement.setDouble(6, gioco.getPrice());
            preparedStatement.setInt(7, gioco.getReleaseYear());
            preparedStatement.setString(8, gioco.getGenre());
            preparedStatement.setString(9, gioco.getPegi());
            preparedStatement.setString(10, gioco.getFormat());
            preparedStatement.setInt(11, gioco.getQuantity());

            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
    }

    @Override
    public synchronized GiocoBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        GiocoBean gioco = null;
        
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                gioco = new GiocoBean();
                gioco.setCode(rs.getInt("ID"));
                gioco.setPrefissoId(rs.getString("PREFISSO_ID"));
                gioco.setName(rs.getString("NOME"));
                gioco.setDescription(rs.getString("DESCRIZIONE"));
                gioco.setImage(rs.getString("IMMAGINE"));
                gioco.setBrand(rs.getString("MARCHIO"));
                gioco.setPrice(rs.getDouble("PREZZO"));
                gioco.setReleaseYear(rs.getInt("ANNO_DI_RILASCIO"));
                gioco.setGenre(rs.getString("GENERE"));
                gioco.setPegi(rs.getString("PEGI"));
                gioco.setFormat(rs.getString("FORMATO"));
                gioco.setQuantity(rs.getInt("QUANTITA"));
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
        return gioco;
    }

    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
        
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);
            result = preparedStatement.executeUpdate();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized Collection<GiocoBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<GiocoBean> giochi = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }
        
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                GiocoBean gioco = new GiocoBean();
                gioco.setCode(rs.getInt("ID"));
                gioco.setPrefissoId(rs.getString("PREFISSO_ID"));
                gioco.setName(rs.getString("NOME"));
                gioco.setDescription(rs.getString("DESCRIZIONE"));
                gioco.setImage(rs.getString("IMMAGINE"));
                gioco.setBrand(rs.getString("MARCHIO"));
                gioco.setPrice(rs.getDouble("PREZZO"));
                gioco.setReleaseYear(rs.getInt("ANNO_DI_RILASCIO"));
                gioco.setGenre(rs.getString("GENERE"));
                gioco.setPegi(rs.getString("PEGI"));
                gioco.setFormat(rs.getString("FORMATO"));
                gioco.setQuantity(rs.getInt("QUANTITA"));
                
                giochi.add(gioco);
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
        return giochi;
    }
    
    @Override
    public synchronized void updateStock(int code, int decremento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateSQL = "UPDATE " + "GIOCO" + " SET QUANTITA = QUANTITA - ? WHERE ID = ?";
        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, decremento);
            preparedStatement.setInt(2, code);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            if (connection != null) {
                connection.rollback();
            }
            throw ex;
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
    }

}
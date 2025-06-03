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

public class AccessorioModelDS implements AccessorioModel {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx  = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/pixel_emporium");
        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private static final String TABLE_NAME = "accessorio";

    @Override
    public synchronized void doSave(AccessorioBean accessory) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
            " (PREFISSO_ID, NOME, DESCRIZIONE, IMMAGINE, MARCHIO, PREZZO, TIPO_ACCESSORIO, QUANTITA) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, accessory.getPrefissoId());
            preparedStatement.setString(2, accessory.getName());
            preparedStatement.setString(3, accessory.getDescription());
            preparedStatement.setString(4, accessory.getImage());
            preparedStatement.setString(5, accessory.getBrand());
            preparedStatement.setDouble(6, accessory.getPrice());
            preparedStatement.setString(7, accessory.getAccessoryType());
            preparedStatement.setInt(8, accessory.getQuantity());

            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
            }
        }
    }

    @Override
    public synchronized AccessorioBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AccessorioBean accessory = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                accessory = new AccessorioBean();
                accessory.setCode(rs.getInt("ID"));
                accessory.setPrefissoId(rs.getString("PREFISSO_ID"));
                accessory.setName(rs.getString("NOME"));
                accessory.setDescription(rs.getString("DESCRIZIONE"));
                accessory.setImage(rs.getString("IMMAGINE"));
                accessory.setBrand(rs.getString("MARCHIO"));
                accessory.setPrice(rs.getDouble("PREZZO"));
                accessory.setAccessoryType(rs.getString("TIPO_ACCESSORIO"));
                accessory.setQuantity(rs.getInt("QUANTITA"));
            }
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
            }
        }
        return accessory;
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
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized Collection<AccessorioBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<AccessorioBean> accessories = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        if(order != null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }
        
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                AccessorioBean accessory = new AccessorioBean();
                accessory.setCode(rs.getInt("ID"));
                accessory.setPrefissoId(rs.getString("PREFISSO_ID"));
                accessory.setName(rs.getString("NOME"));
                accessory.setDescription(rs.getString("DESCRIZIONE"));
                accessory.setImage(rs.getString("IMMAGINE"));
                accessory.setBrand(rs.getString("MARCHIO"));
                accessory.setPrice(rs.getDouble("PREZZO"));
                accessory.setAccessoryType(rs.getString("TIPO_ACCESSORIO"));
                accessory.setQuantity(rs.getInt("QUANTITA"));
                accessories.add(accessory);
            }
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
            }
        }
        return accessories;
    }
    
    @Override
    public synchronized void updateStock(int code, int decremento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateSQL = "UPDATE " + "ACCESSORIO" + " SET QUANTITA = QUANTITA - ? WHERE ID = ?";
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

package it.unisa.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class AccessorioModelDM implements AccessorioModel {

    private static final String TABLE_NAME = "accessorio";

    @Override
    public synchronized void doSave(AccessorioBean accessory) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
            " (PREFISSO_ID, NOME, DESCRIZIONE, IMMAGINE, MARCHIO, PREZZO, TIPO_ACCESSORIO, QUANTITA) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DriverManagerConnectionPool.getConnection();
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
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
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
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
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
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
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
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);
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
    public synchronized Collection<AccessorioBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<AccessorioBean> accessories = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
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
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return accessories;
    }
    
    @Override
    public synchronized void updateStock(int code, int decremento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateSQL = "UPDATE " + TABLE_NAME + " SET QUANTITA = QUANTITA - ? WHERE ID = ?";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, decremento);
            preparedStatement.setInt(2, code);
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
    public synchronized void doUpdate(AccessorioBean bean) throws SQLException {
        String sql = "UPDATE ACCESSORIO SET "
                   + "NOME=?, DESCRIZIONE=?, IMMAGINE=?, MARCHIO=?, "
                   + "PREZZO=?, TIPO_ACCESSORIO=?, QUANTITA=? "
                   + "WHERE ID = ?";
        try (Connection conn = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getImage());
            ps.setString(4, bean.getBrand());
            ps.setDouble(5, bean.getPrice());
            ps.setString(6, bean.getAccessoryType());
            ps.setInt(7, bean.getQuantity());
            ps.setInt(8, bean.getCode());
            ps.executeUpdate();
            conn.commit();
        }
    }
    
    @Override
    public Collection<AccessorioBean> doRetrieveByNameOrDescription(String term) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME +
                     " WHERE NOME LIKE ? OR DESCRIZIONE LIKE ? " +
                     "ORDER BY NOME ASC LIMIT 10";

        Collection<AccessorioBean> list = new LinkedList<>();

        try (Connection conn = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = term + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AccessorioBean a = new AccessorioBean();
                    a.setCode(rs.getInt("ID"));
                    a.setPrefissoId(rs.getString("PREFISSO_ID"));
                    a.setName(rs.getString("NOME"));
                    a.setDescription(rs.getString("DESCRIZIONE"));
                    a.setImage(rs.getString("IMMAGINE"));
                    a.setBrand(rs.getString("MARCHIO"));
                    a.setPrice(rs.getDouble("PREZZO"));
                    a.setAccessoryType(rs.getString("TIPO_ACCESSORIO"));
                    a.setQuantity(rs.getInt("QUANTITA"));
                    list.add(a);
                }
            }
        }

        return list;
    }
}

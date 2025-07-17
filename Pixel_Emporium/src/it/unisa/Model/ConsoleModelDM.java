package it.unisa.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ConsoleModelDM implements ConsoleModel {

    private static final String TABLE_NAME = "console";

    @Override
    public synchronized void doSave(ConsoleBean console) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (PREFISSO_ID, NOME, DESCRIZIONE, IMMAGINE, MARCHIO, PREZZO, ANNO_DI_RILASCIO, SUPPORTI, RETROCOMPATIBILITA, ARCHIVIAZIONE, GENERAZIONE, QUANTITA) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, console.getPrefissoId());
            preparedStatement.setString(2, console.getName());
            preparedStatement.setString(3, console.getDescription());
            preparedStatement.setString(4, console.getImage());
            preparedStatement.setString(5, console.getBrand());
            preparedStatement.setDouble(6, console.getPrice());
            preparedStatement.setInt(7, console.getReleaseYear());
            preparedStatement.setString(8, console.getSupport());
            preparedStatement.setBoolean(9, console.isRetroCompatibility());
            preparedStatement.setString(10, console.getStorage());
            preparedStatement.setInt(11, console.getGeneration());
            preparedStatement.setInt(12, console.getQuantity());

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
    public synchronized ConsoleBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConsoleBean console = null;
        
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                console = new ConsoleBean();
                console.setCode(rs.getInt("ID"));
                console.setPrefissoId(rs.getString("PREFISSO_ID"));
                console.setName(rs.getString("NOME"));
                console.setDescription(rs.getString("DESCRIZIONE"));
                console.setImage(rs.getString("IMMAGINE"));
                console.setBrand(rs.getString("MARCHIO"));
                console.setPrice(rs.getDouble("PREZZO"));
                console.setReleaseYear(rs.getInt("ANNO_DI_RILASCIO"));
                console.setSupport(rs.getString("SUPPORTI"));
                console.setRetroCompatibility(rs.getBoolean("RETROCOMPATIBILITA"));
                console.setStorage(rs.getString("ARCHIVIAZIONE"));
                console.setGeneration(rs.getInt("GENERAZIONE"));
                console.setQuantity(rs.getInt("QUANTITA"));
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return console;
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
    public synchronized Collection<ConsoleBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ConsoleBean> consoles = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ConsoleBean console = new ConsoleBean();
                console.setCode(rs.getInt("ID"));
                console.setPrefissoId(rs.getString("PREFISSO_ID"));
                console.setName(rs.getString("NOME"));
                console.setDescription(rs.getString("DESCRIZIONE"));
                console.setImage(rs.getString("IMMAGINE"));
                console.setBrand(rs.getString("MARCHIO"));
                console.setPrice(rs.getDouble("PREZZO"));
                console.setReleaseYear(rs.getInt("ANNO_DI_RILASCIO"));
                console.setSupport(rs.getString("SUPPORTI"));
                console.setRetroCompatibility(rs.getBoolean("RETROCOMPATIBILITA"));
                console.setStorage(rs.getString("ARCHIVIAZIONE"));
                console.setGeneration(rs.getInt("GENERAZIONE"));
                console.setQuantity(rs.getInt("QUANTITA"));
                consoles.add(console);
            }
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return consoles;
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
    public synchronized void doUpdate(ConsoleBean bean) throws SQLException {
        String sql = "UPDATE CONSOLE SET "
                   + "NOME=?, DESCRIZIONE=?, IMMAGINE=?, MARCHIO=?, "
                   + "PREZZO=?, ANNO_DI_RILASCIO=?, SUPPORTI=?, "
                   + "RETROCOMPATIBILITA=?, ARCHIVIAZIONE=?, GENERAZIONE=?, "
                   + "QUANTITA=? "
                   + "WHERE ID = ?";
        try (Connection conn = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getImage());
            ps.setString(4, bean.getBrand());
            ps.setDouble(5, bean.getPrice());
            ps.setInt(6, bean.getReleaseYear());
            ps.setString(7, bean.getSupport());
            ps.setBoolean(8, bean.isRetroCompatibility());
            ps.setString(9, bean.getStorage());
            ps.setInt(10, bean.getGeneration());
            ps.setInt(11, bean.getQuantity());
            ps.setInt(12, bean.getCode());
            ps.executeUpdate();
            conn.commit();
        }
    }
    @Override
    public Collection<ConsoleBean> doRetrieveByNameOrDescription(String term) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME +
                     " WHERE NOME LIKE ? OR DESCRIZIONE LIKE ? " +
                     "ORDER BY NOME ASC LIMIT 10";
        Collection<ConsoleBean> list = new LinkedList<>();

        try (Connection conn = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = term + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsoleBean c = new ConsoleBean();
                    c.setCode(rs.getInt("ID"));
                    c.setPrefissoId(rs.getString("PREFISSO_ID"));
                    c.setName(rs.getString("NOME"));
                    c.setDescription(rs.getString("DESCRIZIONE"));
                    c.setImage(rs.getString("IMMAGINE"));
                    c.setBrand(rs.getString("MARCHIO"));
                    c.setPrice(rs.getDouble("PREZZO"));
                    c.setReleaseYear(rs.getInt("ANNO_DI_RILASCIO"));
                    c.setSupport(rs.getString("SUPPORTI"));
                    c.setRetroCompatibility(rs.getBoolean("RETROCOMPATIBILITA"));
                    c.setStorage(rs.getString("ARCHIVIAZIONE"));
                    c.setGeneration(rs.getInt("GENERAZIONE"));
                    c.setQuantity(rs.getInt("QUANTITA"));
                    list.add(c);
                }
            }
        }

        return list;
    }
}

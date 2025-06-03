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

public class ConsoleModelDS implements ConsoleModel {

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
    
    private static final String TABLE_NAME = "console";

    @Override
    public synchronized void doSave(ConsoleBean console) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (PREFISSO_ID, NOME, DESCRIZIONE, IMMAGINE, MARCHIO, PREZZO, ANNO_DI_RILASCIO, SUPPORTI, RETROCOMPATIBILITA, ARCHIVIAZIONE, GENERAZIONE, QUANTITA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ds.getConnection();
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
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
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
            connection = ds.getConnection();
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
                if (connection != null)
                    connection.close();
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
    public synchronized Collection<ConsoleBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ConsoleBean> consoles = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }
        try {
            connection = ds.getConnection();
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
                if (connection != null)
                    connection.close();
            }
        }
        return consoles;
    }
    
    @Override
    public synchronized void updateStock(int code, int decremento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateSQL = "UPDATE " + "CONSOLE" + " SET QUANTITA = QUANTITA - ? WHERE ID = ?";
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

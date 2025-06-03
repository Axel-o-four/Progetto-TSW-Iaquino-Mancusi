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

public class UserModelDS implements UserModel {

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
    
    private static final String TABLE_NAME = "UTENTE";

    @Override
    public synchronized void doSave(UserBean user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (EMAIL, NOME, COGNOME, DATA_NASCITA, GENERE, INDIRIZZO, CITTA, PROV, CAP, PASSWORD) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setDate(4, new java.sql.Date(user.getDataNascita().getTime()));
            preparedStatement.setString(5, user.getGenere());
            preparedStatement.setString(6, user.getIndirizzo());
            preparedStatement.setString(7, user.getCitta());
            preparedStatement.setString(8, user.getProv());
            preparedStatement.setString(9, user.getCap());
            preparedStatement.setString(10, user.getPassword());
            
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
    public synchronized UserBean doRetrieveByKey(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UserBean user = null;
        
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?";
        
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new UserBean();
                user.setEmail(rs.getString("EMAIL"));
                user.setNome(rs.getString("NOME"));
                user.setCognome(rs.getString("COGNOME"));
                user.setDataNascita(rs.getDate("DATA_NASCITA"));
                user.setGenere(rs.getString("GENERE"));
                user.setIndirizzo(rs.getString("INDIRIZZO"));
                user.setCitta(rs.getString("CITTA"));
                user.setProv(rs.getString("PROV"));
                user.setCap(rs.getString("CAP"));
                user.setPassword(rs.getString("PASSWORD"));
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
        return user;
    }

    @Override
    public synchronized boolean doDelete(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE EMAIL = ?";
        
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, email);
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
    public synchronized Collection<UserBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<UserBean> users = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }
        
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                UserBean user = new UserBean();
                user.setEmail(rs.getString("EMAIL"));
                user.setNome(rs.getString("NOME"));
                user.setCognome(rs.getString("COGNOME"));
                user.setDataNascita(rs.getDate("DATA_NASCITA"));
                user.setGenere(rs.getString("GENERE"));
                user.setIndirizzo(rs.getString("INDIRIZZO"));
                user.setCitta(rs.getString("CITTA"));
                user.setProv(rs.getString("PROV"));
                user.setCap(rs.getString("CAP"));
                user.setPassword(rs.getString("PASSWORD"));
                users.add(user);
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
        return users;
    }
}

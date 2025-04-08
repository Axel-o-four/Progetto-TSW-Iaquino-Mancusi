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

public class ProductModelDS implements ProductModel {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/PIXEL_EMPORIUM");
        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private static final String TABLE_NAME = "prodotto";

    @Override
    public synchronized void doSave(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (PREFISSO_ID, ID, NAME, DESCRIPTION, PRICE, QUANTITY) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, product.getPrefissoId());
            preparedStatement.setInt(2, product.getCode());
            preparedStatement.setString(3, product.getName());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.setInt(6, product.getQuantity());
            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public synchronized ProductBean doRetrieveByKey(int code, String prefissoId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProductBean bean = new ProductBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE PREFISSO_ID = ? AND ID = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, prefissoId);
            preparedStatement.setInt(2, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setPrefissoId(rs.getString("PREFISSO_ID"));
                bean.setCode(rs.getInt("ID"));
                bean.setName(rs.getString("NAME"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setPrice(rs.getDouble("PRICE"));
                bean.setQuantity(rs.getInt("QUANTITY"));
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(int code, String prefissoId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE PREFISSO_ID = ? AND ID = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, prefissoId);
            preparedStatement.setInt(2, code);
            result = preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return result != 0;
    }

    @Override
    public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean bean = new ProductBean();
                bean.setPrefissoId(rs.getString("PREFISSO_ID"));
                bean.setCode(rs.getInt("ID"));
                bean.setName(rs.getString("NAME"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setPrice(rs.getDouble("PRICE"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                products.add(bean);
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return products;
    }
}

package it.unisa.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class GiocoModelDM implements GiocoModel {

    private static final String TABLE_NAME = "gioco";

    @Override
    public synchronized void doSave(GiocoBean gioco) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (PREFISSO_ID, NOME, DESCRIZIONE, IMMAGINE, MARCHIO, PREZZO, ANNO_DI_RILASCIO, GENERE, PEGI, FORMATO, QUANTITA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
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
                DriverManagerConnectionPool.releaseConnection(connection);
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
            connection = DriverManagerConnectionPool.getConnection();
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
                DriverManagerConnectionPool.releaseConnection(connection);
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
    public synchronized Collection<GiocoBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<GiocoBean> giochi = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
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
                DriverManagerConnectionPool.releaseConnection(connection);
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
    public synchronized void doUpdate(GiocoBean bean) throws SQLException {
        String sql = "UPDATE GIOCO SET "
                   + "NOME=?, DESCRIZIONE=?, IMMAGINE=?, MARCHIO=?, "
                   + "PREZZO=?, ANNO_DI_RILASCIO=?, GENERE=?, PEGI=?, "
                   + "FORMATO=?, QUANTITA=? "
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
            ps.setString(7, bean.getGenre());
            ps.setString(8, bean.getPegi());
            ps.setString(9, bean.getFormat());
            ps.setInt(10, bean.getQuantity());
            ps.setInt(11, bean.getCode());
            ps.executeUpdate();
            conn.commit();
        }
    }
    
    @Override
    public Collection<GiocoBean> doRetrieveByNameOrDescription(String term) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME +
                     " WHERE NOME LIKE ? OR DESCRIZIONE LIKE ? " +
                     "ORDER BY NOME ASC LIMIT 10";

        Collection<GiocoBean> risultati = new LinkedList<>();

        try (Connection conn = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = term + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GiocoBean g = new GiocoBean();
                    g.setCode(rs.getInt("ID"));
                    g.setPrefissoId(rs.getString("PREFISSO_ID"));
                    g.setName(rs.getString("NOME"));
                    g.setDescription(rs.getString("DESCRIZIONE"));
                    g.setImage(rs.getString("IMMAGINE"));
                    g.setBrand(rs.getString("MARCHIO"));
                    g.setPrice(rs.getDouble("PREZZO"));
                    g.setReleaseYear(rs.getInt("ANNO_DI_RILASCIO"));
                    g.setGenre(rs.getString("GENERE"));
                    g.setPegi(rs.getString("PEGI"));
                    g.setFormat(rs.getString("FORMATO"));
                    g.setQuantity(rs.getInt("QUANTITA"));
                    risultati.add(g);
                }
            }
        }

        return risultati;
    }
}

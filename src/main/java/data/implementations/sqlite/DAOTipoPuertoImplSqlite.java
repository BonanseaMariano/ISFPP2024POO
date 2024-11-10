package data.implementations.sqlite;

import data.interfaces.DAOTipoPuerto;
import database.DBConnection;
import models.TipoPuerto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Implementation of the DAOTipoPuerto interface for SQLite database.
 */
public class DAOTipoPuertoImplSqlite implements DAOTipoPuerto {

    /**
     * Creates a new TipoPuerto record in the database.
     *
     * @param tipoPuerto the TipoPuerto object to create
     */
    @Override
    public void create(TipoPuerto tipoPuerto) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO tipos_puertos (codigo, descripcion, velocidad) ";
            sql += "VALUES(?,?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tipoPuerto.getCodigo());
            pstm.setString(2, tipoPuerto.getDescripcion());
            pstm.setInt(3, tipoPuerto.getVelocidad());
            pstm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Reads all TipoPuerto records from the database.
     *
     * @return a list of TipoPuerto objects
     */
    @Override
    public List<TipoPuerto> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, descripcion, velocidad FROM tipos_puertos ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<TipoPuerto> ret = new ArrayList<>();
            while (rs.next()) {
                ret.add(new TipoPuerto(rs.getString("codigo"), rs.getString("descripcion"), rs.getInt("velocidad")));
            }
            return ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Updates an existing TipoPuerto record in the database.
     *
     * @param t the TipoPuerto object to update
     */
    @Override
    public void update(TipoPuerto t) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE tipos_puertos ";
            sql += "SET descripcion = ?, velocidad = ?";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, t.getDescripcion());
            pstm.setInt(2, t.getVelocidad());
            pstm.setString(3, t.getCodigo());
            pstm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Deletes a TipoPuerto record from the database.
     *
     * @param tipoPuerto the TipoPuerto object to delete
     */
    @Override
    public void delete(TipoPuerto tipoPuerto) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM tipos_puertos WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tipoPuerto.getCodigo());
            pstm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
package data.implementations.sqlite;

import data.interfaces.DAOTipoCable;
import database.DBConnection;
import models.TipoCable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Implementation of the DAOTipoCable interface for SQLite database.
 */
public class DAOTipoCableImplSqlite implements DAOTipoCable {

    /**
     * Creates a new TipoCable record in the database.
     *
     * @param tipoCable the TipoCable object to create
     */
    @Override
    public void create(TipoCable tipoCable) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO tipos_cables (codigo, descripcion, velocidad) ";
            sql += "VALUES(?,?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tipoCable.getCodigo());
            pstm.setString(2, tipoCable.getDescripcion());
            pstm.setInt(3, tipoCable.getVelocidad());
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
     * Reads all TipoCable records from the database.
     *
     * @return a list of TipoCable objects
     */
    @Override
    public List<TipoCable> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, descripcion, velocidad FROM tipos_cables ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<TipoCable> ret = new ArrayList<>();
            while (rs.next()) {
                ret.add(new TipoCable(rs.getString("codigo"), rs.getString("descripcion"), rs.getInt("velocidad")));
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
     * Updates an existing TipoCable record in the database.
     *
     * @param o the existing TipoCable object
     * @param n the new TipoCable object
     */
    @Override
    public void update(TipoCable o, TipoCable n) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE tipos_cables ";
            sql += "SET descripcion = ? ";
            sql += "SET velocidad = ? ";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, n.getDescripcion());
            pstm.setInt(2, n.getVelocidad());
            pstm.setString(3, o.getCodigo());
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
     * Deletes a TipoCable record from the database.
     *
     * @param tipoCable the TipoCable object to delete
     */
    @Override
    public void delete(TipoCable tipoCable) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM tipos_cables WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tipoCable.getCodigo());
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
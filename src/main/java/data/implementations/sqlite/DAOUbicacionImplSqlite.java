package data.implementations.sqlite;

import data.interfaces.DAOUbicacion;
import database.DBConnection;
import models.Ubicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Implementation of the DAOUbicacion interface for SQLite database.
 */
public class DAOUbicacionImplSqlite implements DAOUbicacion {

    /**
     * Creates a new Ubicacion record in the database.
     *
     * @param ubicacion the Ubicacion object to create
     */
    @Override
    public void create(Ubicacion ubicacion) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO ubicaciones (codigo, descripcion) ";
            sql += "VALUES(?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, ubicacion.getCodigo());
            pstm.setString(2, ubicacion.getDescripcion());
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
     * Reads all Ubicacion records from the database.
     *
     * @return a list of Ubicacion objects
     */
    @Override
    public List<Ubicacion> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, descripcion FROM ubicaciones ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<Ubicacion> ret = new ArrayList<>();
            while (rs.next()) {
                ret.add(new Ubicacion(rs.getString("codigo"), rs.getString("descripcion")));
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
     * Updates an existing Ubicacion record in the database.
     *
     * @param t the Ubicacion object to update
     */
    @Override
    public void update(Ubicacion t) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE ubicaciones ";
            sql += "SET descripcion = ? ";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, t.getDescripcion());
            pstm.setString(2, t.getCodigo());
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
     * Deletes a Ubicacion record from the database.
     *
     * @param ubicacion the Ubicacion object to delete
     */
    @Override
    public void delete(Ubicacion ubicacion) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM ubicaciones WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, ubicacion.getCodigo());
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
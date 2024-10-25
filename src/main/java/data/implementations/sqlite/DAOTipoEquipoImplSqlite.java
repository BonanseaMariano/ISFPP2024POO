package data.implementations.sqlite;

import data.interfaces.DAOTipoEquipo;
import database.DBConnection;
import models.TipoEquipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class DAOTipoEquipoImplSqlite implements DAOTipoEquipo {

    @Override
    public void create(TipoEquipo tipoEquipo) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO tipos_equipos (codigo, descripcion) ";
            sql += "VALUES(?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tipoEquipo.getCodigo());
            pstm.setString(2, tipoEquipo.getDescripcion());
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

    @Override
    public List<TipoEquipo> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, descripcion FROM tipos_equipos ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<TipoEquipo> ret = new ArrayList<>();
            while (rs.next()) {
                ret.add(new TipoEquipo(rs.getString("codigo"), rs.getString("descripcion")));
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

    @Override
    public void update(TipoEquipo o, TipoEquipo n) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE tipos_equipos ";
            sql += "SET descripcion = ? ";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, n.getDescripcion());
            pstm.setString(2, o.getCodigo());
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

    @Override
    public void delete(TipoEquipo tipoEquipo) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM tipos_equipos WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tipoEquipo.getCodigo());
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
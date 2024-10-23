package data.implementations.sqlite;

import data.interfaces.DAOTipoPuerto;
import database.DBConnection;
import models.TipoPuerto;
import models.Ubicacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOTipoPuertoImplSqlite implements DAOTipoPuerto {

    @Override
    public void create(TipoPuerto tipoPuerto) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO tipospuerto (codigo, descripcion, velocidad) ";
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

    @Override
    public List<TipoPuerto> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, descripcion, velocidad FROM tipospuertos ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<TipoPuerto> ret = new ArrayList<TipoPuerto>();
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

    @Override
    public void update(TipoPuerto o, TipoPuerto n) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE tipospuerto ";
            sql += "SET descripcion = ?, velocidad = ? ";
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

    @Override
    public void delete(TipoPuerto tipoPuerto) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM tipospuerto WHERE codigo = ? ";
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

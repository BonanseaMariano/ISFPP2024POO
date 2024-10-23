package data.implementations.sqlite;

import data.interfaces.DAOConexion;
import database.DBConnection;
import models.Conexion;
import models.Equipo;
import models.TipoCable;
import models.TipoPuerto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class DAOConexionImplSqlite implements DAOConexion {
    private Hashtable<String, TipoCable> tiposCables;
    private Hashtable<String, Equipo> equipos;
    private Hashtable<String, TipoPuerto> tiposPuertos;

    public DAOConexionImplSqlite() {
        tiposCables = loadTiposCables();
        equipos = loadEquipos();
        tiposPuertos = loadTiposPuertos();
    }

    @Override
    public void create(Conexion conexion) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO conexiones (equipo1, equipo2, tipo_cable, tipo_puerto1, tipo_puerto2) ";
            sql += "VALUES(?,?,?,?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, conexion.getEquipo1().getCodigo());
            pstm.setString(2, conexion.getEquipo2().getCodigo());
            pstm.setString(3, conexion.getTipoCable().getCodigo());
            pstm.setString(4, conexion.getPuerto1().getCodigo());
            pstm.setString(5, conexion.getPuerto2().getCodigo());
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
    public List<Conexion> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT equipo1, equipo2, tipo_cable, tipo_puerto1, tipo_puerto2 FROM conexiones ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<Conexion> ret = new ArrayList<>();
            while (rs.next()) {
                Conexion c = new Conexion();
                c.setEquipo1(equipos.get(rs.getString("equipo1")));
                c.setEquipo2(equipos.get(rs.getString("equipo2")));
                c.setTipoCable(tiposCables.get(rs.getString("tipo_cable")));
                c.setPuerto1(tiposPuertos.get(rs.getString("tipo_puerto1")));
                c.setPuerto2(tiposPuertos.get(rs.getString("tipo_puerto2")));
                ret.add(c);
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
    public void update(Conexion o, Conexion n) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE conexiones ";
            sql += "SET equipo1 = ?, equipo2 = ?, tipo_cable = ?, tipo_puerto1 = ?, tipo_puerto2 = ? ";
            sql += "WHERE equipo1 = ? AND equipo2 = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, n.getEquipo1().getCodigo());
            pstm.setString(2, n.getEquipo2().getCodigo());
            pstm.setString(3, n.getTipoCable().getCodigo());
            pstm.setString(4, n.getPuerto1().getCodigo());
            pstm.setString(5, n.getPuerto2().getCodigo());
            pstm.setString(6, o.getEquipo1().getCodigo());
            pstm.setString(7, o.getEquipo2().getCodigo());
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
    public void delete(Conexion conexion) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM conexiones WHERE equipo1 = ? AND equipo2 = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, conexion.getEquipo1().getCodigo());
            pstm.setString(2, conexion.getEquipo2().getCodigo());
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

    private Hashtable<String, TipoCable> loadTiposCables() {
        Hashtable<String, TipoCable> tiposCables = new Hashtable<>();
        List<TipoCable> list = new DAOTipoCableImplSqlite().read();
        for (TipoCable e : list) {
            tiposCables.put(e.getCodigo(), e);
        }
        return tiposCables;
    }

    private Hashtable<String, Equipo> loadEquipos() {
        Hashtable<String, Equipo> equipos = new Hashtable<>();
        List<Equipo> list = new DAOEquipoImplSqlite().read();
        for (Equipo e : list) {
            equipos.put(e.getCodigo(), e);
        }
        return equipos;
    }

    private Hashtable<String, TipoPuerto> loadTiposPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        List<TipoPuerto> list = new DAOTipoPuertoImplSqlite().read();
        for (TipoPuerto e : list) {
            tiposPuertos.put(e.getCodigo(), e);
        }
        return tiposPuertos;
    }
}

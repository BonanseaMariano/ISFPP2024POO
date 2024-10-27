package data.implementations.sqlite;

import data.interfaces.DAOEquipo;
import data.interfaces.DAOTipoEquipo;
import data.interfaces.DAOTipoPuerto;
import data.interfaces.DAOUbicacion;
import database.DBConnection;
import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class DAOEquipoImplSqlite implements DAOEquipo {
    private Hashtable<String, TipoEquipo> tiposEquipos;
    private Hashtable<String, Ubicacion> ubicaciones;
    private Hashtable<String, TipoPuerto> tiposPuertos;

    public DAOEquipoImplSqlite() {
        tiposEquipos = loadTipoEquipos();
        ubicaciones = loadUbicaciones();
        tiposPuertos = loadTipoPuertos();
    }

    @Override
    public void create(Equipo equipo) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO equipos (codigo,marca,modelo,tipo_equipo,ubicacion,estado) ";
            sql += "VALUES(?,?,?,?,?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.setString(2, equipo.getMarca());
            pstm.setString(3, equipo.getModelo());
            pstm.setString(4, equipo.getTipoEquipo().getCodigo());
            pstm.setString(5, equipo.getUbicacion().getCodigo());
            pstm.setInt(6, equipo.isEstado() ? 1 : 0);
            pstm.executeUpdate();

            for (Puerto puerto : equipo.getPuertos()) {
                sql = "";
                sql += "INSERT INTO puertos (equipo, tipo_puerto, cantidad) ";
                sql += "VALUES(?,?,?) ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, equipo.getCodigo());
                pstm.setString(2, puerto.getTipoPuerto().getCodigo());
                pstm.setInt(3, puerto.getCantidad());
                pstm.executeUpdate();
            }

            for (String direccionIp : equipo.getDireccionesIp()) {
                if (!direccionIp.isBlank()) {
                    sql = "";
                    sql += "INSERT INTO direcciones_ip (equipo, ip) ";
                    sql += "VALUES(?,?) ";
                    pstm = con.prepareStatement(sql);
                    pstm.setString(1, equipo.getCodigo());
                    pstm.setString(2, direccionIp);
                    pstm.executeUpdate();
                }
            }

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
    public List<Equipo> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, marca, modelo, tipo_equipo, ubicacion, estado FROM equipos ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<Equipo> ret = new ArrayList<>();
            while (rs.next()) { //Primer while para cargar los equipos
                Equipo e = new Equipo();
                e.setCodigo(rs.getString("codigo"));
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setTipoEquipo(tiposEquipos.get(rs.getString("tipo_equipo")));
                e.setUbicacion(ubicaciones.get(rs.getString("ubicacion")));
                e.setEstado(rs.getInt("estado") == 1);
                ret.add(e);
            }

            for (Equipo equipo : ret) { //Segundo while para cargar los puertos y direcciones ip a cada equipo
                sql = "SELECT cantidad, tipo_puerto FROM puertos ";
                sql += "WHERE equipo = ? ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, equipo.getCodigo());
                rs = pstm.executeQuery();
                while (rs.next()) {
                    equipo.addPuerto(new Puerto(rs.getInt("cantidad"), tiposPuertos.get(rs.getString("tipo_puerto"))));
                }

                sql = "SELECT ip FROM direcciones_ip ";
                sql += "WHERE equipo = ? ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, equipo.getCodigo());
                rs = pstm.executeQuery();
                while (rs.next()) {
                    equipo.addIP(rs.getString("ip"));
                }
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
    public void update(Equipo o, Equipo n) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE equipos ";
            sql += "SET marca = ?, modelo = ? ,ubicacion = ? ,tipo_equipo = ? ,estado = ? ";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, n.getMarca());
            pstm.setString(2, n.getModelo());
            pstm.setString(3, n.getUbicacion().getCodigo());
            pstm.setString(4, n.getTipoEquipo().getCodigo());
            pstm.setInt(5, n.isEstado() ? 1 : 0);
            pstm.setString(6, o.getCodigo());
            pstm.executeUpdate();

            sql = "UPDATE puertos ";
            sql += "SET tipo_puerto = ?, cantidad = ? ";
            sql += "WHERE equipo = ? ";
            pstm = con.prepareStatement(sql);
            for (Puerto puerto : n.getPuertos()) {
                pstm.setString(1, puerto.getTipoPuerto().getCodigo());
                pstm.setInt(2, puerto.getCantidad());
                pstm.setString(3, o.getCodigo());
                pstm.executeUpdate();
            }

            sql = "UPDATE direcciones_ip ";
            sql += "SET ip = ? ";
            sql += "WHERE equipo = ? ";
            pstm = con.prepareStatement(sql);
            for (String direccionIp : n.getDireccionesIp()) {
                pstm.setString(1, direccionIp);
                pstm.setString(2, o.getCodigo());
                pstm.executeUpdate();
            }

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
    public void delete(Equipo equipo) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM direcciones_ip ";
            sql += "WHERE equipo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.executeUpdate();


            sql = "DELETE FROM puertos ";
            sql += "WHERE equipo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.executeUpdate();


            sql = "DELETE FROM conexiones ";
            sql += "WHERE equipo1 = ? OR equipo2 = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.setString(2, equipo.getCodigo());
            pstm.executeUpdate();


            sql = "DELETE FROM equipos ";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
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

    private Hashtable<String, TipoEquipo> loadTipoEquipos() {
        Hashtable<String, TipoEquipo> tiposEquipos = new Hashtable<>();
        DAOTipoEquipo tipoEquipoDAO = new DAOTipoEquipoImplSqlite();
        List<TipoEquipo> ds = tipoEquipoDAO.read();
        for (TipoEquipo d : ds)
            tiposEquipos.put(d.getCodigo(), d);
        return tiposEquipos;
    }

    private Hashtable<String, Ubicacion> loadUbicaciones() {
        Hashtable<String, Ubicacion> ubicaciones = new Hashtable<>();
        DAOUbicacion ubicacionDAO = new DAOUbicacionImplSqlite();
        List<Ubicacion> ds = ubicacionDAO.read();
        for (Ubicacion d : ds)
            ubicaciones.put(d.getCodigo(), d);
        return ubicaciones;
    }

    private Hashtable<String, TipoPuerto> loadTipoPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        DAOTipoPuerto tipoPuertoDAO = new DAOTipoPuertoImplSqlite();
        List<TipoPuerto> ds = tipoPuertoDAO.read();
        for (TipoPuerto d : ds)
            tiposPuertos.put(d.getCodigo(), d);
        return tiposPuertos;
    }
}
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

/**
 * Implementation of the DAOEquipo interface for SQLite database.
 */
public class DAOEquipoImplSqlite implements DAOEquipo {
    /**
     * Hashtable to store TipoEquipo objects.
     */
    private Hashtable<String, TipoEquipo> tiposEquipos;

    /**
     * Hashtable to store Ubicacion objects.
     */
    private Hashtable<String, Ubicacion> ubicaciones;

    /**
     * Hashtable to store TipoPuerto objects.
     */
    private Hashtable<String, TipoPuerto> tiposPuertos;

    /**
     * Constructor that initializes the Hashtables by loading data from the database.
     */
    public DAOEquipoImplSqlite() {
        tiposEquipos = loadTipoEquipos();
        ubicaciones = loadUbicaciones();
        tiposPuertos = loadTipoPuertos();
    }

    /**
     * Creates a new Equipo record in the database.
     *
     * @param equipo the Equipo object to create
     */
    @Override
    public void create(Equipo equipo) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO equipos (codigo,descripcion,marca,modelo,tipo_equipo,ubicacion,estado) ";
            sql += "VALUES(?,?,?,?,?,?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.setString(2, equipo.getDescripcion());
            pstm.setString(3, equipo.getMarca());
            pstm.setString(4, equipo.getModelo());
            pstm.setString(5, equipo.getTipoEquipo().getCodigo());
            pstm.setString(6, equipo.getUbicacion().getCodigo());
            pstm.setInt(7, equipo.isEstado() ? 1 : 0);
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

    /**
     * Reads all Equipo records from the database.
     *
     * @return a list of Equipo objects
     */
    @Override
    public List<Equipo> read() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT codigo, descripcion, marca, modelo, tipo_equipo, ubicacion, estado FROM equipos ";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            List<Equipo> ret = new ArrayList<>();
            while (rs.next()) { // Primer while para cargar los equipos
                Equipo e = new Equipo();
                e.setCodigo(rs.getString("codigo"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setTipoEquipo(tiposEquipos.get(rs.getString("tipo_equipo")));
                e.setUbicacion(ubicaciones.get(rs.getString("ubicacion")));
                e.setEstado(rs.getInt("estado") == 1);
                ret.add(e);
            }

            for (Equipo equipo : ret) { // Segundo while para cargar los puertos y direcciones ip a cada equipo
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

    /**
     * Updates an existing Equipo record in the database.
     *
     * @param t the Equipo object to update
     */
    @Override
    public void update(Equipo t) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE equipos ";
            sql += "SET descripcion = ?, marca = ?, modelo = ? ,ubicacion = ? ,tipo_equipo = ? ,estado = ? ";
            sql += "WHERE codigo = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, t.getDescripcion());
            pstm.setString(2, t.getMarca());
            pstm.setString(3, t.getModelo());
            pstm.setString(4, t.getUbicacion().getCodigo());
            pstm.setString(5, t.getTipoEquipo().getCodigo());
            pstm.setInt(6, t.isEstado() ? 1 : 0);
            pstm.setString(7, t.getCodigo());
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
     * Deletes an Equipo record from the database.
     *
     * @param equipo the Equipo object to delete
     */
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

    /**
     * Creates a new IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the IP address will be added
     * @param IP     the IP address to be added
     */
    @Override
    public void createIp(Equipo equipo, String IP) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            if (!IP.isBlank()) {
                String sql = "";
                sql += "INSERT INTO direcciones_ip (equipo, ip) ";
                sql += "VALUES(?,?) ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, equipo.getCodigo());
                pstm.setString(2, IP);
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

    /**
     * Deletes an IP address from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the IP address will be deleted
     * @param IP     the IP address to be deleted
     */
    @Override
    public void deleteIp(Equipo equipo, String IP) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM direcciones_ip ";
            sql += "WHERE equipo = ? AND ip = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.setString(2, IP);
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
     * Updates an existing IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity for which the IP address will be updated
     * @param oldIP  the existing IP address to be updated
     * @param newIP  the new IP address with updated values
     */
    @Override
    public void updateIp(Equipo equipo, String oldIP, String newIP) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            if (!newIP.isBlank()) {
                String sql = "";
                sql += "UPDATE direcciones_ip ";
                sql += "SET ip = ? ";
                sql += "WHERE equipo = ? AND ip = ? ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, newIP);
                pstm.setString(2, equipo.getCodigo());
                pstm.setString(3, oldIP);
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

    /**
     * Creates a new port for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the port will be added
     * @param puerto the Puerto entity representing the port to be added
     */
    @Override
    public void createPort(Equipo equipo, Puerto puerto) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "INSERT INTO puertos (equipo, tipo_puerto, cantidad) ";
            sql += "VALUES(?,?,?) ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.setString(2, puerto.getTipoPuerto().getCodigo());
            pstm.setInt(3, puerto.getCantidad());
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
     * Deletes a port from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the port will be deleted
     * @param puerto the Puerto entity representing the port to be deleted
     */
    @Override
    public void deletePort(Equipo equipo, Puerto puerto) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "DELETE FROM puertos ";
            sql += "WHERE equipo = ? AND tipo_puerto = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, equipo.getCodigo());
            pstm.setString(2, puerto.getTipoPuerto().getCodigo());
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
     * Updates an existing port for the specified Equipo.
     *
     * @param equipo  the Equipo entity for which the port will be updated
     * @param oldPort the existing Puerto entity to be updated
     * @param newPort the new Puerto entity with updated values
     */
    @Override
    public void updatePort(Equipo equipo, Puerto oldPort, Puerto newPort) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            String sql = "";
            sql += "UPDATE puertos ";
            sql += "SET cantidad = ?, tipo_puerto = ? ";
            sql += "WHERE equipo = ? AND tipo_puerto = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, newPort.getCantidad());
            pstm.setString(2, newPort.getTipoPuerto().getCodigo());
            pstm.setString(3, equipo.getCodigo());
            pstm.setString(4, oldPort.getTipoPuerto().getCodigo());
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
     * Loads TipoEquipo objects from the database into a Hashtable.
     *
     * @return a Hashtable of TipoEquipo objects
     */
    private Hashtable<String, TipoEquipo> loadTipoEquipos() {
        Hashtable<String, TipoEquipo> tiposEquipos = new Hashtable<>();
        DAOTipoEquipo tipoEquipoDAO = new DAOTipoEquipoImplSqlite();
        List<TipoEquipo> ds = tipoEquipoDAO.read();
        for (TipoEquipo d : ds)
            tiposEquipos.put(d.getCodigo(), d);
        return tiposEquipos;
    }

    /**
     * Loads Ubicacion objects from the database into a Hashtable.
     *
     * @return a Hashtable of Ubicacion objects
     */
    private Hashtable<String, Ubicacion> loadUbicaciones() {
        Hashtable<String, Ubicacion> ubicaciones = new Hashtable<>();
        DAOUbicacion ubicacionDAO = new DAOUbicacionImplSqlite();
        List<Ubicacion> ds = ubicacionDAO.read();
        for (Ubicacion d : ds)
            ubicaciones.put(d.getCodigo(), d);
        return ubicaciones;
    }

    /**
     * Loads TipoPuerto objects from the database into a Hashtable.
     *
     * @return a Hashtable of TipoPuerto objects
     */
    private Hashtable<String, TipoPuerto> loadTipoPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        DAOTipoPuerto tipoPuertoDAO = new DAOTipoPuertoImplSqlite();
        List<TipoPuerto> ds = tipoPuertoDAO.read();
        for (TipoPuerto d : ds)
            tiposPuertos.put(d.getCodigo(), d);
        return tiposPuertos;
    }
}
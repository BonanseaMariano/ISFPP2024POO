package test;

import data.CargarParametrosArchivos;
import data.implementations.*;
import data.interfaces.*;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataTest {
    TreeMap<String, TipoEquipo> tiposEquipos;
    TreeMap<String, TipoPuerto> tiposPuertos;
    TreeMap<String, TipoCable> tiposCables;
    TreeMap<String, Ubicacion> ubicaciones;
    TreeMap<String, Equipo> equipos;
    TreeMap<String, Conexion> conexiones;

    @BeforeEach
    public void setUp() {
        DAOTipoEquipo daoTipoEquipo = new DAOTipoEquipoImplFile();
        DAOTipoPuerto daoTipoPuerto = new DAOTipoPuertoImplFile();
        DAOTipoCable daoTipoCable = new DAOTipoCableImplFile();
        DAOUbicacion daoUbicacion = new DAOUbicacionImplFile();
        DAOEquipo daoEquipo = new DAOEquipoImplFile();
        DAOConexion daoConexion = new DAOConexionImplFile();
        CargarParametrosArchivos.parametros();
        tiposEquipos = daoTipoEquipo.cargarMapa();
        tiposPuertos = daoTipoPuerto.cargarMapa();
        tiposCables = daoTipoCable.cargarMapa();
        ubicaciones = daoUbicacion.cargarMapa();
        equipos = daoEquipo.cargarMapa();
        conexiones = daoConexion.cargarMapa();
    }

    @Test
    public void testUbicacionesFile() {
        assertEquals(6, ubicaciones.size());
        assertTrue(ubicaciones.containsKey("SS"));
        assertTrue(ubicaciones.containsKey("L0"));
        assertTrue(ubicaciones.containsKey("L1"));
        assertTrue(ubicaciones.containsKey("PC"));
        assertTrue(ubicaciones.containsKey("OF1"));
        assertTrue(ubicaciones.containsKey("OF2"));
        System.out.println("\t-- Ubicaciones --");
        for (Ubicacion ubicacion : ubicaciones.values()) {
            System.out.println(ubicacion);
        }
    }

    @Test
    public void testTiposEquiposFile() {
        assertEquals(9, tiposEquipos.size());
        assertTrue(tiposEquipos.containsKey("AP"));
        assertTrue(tiposEquipos.containsKey("CAM"));
        assertTrue(tiposEquipos.containsKey("COM"));
        assertTrue(tiposEquipos.containsKey("IMP"));
        assertTrue(tiposEquipos.containsKey("RJ"));
        assertTrue(tiposEquipos.containsKey("NAS"));
        assertTrue(tiposEquipos.containsKey("NVR"));
        assertTrue(tiposEquipos.containsKey("RT"));
        assertTrue(tiposEquipos.containsKey("SW"));
        System.out.println("\t-- Tipos de Equipos --");
        for (TipoEquipo tipoEquipo : tiposEquipos.values()) {
            System.out.println(tipoEquipo);
        }
    }

    @Test
    public void testTiposPuertosFile() {
        assertEquals(6, tiposPuertos.size());
        assertTrue(tiposPuertos.containsKey("10M"));
        assertTrue(tiposPuertos.containsKey("100M"));
        assertTrue(tiposPuertos.containsKey("100MP"));
        assertTrue(tiposPuertos.containsKey("1G"));
        assertTrue(tiposPuertos.containsKey("4SFP"));
        assertTrue(tiposPuertos.containsKey("10SFP+"));
        System.out.println("\t-- Tipos de Puertos --");
        for (TipoPuerto tipoPuerto : tiposPuertos.values()) {
            System.out.println(tipoPuerto);
        }
    }

    @Test
    public void testTiposCablesFile() {
        assertEquals(4, tiposCables.size());
        assertTrue(tiposCables.containsKey("C5"));
        assertTrue(tiposCables.containsKey("C5e"));
        assertTrue(tiposCables.containsKey("C6"));
        assertTrue(tiposCables.containsKey("FOM"));
        System.out.println("\t-- Tipos de Cables --");
        for (TipoCable tipoCable : tiposCables.values()) {
            System.out.println(tipoCable);
        }
    }

    //TODO: Ver valores del archivo equipos.txt (TIPOS de equipos cargados ahi)
    @Test
    public void testEquiposFile() {
        assertEquals(2, equipos.size());
        assertTrue(equipos.containsKey("SE01"));
        assertTrue(equipos.containsKey("PC01"));
        System.out.println("\t-- Equipos --");
        for (Equipo equipo : equipos.values()) {
            System.out.println(equipo);
        }
    }

    //TODO: Implement testConexionesFile
    @Test
    public void testConexionesFile() {

        System.out.println("\t-- Conexiones --");
        for (Conexion conexion : conexiones.values()) {
            System.out.println(conexion);
        }
    }
}

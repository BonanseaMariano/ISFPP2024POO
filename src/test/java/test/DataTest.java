package test;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.List;

class DataTest {
    List<TipoEquipo> tiposEquipos;
    List<TipoPuerto> tiposPuertos;
    List<TipoCable> tiposCables;
    List<Ubicacion> ubicaciones;
    List<Equipo> equipos;
    List<Conexion> conexiones;

    /**
     * Sets up the test environment by initializing the lists of different types of network components.
     * <p>
     * This method is executed before each test to ensure that the lists are populated with data from the respective services.
     */
    @BeforeEach
    public void setUp() {
        TipoEquipoService tipoEquipoService = new TipoEquipoServiceImpl();
        TipoPuertoService tipoPuertoService = new TipoPuertoServiceImpl();
        TipoCableService tipoCableService = new TipoCableServiceImpl();
        UbicacionService ubicacionService = new UbicacionServiceImpl();
        EquipoService equipoService = new EquipoServiceImpl();
        ConexionService conexionService = new ConexionServiceImpl();

        tiposEquipos = tipoEquipoService.getAll();
        tiposPuertos = tipoPuertoService.getAll();
        tiposCables = tipoCableService.getAll();
        ubicaciones = ubicacionService.getAll();
        equipos = equipoService.getAll();
        conexiones = conexionService.getAll();
    }

    /**
     * Tests the retrieval and printing of all locations (ubicaciones).
     */
    @Test
    void testUbicacionesFile() {
        System.out.println("\t-- Ubicaciones --");
        for (Ubicacion ubicacion : ubicaciones) {
            System.out.println(ubicacion);
        }
    }

    /**
     * Tests the retrieval and printing of all types of devices (tipos de equipos).
     */
    @Test
    void testTiposEquiposFile() {
        System.out.println("\t-- Tipos de Equipos --");
        for (TipoEquipo tipoEquipo : tiposEquipos) {
            System.out.println(tipoEquipo);
        }
    }

    /**
     * Tests the retrieval and printing of all types of ports (tipos de puertos).
     */
    @Test
    void testTiposPuertosFile() {
        System.out.println("\t-- Tipos de Puertos --");
        for (TipoPuerto tipoPuerto : tiposPuertos) {
            System.out.println(tipoPuerto);
        }
    }

    /**
     * Tests the retrieval and printing of all types of cables (tipos de cables).
     */
    @Test
    void testTiposCablesFile() {
        System.out.println("\t-- Tipos de Cables --");
        for (TipoCable tipoCable : tiposCables) {
            System.out.println(tipoCable);
        }
    }

    /**
     * Tests the retrieval and printing of all devices (equipos).
     */
    @Test
    void testEquiposFile() {
        System.out.println("\t-- Equipos --");
        for (Equipo equipo : equipos) {
            System.out.println(equipo);
        }
    }

    /**
     * Tests the retrieval and printing of all connections (conexiones).
     */
    @Test
    void testConexionesFile() {
        System.out.println("\t-- Conexiones --");
        for (Conexion conexion : conexiones) {
            System.out.println(conexion);
        }
    }
}
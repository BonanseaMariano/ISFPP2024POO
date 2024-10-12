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

    @Test
    void testUbicacionesFile() {

        System.out.println("\t-- Ubicaciones --");
        for (Ubicacion ubicacion : ubicaciones) {
            System.out.println(ubicacion);
        }
    }

    @Test
    void testTiposEquiposFile() {
        System.out.println("\t-- Tipos de Equipos --");
        for (TipoEquipo tipoEquipo : tiposEquipos) {
            System.out.println(tipoEquipo);
        }
    }

    @Test
    void testTiposPuertosFile() {
        System.out.println("\t-- Tipos de Puertos --");
        for (TipoPuerto tipoPuerto : tiposPuertos) {
            System.out.println(tipoPuerto);
        }
    }

    @Test
    void testTiposCablesFile() {
        System.out.println("\t-- Tipos de Cables --");
        for (TipoCable tipoCable : tiposCables) {
            System.out.println(tipoCable);
        }
    }


    @Test
    void testEquiposFile() {
        System.out.println("\t-- Equipos --");
        for (Equipo equipo : equipos) {
            System.out.println(equipo);
        }
    }

    @Test
    void testConexionesFile() {
        System.out.println("\t-- Conexiones --");
        for (Conexion conexion : conexiones) {
            System.out.println(conexion);
        }
    }
}

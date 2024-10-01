package test;

import logic.Calculo;
import models.*;
import org.jgrapht.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class CalculoTest {
    private Red red;
    private Equipo equipo1;
    private Equipo equipo2;
    private Equipo switch1;
    private Equipo switch2;
    private Conexion conexion;
    private Conexion conexion2;
    private Conexion conexion3;

    @BeforeEach
    void setUp() {
        Calculo.getInstance();

        // Instantiating 2 types of TipoCable
        TipoCable cable1 = new TipoCable("Eth", "Ethernet", 100);
        TipoCable cable2 = new TipoCable("FO", "Fribra Optica", 10000);

        // Instantiating 2 types of TipoEquipo
        TipoEquipo tipoEquipo1 = new TipoEquipo("PC", "Personal Computer");
        TipoEquipo tipoEquipo2 = new TipoEquipo("SV", "Server");

        // Instantiating 2 types of Ubicacion
        Ubicacion ubicacion1 = new Ubicacion("LAB1", "Laboratorio 1");
        Ubicacion ubicacion2 = new Ubicacion("LAB2", "Laboratorio 2");

        // Instantiating 2 types of TipoPuerto
        TipoPuerto puerto1 = new TipoPuerto("10M", "10 Mbps", 10);
        TipoPuerto puerto2 = new TipoPuerto("4SFP", "4 Gbps Fibra", 4000);

        // Instantiating 2 types of Equipo
        equipo1 = new Equipo("PC1", "Equipo1", "HP", "101", tipoEquipo1, ubicacion1, new Puerto(1, puerto1), List.of("166.82.1.10"));
        equipo2 = new Equipo("PC2", "PC", "HP", "203", tipoEquipo2, ubicacion1, new Puerto(2, puerto2), Arrays.asList("166.82.2.10", "166.82.2.11"));
        switch1 = new Equipo("SW1", "Switch1", "CISCO", "202", tipoEquipo2, ubicacion2, new Puerto(1, puerto2), List.of("167.83.3.10"));
        switch2 = new Equipo("SW2", "Switch2", "Generica", "201", tipoEquipo2, ubicacion2, new Puerto(1, puerto2), List.of("167.84.3.10"));

        // Instantiating 2 types of Conexion
        conexion = new Conexion(cable1, equipo1, switch1);
        conexion2 = new Conexion(cable2, switch1, switch2);
        conexion3 = new Conexion(cable1, switch2, equipo2);


        red = new Red("red");
        red.addEquipo(equipo1);
        red.addEquipo(equipo2);
        red.addEquipo(switch1);
        red.addEquipo(switch2);
        red.addConexion(conexion);
        red.addConexion(conexion2);
        red.addConexion(conexion3);
    }

    @Test
    void cargarGrafo_shouldAddVerticesAndEdges() {
        Calculo.cargarGrafo(red);
        Graph<Equipo, Conexion> grafo = Calculo.getGrafo();

        assertTrue(grafo.containsVertex(equipo1));
        assertTrue(grafo.containsVertex(equipo2));
        assertTrue(grafo.containsVertex(switch1));
        assertTrue(grafo.containsVertex(switch2));
        assertTrue(grafo.containsEdge(conexion));
        assertTrue(grafo.containsEdge(conexion2));
        assertTrue(grafo.containsEdge(conexion3));
        assertEquals(100, conexion.getWeight());
        assertEquals(10000, conexion2.getWeight());
        assertEquals(100, conexion3.getWeight());
    }

    @Test
    void cargarGrafo_shouldHandleEmptyRed() {
        Red emptyRed = new Red("emptyRed");
        Calculo.limpiaGrafo();
        Calculo.cargarGrafo(emptyRed);
        Graph<Equipo, Conexion> grafo = Calculo.getGrafo();

        assertTrue(grafo.vertexSet().isEmpty());
        assertTrue(grafo.edgeSet().isEmpty());
    }

    @Test
    void cargarGrafo_shouldHandleRedWithNoConnections() {
        Red redNoConnections = new Red("redNoConnections");
        Calculo.limpiaGrafo();
        redNoConnections.addEquipo(equipo1);
        redNoConnections.addEquipo(equipo2);
        Calculo.cargarGrafo(redNoConnections);
        Graph<Equipo, Conexion> grafo = Calculo.getGrafo();

        assertTrue(grafo.containsVertex(equipo1));
        assertTrue(grafo.containsVertex(equipo2));
        assertTrue(grafo.edgeSet().isEmpty());
    }
}
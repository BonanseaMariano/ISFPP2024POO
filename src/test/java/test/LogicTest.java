package test;

import controller.Coordinator;
import logic.Logic;
import logic.Red;
import models.Conexion;
import models.Equipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class LogicTest {
    Red red;
    Logic logic;
    Coordinator coordinator;

    @BeforeEach
    public void setUp() {
        /* Se instancian las clases */
        red = Red.getRed();
        logic = new Logic();
        coordinator = new Coordinator();

        /* Se establecen las relaciones entre clases */
        logic.setCoordinator(coordinator);

        /* Se establecen relaciones con la clase coordinador */
        coordinator.setRed(red);
        coordinator.setLogic(logic);
    }

    @Test
    public void testUpdateData() {

        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());


        System.out.println("\t---- Eqipos grafo ----");
        for (Equipo equipo : logic.getGraph().vertexSet()) {
            System.out.println(equipo);
        }

        System.out.println("\t---- Conexiones grafo ----");
        for (Conexion conexion : logic.getGraph().edgeSet()) {
            System.out.println(conexion);
        }
    }

    @Test
    public void testConsulta1() {

        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        System.out.println("\t---- Path ----");
        List<Conexion> path = logic.shortestPath(coordinator.getVertexMap().get("SWGR"), coordinator.getVertexMap().get("FW02"));
        System.out.println(path);
        System.out.println("Max BW :" + coordinator.maxBandwith(path));
    }

    @Test
    public void testConsulta2() {
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());
        System.out.println("\t---- Status Map ----");
        for (Map.Entry<Equipo, Boolean> entry : coordinator.mapStatus().entrySet()) {
            System.out.println(entry.getKey().getCodigo() + " " + entry.getValue());
        }
    }
}

package test;

import controller.Coordinator;
import logic.Logic;
import logic.Red;
import models.Conexion;
import models.Equipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        for (String equipo : coordinator.getVertexMap().keySet()) {
            System.out.println(equipo);
        }
//        System.out.println(logic.shortestPath(coordinator.getVertexMap().get(""), coordinator.getVertexMap().get("AP01")));
    }
}

package test;

import controller.Coordinator;
import exceptions.CicleException;
import exceptions.InvalidConexionException;
import exceptions.LoopException;
import exceptions.NoAvailablePortsException;
import logic.Logic;
import logic.Red;
import models.Conexion;
import models.Equipo;
import models.TipoCable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {
    Red red;
    Logic logic;
    Coordinator coordinator;

    @BeforeEach
    void setUp() {
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

    /**
     * Tests the update of logic data with coordinator data.
     * <p>
     * This test updates the logic data with the coordinator data and verifies that:
     * <ul>
     *   <li>Each device (vertex) in the logic graph is present in the coordinator's vertex map, logic's device map, and Red's device map.</li>
     *   <li>Each connection (edge) in the logic graph is present in the coordinator's connection list, logic's connection map, and Red's connection map.</li>
     * </ul>
     * It also prints the devices and connections in the logic graph.
     */
    @Test
    void testUpdateData() {
        // Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        System.out.println("\t---- Eqipos grafo ----");
        for (Equipo equipo : logic.getGraph().vertexSet()) {
            // Verifica que cada equipo (vertice) de logica este en el mapa de vertices de coordinator
            assertEquals(equipo, coordinator.getVertexMap().get(equipo.getCodigo()));
            // Verifica que cada equipo (vertice) de logica este en el mapa de equipos de logica
            assertEquals(equipo, logic.getEquiposMap().get(equipo.getCodigo()));
            // Verifica que cada equipo (vertice) de logica este en el mapa de equipos de Red
            assertEquals(equipo, red.getEquipos().get(equipo.getCodigo()));
            // Imprime los equipos (vertices) del grafo de logica
            System.out.println(equipo);
        }

        System.out.println("\t---- Conexiones grafo ----");
        for (Conexion conexion : logic.getGraph().edgeSet()) {
            // Verifica que cada conexion (edge) de logica este en la lista de conexiones de coordinator
            assertTrue(coordinator.getConexiones().contains(conexion));
            // Verifica que cada conexion (edge) de logica este en el mapa de conexiones de logica
            assertEquals(conexion, logic.getConexionesMap().get(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo()));
            // Verifica que cada conexion (edge) de logica este en el mapa de conexiones de Red
            assertEquals(conexion, red.getConexiones().get(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo()));
            // Imprime las conexiones (aristas) del grafo de logica
            System.out.println(conexion);
        }
    }

    /**
     * Tests the path finding with maximum bandwidth in the network.
     * <p>
     * This test updates the logic data with the coordinator data, finds a valid path between two devices,
     * prints the path and its maximum bandwidth, and verifies that a non-existent path returns null.
     */
    @Test
    void testPathWithMaxBW() {
        // Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        // Camino valido
        System.out.println("\t---- Path ----");
        List<Conexion> path = coordinator.shortestPath(coordinator.getVertexMap().get("TEST1"), coordinator.getVertexMap().get("TEST4"));
        System.out.println(path);
        System.out.println("Max BW :" + coordinator.maxBandwith(path));

        // Camino inexistente
        assertNull(coordinator.shortestPath(coordinator.getVertexMap().get("TEST1"), coordinator.getVertexMap().get("TEST5")));
    }

    /**
     * Tests the ping functionality and status mapping in the network.
     * <p>
     * This test updates the logic data with the coordinator data, tests individual pings, and group pings.
     * It verifies that valid IP addresses return true and invalid IP addresses return false.
     * It also prints the status map of all devices in the network.
     */
    @Test
    void testPingAndStatusMap() {
        // Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());
        // Se prueban pings individuales
        assertTrue(coordinator.ping("111.111.11.11"));
        assertFalse(coordinator.ping("777.777.77.77"));
        assertFalse(coordinator.ping("333.333.33.33"));
        // Se prueban pings en grupo
        List<String> ips = List.of("111.111.11.11", "222.222.22.22", "444.444.44.44", "555.555.55.55");
        for (Boolean status : coordinator.pingRange(ips).values()) {
            assertTrue(status);
        }

        System.out.println("\t---- Status Map ----");
        for (Map.Entry<Equipo, Boolean> entry : coordinator.mapStatus().entrySet()) {
            System.out.println(entry.getKey().getCodigo() + " " + entry.getValue());
        }
    }

    //TODO: Revisar getConnectedPart
    @Test
    void testGetConnectedPart() {
        // Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());
        // Se obtiene la parte conectada de un vertice (Deveria mostrar TEST1, TEST2, TEST3. Fijarse corriendo GUITest)
        for (Conexion conexion : coordinator.getConnectedPart(coordinator.getVertexMap().get("TEST1"))) {
            System.out.println(conexion);
        }

    }
}

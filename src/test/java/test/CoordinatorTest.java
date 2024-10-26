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
import models.Puerto;
import models.TipoCable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoordinatorTest {
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
        red.setCoordinator(coordinator);

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
     * Tests the addition of a connection to the network.
     * <p>
     * This test updates the logic data with the coordinator data, creates a new valid connection,
     * and verifies that the connection is added to the logic graph, logic connections map, and Red connections map.
     * It also verifies that the same connection cannot be added again, and checks for various exceptions
     * such as loops, cycles, and unavailable ports.
     */
    @Test
    void testAddConnection() {
        //Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        //Se crea una nueva conexion valida
        Equipo e1 = coordinator.getVertexMap().get("TEST1");
        Equipo e2 = coordinator.getVertexMap().get("TEST5");
        TipoCable cable = coordinator.getTiposCables().get("C5");
        Conexion c = new Conexion(cable, e1, e1.getPuertos().get(0).getTipoPuerto(), e2, e2.getPuertos().get(0).getTipoPuerto());

        //Se agrega la conexion a través del coordinador
        coordinator.addConnection(c);
        //Se verifica que la conexion se haya agregado al grafo de logica
        assertTrue(logic.getGraph().containsEdge(c));
        //Se verifica que la conexion se haya agregado al mapa de conexiones de logica
        assertEquals(c, logic.getConexionesMap().get(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));
        //Se verifica que la conexion se haya agregado al mapa de conexiones de Red
        assertEquals(c, red.getConexiones().get(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));

        //Se verifica que no se agregue denuevo la misma conexion en logica
        assertThrows(InvalidConexionException.class, () -> logic.addEdge(c));
        //Se borra la conexion a través del coordinador
        coordinator.deleteConnection(c);

        //Se verifica que no se agregue la conexion si forma un loop en logica
        c.setEquipo2(e1);
        assertThrows(LoopException.class, () -> logic.addEdge(c));


        //Se verifica que no se agregue la conexion si forma un ciclo en logica
        e1 = coordinator.getVertexMap().get("TEST1");
        e2 = coordinator.getVertexMap().get("TEST4");
        c.setEquipo1(e1);
        c.setEquipo2(e2);
        assertThrows(CicleException.class, () -> logic.addEdge(c));
        //Se verifica que no se agregue la conexion si forma un ciclo en Coordinator
        coordinator.addConnection(c);
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMap().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));

        //Se verifica que no se agregue la conexion si no hay puertos disponibles en logica
        e1 = coordinator.getVertexMap().get("TEST2");
        e2 = coordinator.getVertexMap().get("TEST6"); //TEST6 tiene 0 cantidad de puertos en el archivo
        c.setEquipo1(e1);
        c.setEquipo2(e2);
        assertThrows(NoAvailablePortsException.class, () -> logic.addEdge(c));
        //Se verifica que no se agregue la conexion si no hay puertos disponibles en Coordinator
        coordinator.addConnection(c);
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMap().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));
    }

    /**
     * Tests the modification of an existing connection in the network.
     * <p>
     * This test updates the logic data with the coordinator data, modifies an existing valid connection,
     * and verifies that the connection is updated in the logic graph, logic connections map, and Red connections map.
     * It also verifies that the old connection no longer exists.
     * Finally, it restores the original connection.
     */
    @Test
    void testModifyConnection() {
        // Update logic data with coordinator data
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        // Modify an existing valid connection
        Conexion vieja = coordinator.getEdgesMap().get("TEST3-TEST4");
        Conexion nueva = new Conexion(coordinator.getTiposCables().get("C5"), vieja.getEquipo1(), vieja.getPuerto1(), coordinator.getVertexMap().get("TEST5"), coordinator.getVertexMap().get("TEST5").getPuertos().getFirst().getTipoPuerto());

        coordinator.modifyConnection(vieja, nueva);
        // Verify that the connection has been modified
        assertTrue(coordinator.getEdgesMap().containsKey(nueva.getEquipo1().getCodigo() + "-" + nueva.getEquipo2().getCodigo()));
        assertTrue(coordinator.getConexiones().contains(nueva));
        // Verify that the old connection no longer exists
        assertFalse(coordinator.getEdgesMap().containsKey(vieja.getEquipo1().getCodigo() + "-" + vieja.getEquipo2().getCodigo()));
        assertFalse(coordinator.getConexiones().contains(vieja));

        // Revert the changes
        coordinator.modifyConnection(nueva, vieja);
    }

    /**
     * Tests the deletion of a connection from the network.
     * <p>
     * This test updates the logic data with the coordinator data, deletes an existing valid connection,
     * and verifies that the connection is removed from the logic graph, logic connections map, and Red connections map.
     * It also verifies that an attempt to delete a non-existent connection throws an InvalidConexionException.
     * Finally, it restores the deleted connection.
     */
    @Test
    void testDeleteConnection() {
        // Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        // Se elimina una conexion existente de manera valida
        Conexion c = coordinator.getEdgesMap().get("TEST1-TEST2");

        // Se borra la conexion a través del coordinador
        coordinator.deleteConnection(c);
        // Se verifica que la conexion se haya eliminado del grafo de logica
        assertFalse(coordinator.getLogic().getGraph().containsEdge(c));
        // Se verifica que la conexion se haya eliminado del mapa de conexiones de logica
        assertFalse(coordinator.getEdgesMap().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));
        // Se verifica que la conexion se haya eliminado del mapa de conexiones de Red
        assertFalse(coordinator.getConexiones().contains(c));

        // Se verifica que no se elimine una conexion inexistente
        assertThrows(InvalidConexionException.class, () -> logic.deleteEdge(c));
        assertThrows(InvalidConexionException.class, () -> red.deleteConexion(c));

        // Se restaura la conexion
        coordinator.addConnection(c);
    }

    //TODO: Revisar addEquipo (Tiene algun problema el DAO con la ubicacion)
    @Test
    void testAddDevice() {
        //Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        //Se crea un nuevo equipo valido
        Equipo e = new Equipo("TEST7", "Test7", "Test7", "Test7", coordinator.getTiposEquipos().get("AP"), coordinator.getUbicaciones().get("RL3"), new Puerto(1,coordinator.getTiposPuertos().get("100M")), "777.777.77.77", true);

        //Se agrega el equipo a través del coordinador
        coordinator.addEquipo(e);
        //Se verifica que el equipo se haya agregado al grafo de logica
        assertTrue(coordinator.getLogic().getGraph().containsVertex(e));
        //Se verifica que el equipo se haya agregado al mapa de equipos de logica
        assertEquals(e, coordinator.getVertexMap().get(e.getCodigo()));
        //Se verifica que el equipo se haya agregado al mapa de equipos de Red
        assertTrue(coordinator.getRed().getEquipos().containsKey(e.getCodigo()));

        //Se verifica que no se agregue denuevo el mismo equipo en logica
        assertThrows(IllegalArgumentException.class, () -> logic.addVertex(e));
        //Se verifica que no se agregue denuevo el mismo equipo en Red
        assertThrows(IllegalArgumentException.class, () -> red.addEquipo(e));

        //Se borra el equipo a través del coordinador
        coordinator.deleteEquipo(e);
    }

    //TODO: Revisar modify
//    @Test
//    void testModifyDevice() {
//
//    }

    @Test
    void testDeleteDevice() {
        //Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

    }
}

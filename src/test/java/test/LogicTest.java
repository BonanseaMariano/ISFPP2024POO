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
        Equipo e1 = coordinator.getVertexMap().get("AP07");
        Equipo e2 = coordinator.getVertexMap().get("AP03");
        TipoCable cable = coordinator.getRed().getTiposCables().get("C5");
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
        e1 = coordinator.getVertexMap().get("GWUSG");
        e2 = coordinator.getVertexMap().get("SW04");
        c.setEquipo1(e1);
        c.setEquipo2(e2);
        assertThrows(CicleException.class, () -> logic.addEdge(c));
        //Se verifica que no se agregue la conexion si forma un ciclo en Coordinator
        coordinator.addConnection(c);
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMao().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));

        //Se verifica que no se agregue la conexion si no hay puertos disponibles en logica
        e1 = coordinator.getVertexMap().get("AP03");
        e2 = coordinator.getVertexMap().get("SWAP"); //SWAP tiene 0 cantidad de puertos en el archivo
        c.setEquipo1(e1);
        c.setEquipo2(e2);
        assertThrows(NoAvailablePortsException.class, () -> logic.addEdge(c));
        //Se verifica que no se agregue la conexion si no hay puertos disponibles en Coordinator
        coordinator.addConnection(c);
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMao().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));
    }

    @Test
    void testModifyConnection() {
        //Se actualizan los datos de logica con los datos de coordinator
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        //Se crea una nueva conexion valida
        Equipo e1 = coordinator.getVertexMap().get("AP07");
        Equipo e2 = coordinator.getVertexMap().get("AP03");
        TipoCable cable = coordinator.getRed().getTiposCables().get("C5");
        Conexion c = new Conexion(cable, e1, e1.getPuertos().get(0).getTipoPuerto(), e2, e2.getPuertos().get(0).getTipoPuerto());

        //Se agrega la conexion a través del coordinador
        coordinator.addConnection(c);
        //Se verifica que la conexion se haya agregado al grafo de logica
        assertTrue(logic.getGraph().containsEdge(c));
        //Se verifica que la conexion se haya agregado al mapa de conexiones de logica
        assertEquals(c, logic.getConexionesMap().get(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));
        //Se verifica que la conexion se haya agregado al mapa de conexiones de Red
        assertEquals(c, red.getConexiones().get(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));

        //Se intenta modificar la conexion para que forme un loop
        c.setEquipo2(e1);
        coordinator.modifyConnection(c);
        //Se verifica que la conexion no se haya modificado
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMao().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));

        //Se borra la conexion a través del coordinador
        coordinator.deleteConnection(c);

        //Se verifica que no se agregue la conexion si forma un loop en logica
        c.setEquipo2(e1);
        assertThrows(LoopException.class, () -> logic.addEdge(c));


        //Se verifica que no se agregue la conexion si forma un ciclo en logica
        e1 = coordinator.getVertexMap().get("GWUSG");
        e2 = coordinator.getVertexMap().get("SW04");
        c.setEquipo1(e1);
        c.setEquipo2(e2);
        assertThrows(CicleException.class, () -> logic.addEdge(c));
        //Se verifica que no se agregue la conexion si forma un ciclo en Coordinator
        coordinator.addConnection(c);
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMao().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));

        //Se verifica que no se agregue la conexion si no hay puertos disponibles en logica
        e1 = coordinator.getVertexMap().get("AP03");
        e2 = coordinator.getVertexMap().get("SWAP"); //SWAP tiene 0 cantidad de puertos en el archivo
        c.setEquipo1(e1);
        c.setEquipo2(e2);
        assertThrows(NoAvailablePortsException.class, () -> logic.addEdge(c));
        //Se verifica que no se agregue la conexion si no hay puertos disponibles en Coordinator
        coordinator.addConnection(c);
        assertFalse(coordinator.getConexiones().contains(c));
        assertFalse(coordinator.getEdgesMao().containsKey(c.getEquipo1().getCodigo() + "-" + c.getEquipo2().getCodigo()));
    }

    @Test
    void testDeleteConnection() {

    }

    @Test
    void testAddDevice() {

    }

    @Test
    void testModifyDevice() {

    }

    @Test
    void testDeleteDevice() {

    }


    @Test
    void testPathWithMaxBW() {
        //Se actualizan los datos de logica con los datos de coordinator
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

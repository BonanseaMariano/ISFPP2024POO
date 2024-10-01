package logic;

import models.Conexion;
import models.Equipo;
import models.Red;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Calculo {
    private static Calculo instance;
    private static Graph<Equipo, Conexion> grafo;

    private Calculo() {
        this.grafo = new SimpleWeightedGraph<>(Conexion.class);
    }

    public static Calculo getInstance() {
        if (instance == null) {
            instance = new Calculo();
        }
        return instance;
    }

    public static Graph<Equipo, Conexion> getGrafo() {
        return grafo;
    }

    public static void cargarGrafo(Red red) {
        // Add vertices
        for (Equipo equipo : red.getEquipos().values()) {
            grafo.addVertex(equipo);
        }

        // Add edges
        for (Conexion conexion : red.getConexiones()) {
            grafo.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
            grafo.setEdgeWeight(conexion, conexion.getWeight());
        }
    }

    public static void limpiaGrafo() {
        grafo = new SimpleWeightedGraph<>(Conexion.class);
    }

    //TODO: 3.1 Dado dos equipos mostrar todos los equipos intermedios y sus conexiones. Calcular la velocidad máxima de acuerdo al tipo de puerto y cables por donde se transmiten los datos.
    //TODO: 3.2 Realizar un ping a un equipo. Realizar un ping a un rango de IP. Realizar un mapa del estado actual de los equipos conectados a la red.
    //TODO: 3.3 Detectar problemas de conectividad, por ejemplo un usuario de una computadora no puede navegar en Internet. Detectar hasta que parte de la red puede acceder y donde pierde la conectividad.

}

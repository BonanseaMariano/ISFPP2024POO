package gui;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import controller.Coordinator;
import models.Conexion;
import models.Equipo;
import org.jgrapht.Graph;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * ConnectivityProblemsDialog is a custom dialog for visualizing connectivity problems in a network.
 * It uses a graph representation to display the connections between devices (Equipos).
 * The dialog includes various styles and properties for the graph visualization.
 */
public class ConnectivityProblemsDialog extends javax.swing.JDialog {

    /**
     * Style for the vertices in the graph.
     * The style includes font color, stroke color, and fill color.
     */
    private static final String VERTEX_STYLE = "fontColor=white;strokeColor=black;fillColor=";
    /**
     * Style for the edges in the graph.
     * The style includes the end arrow and stroke color.
     */
    private static final String EDGE_STYLE = "endArrow=none;strokeColor=";
    /**
     * Width of the vertex
     */
    private static final int VERTEX_WIDTH = 80;
    /**
     * Height of the vertex
     */
    private static final int VERTEX_HEIGHT = 30;
    /**
     * Panel for displaying the graph representation of connectivity problems.
     */
    private javax.swing.JPanel graphJP;
    /**
     * Instance of the mxGraph used for visualizing the graph.
     */
    private com.mxgraph.view.mxGraph mxGraph;
    /**
     * Map to store the association between Equipo objects and their corresponding vertices in the graph.
     */
    private Map<Equipo, Object> vertexMap;
    /**
     * Coordinator instance
     */
    private final Coordinator coordinator;
    /**
     * Resource bundle for internationalization
     */
    private final ResourceBundle rb;
    /**
     * Width of the dialog.
     */
    private static final int WIDTH_DIALOG = 800;
    /**
     * Height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 900;

    /**
     * Constructs a new ConnectivityProblemsDialog.
     *
     * @param parent      The parent frame of the dialog.
     * @param modal       Indicates whether the dialog should be modal.
     * @param coordinator The Coordinator instance used to manage connectivity data.
     * @param equipo      The name of the team (Equipo) to visualize.
     */
    public ConnectivityProblemsDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator, String equipo) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        initComponents(equipo);
        initMxGraphStyle();
        visualizeGraph(equipo);
        initStyles();
    }

    /**
     * Initializes the styles and properties of the dialog.
     * Sets the minimum size, location, visibility, and default close operation.
     */
    private void initStyles() {
        this.setMinimumSize(new Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components of the dialog.
     *
     * @param equipo The name of the team (Equipo) to visualize.
     */
    private void initComponents(String equipo) {
        // Main panel that contains all components of the dialog
        javax.swing.JPanel parentJP = new javax.swing.JPanel();

        // Panel for displaying the graph visualization
        graphJP = new javax.swing.JPanel();

        // Panel for the title of the dialog
        javax.swing.JPanel tileJP = new javax.swing.JPanel();

        // Label to display the title and the selected team name
        javax.swing.JLabel titleJLabel = new javax.swing.JLabel();

        // Set the default close operation for the dialog
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Set the title of the dialog using the resource bundle
        setTitle(rb.getString("ConnectivityProblems_title"));

        // Set preferred size for the graph panel
        graphJP.setPreferredSize(new java.awt.Dimension(500, 600));
        graphJP.setLayout(new java.awt.BorderLayout());

        // Center-align the title label and set its text
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText(rb.getString("ConnectivityProblems_title") + " " + rb.getString("ConnectivityProblems_of") + " : " + equipo);

        // Create a layout for the title panel
        javax.swing.GroupLayout titleJPLayout = new javax.swing.GroupLayout(tileJP);
        tileJP.setLayout(titleJPLayout);

        // Set horizontal group layout for the title panel
        titleJPLayout.setHorizontalGroup(
                titleJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(titleJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );

        // Set vertical group layout for the title panel
        titleJPLayout.setVerticalGroup(
                titleJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
        );

        // Create a layout for the main panel
        javax.swing.GroupLayout parentJPLayout = new javax.swing.GroupLayout(parentJP);
        parentJP.setLayout(parentJPLayout);

        // Set horizontal group layout for the main panel
        parentJPLayout.setHorizontalGroup(
                parentJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tileJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(graphJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // Set vertical group layout for the main panel
        parentJPLayout.setVerticalGroup(
                parentJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(parentJPLayout.createSequentialGroup()
                                .addComponent(tileJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(graphJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Add the main panel to the content pane of the dialog
        getContentPane().add(parentJP, java.awt.BorderLayout.CENTER);

        // Pack the components within the dialog to fit their preferred sizes
        pack();
    }

    /**
     * Initializes the styles for the mxGraph instance.
     * Sets the behavior for cell connectivity and movement.
     */
    private void initMxGraphStyle() {
        mxGraph = new mxGraph() {
            @Override
            public boolean isCellConnectable(Object cell) {
                return false; // Deshabilitar la creación de nuevas aristas
            }

            @Override
            public boolean isCellMovable(Object cell) {
                return !getModel().isEdge(cell); // Permitir mover solo los vértices
            }
        };
        vertexMap = new HashMap<>();
    }

    /**
     * Visualizes the graph representation of a specific team and its connections.
     * <p>
     * This method retrieves the connected part of the graph for the specified team,
     * represented by the given team name. It updates the graph model by inserting
     * vertices for each team (Equipo) and edges for each connection (Conexion).
     * After updating the model, it applies a hierarchical layout to the graph and
     * creates a graph component to display it within the designated panel.
     *
     * @param vEquipo The name of the team (Equipo) to visualize. This parameter
     *                is used to retrieve the corresponding graph model from the
     *                coordinator.
     * @throws IllegalArgumentException if the specified team does not exist
     *                                  in the coordinator.
     * @see Graph
     * @see Equipo
     * @see Conexion
     * @see mxGraph
     * @see mxGraphComponent
     * @see mxHierarchicalLayout
     */
    public void visualizeGraph(String vEquipo) {

        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

        Graph<Equipo, Conexion> model = coordinator.getConnectedPart(coordinator.getEquipo(vEquipo));
        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Insert vertices for each Equipo object
            for (Equipo equipo : model.vertexSet()) {
                insertColoredVertex(equipo);
            }
            // Insert edges for each Conexion object
            for (Conexion conexion : model.edgeSet()) {
                insertColoredEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
            }
        } finally {
            // End updating the graph model
            mxGraph.getModel().endUpdate();
        }

        // Apply a hierarchical layout to the graph
        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGraph);
        layout.execute(mxGraph.getDefaultParent());

        // Create a graph component to display the graph
        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        graphComponent.setSize(graphJP.getSize()); // Set the size of the graph component to match the panel
        graphComponent.setConnectable(false); // Disable connecting new edges
        graphComponent.zoomAndCenter(); // Center and zoom the graph

        // Add the graph component to the panel
        graphJP.removeAll();
        graphJP.add(graphComponent, BorderLayout.CENTER);
        graphJP.revalidate();
        graphJP.repaint();
    }

    /**
     * Inserts a colored vertex into the graph.
     *
     * @param equipo The Equipo object representing the vertex to be added.
     */
    private void insertColoredVertex(Equipo equipo) {
        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

        // Determine the fill color based on the estado of the Equipo
        String fillColor;
        if (equipo.isEstado()) {
            fillColor = "green";
        } else {
            fillColor = "red";
        }

        String value = equipo.getCodigo();
        if (!equipo.getDireccionesIp().isEmpty()) {
            value += "\n" + equipo.getDireccionesIp().get(0);
        }
        // Insert the vertex with the specified color and dimensions
        Object v = mxGraph.insertVertex(parent, null, value, 0, 0, VERTEX_WIDTH, VERTEX_HEIGHT, VERTEX_STYLE + fillColor);

        // Map the Equipo object to the inserted vertex
        vertexMap.put(equipo, v);
    }


    /**
     * Inserts a colored edge into the graph.
     *
     * @param source   The source Equipo object representing the starting vertex of the edge.
     * @param target   The target Equipo object representing the ending vertex of the edge.
     * @param conexion The Conexion object representing the edge to be added.
     */
    private void insertColoredEdge(Equipo source, Equipo target, Conexion conexion) {
        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

        // Determine the stroke color based on the estado of the source and target Equipos
        String strokeColor;
        if (source.isEstado() && target.isEstado()) {
            strokeColor = "green";
        } else {
            strokeColor = "red";
        }

        // Insert the edge with the specified color and velocidad
        mxGraph.insertEdge(parent, null, conexion.getTipoCable().getVelocidad(), vertexMap.get(source), vertexMap.get(target), EDGE_STYLE + strokeColor);

    }

}

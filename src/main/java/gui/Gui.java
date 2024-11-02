package gui;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import controller.Coordinator;
import models.Conexion;
import models.Equipo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Gui extends javax.swing.JFrame {
    /**
     * Path to the project scope documentation file.
     */
    private static final String DOC_PATH = "Documentacion/Alcance del proyecto - Bonansea Mareano y Rivero Lucia.docx";
    /**
     * Path to the Javadoc documentation index file.
     */
    private static final String JAVADOC_PATH = "Documentacion/Javadoc/index.html";
    /**
     * Width of the vertex
     */
    private static final int VERTEX_WIDTH = 80;
    /**
     * Height of the vertex
     */
    private static final int VERTEX_HEIGHT = 30;
    /**
     * Width of the frame
     */
    private static final int WIDTH_FRAME = 800;
    /**
     * Height of the frame
     */
    private static final int HEIGHT_FRAME = 600;
    /**
     * Flag to enable or disable the simulation mode
     */
    private static boolean simulation = true;
    /**
     * Coordinator instance
     */
    private Coordinator coordinator;
    /**
     * Resource bundle for internationalization
     */
    private ResourceBundle rb;
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
     * Instance of the mxGraph used for visualizing the graph.
     */
    private com.mxgraph.view.mxGraph mxGraph;
    /**
     * Panel to display the graph.
     */
    private javax.swing.JPanel graphJP;
    /**
     * Menu item to enable or disable the simulation mode.
     */
    private javax.swing.JCheckBoxMenuItem simulationMI;
    /**
     * Map to store the association between Equipo objects and their corresponding vertices in the graph.
     */
    private Map<Equipo, Object> vertexMap;

    /**
     * Initializes the GUI components and visualizes the graph.
     * This method sets up the resource bundle, initializes the components,
     * styles, and the mxGraph, and visualizes the graph with the given equipos and conexiones.
     */
    public void init() {
        rb = coordinator.getResourceBundle();
        initComponents();
        initStyles();
        initMxGraphStyle();
        visualizeGraph(coordinator.getEquipos(), coordinator.getConexiones());
    }

    /**
     * Initializes the styles for the GUI components.
     * This method sets the location of the window to the center of the screen,
     * sets the title of the window, sets the icon image, and configures the default close operation.
     */
    private void initStyles() {
        this.setLocationRelativeTo(null);
        this.setTitle(rb.getString("GUI_title"));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/images/MainIcon.png"));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initializes the GUI components.
     * This method creates the layout for the GUI, including the menu bar, the graph panel, and the menu panel.
     */
    private void initComponents() {

        JPanel bg = new JPanel();
        JPanel menuJP = new JPanel();
        JPanel upperMenu = new JPanel();
        JLabel titleLB = new JLabel();
        JPanel upperButtonsPanel = new JPanel();
        JButton equiposBT = new JButton();
        JButton conexionesBT = new JButton();
        JButton tiposEquiposBT = new JButton();
        JButton tiposCablesBT = new JButton();
        JButton tiposPuertosBT = new JButton();
        JButton ubicacionesBT = new JButton();
        JPanel lowerMenu = new JPanel();
        JButton tracerouteBT = new JButton();
        JButton pingBT = new JButton();
        JButton pingRangeBT = new JButton();
        JButton statusMapBT = new JButton();
        JButton problemsBT = new JButton();
        graphJP = new javax.swing.JPanel();
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu();
        JMenu ayudaMenu = new JMenu();
        JMenuItem javaDocMI = new JMenuItem();
        JMenuItem documentacionMI = new JMenuItem();
        simulationMI = new javax.swing.JCheckBoxMenuItem();


        bg.setPreferredSize(new java.awt.Dimension(800, 600));

        menuJP.setMinimumSize(new java.awt.Dimension(0, 400));
        menuJP.setPreferredSize(new java.awt.Dimension(300, 600));
        menuJP.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        upperMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5));
        upperMenu.setLayout(new java.awt.GridLayout(2, 1));

        titleLB.setFont(new java.awt.Font("Unispace", Font.BOLD, 18)); // NOI18N
        titleLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLB.setText(rb.getString("GUI_title"));
        upperMenu.add(titleLB);

        upperButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        upperButtonsPanel.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        equiposBT.setText(rb.getString("TableEquipos_title"));
        equiposBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        equiposBT.addActionListener(this::equiposBTActionPerformed);
        upperButtonsPanel.add(equiposBT);

        conexionesBT.setText(rb.getString("TableConexiones_title"));
        conexionesBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        conexionesBT.addActionListener(this::conexionesBTActionPerformed);
        upperButtonsPanel.add(conexionesBT);

        tiposEquiposBT.setText(rb.getString("TableTiposEquipos_title"));
        tiposEquiposBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tiposEquiposBT.addActionListener(this::tiposEquiposBTActionPerformed);
        upperButtonsPanel.add(tiposEquiposBT);

        tiposCablesBT.setText(rb.getString("TableTiposCables_title"));
        tiposCablesBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tiposCablesBT.addActionListener(this::tiposCablesBTActionPerformed);
        upperButtonsPanel.add(tiposCablesBT);

        tiposPuertosBT.setText(rb.getString("TableTiposPuertos_title"));
        tiposPuertosBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tiposPuertosBT.addActionListener(this::tiposPuertosBTActionPerformed);
        upperButtonsPanel.add(tiposPuertosBT);

        ubicacionesBT.setText(rb.getString("TableUbicaciones_title"));
        ubicacionesBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ubicacionesBT.addActionListener(this::ubicacionesBTActionPerformed);
        upperButtonsPanel.add(ubicacionesBT);

        upperMenu.add(upperButtonsPanel);

        menuJP.add(upperMenu);

        lowerMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5));
        lowerMenu.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        tracerouteBT.setText(rb.getString("GUI_tracerouteButton"));
        tracerouteBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tracerouteBT.addActionListener(this::tracerouteBTActionPerformed);
        lowerMenu.add(tracerouteBT);

        pingBT.setText(rb.getString("GUI_pingButton"));
        pingBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pingBT.addActionListener(this::pingBTActionPerformed);
        lowerMenu.add(pingBT);

        pingRangeBT.setText(rb.getString("GUI_pingRangeButton"));
        pingRangeBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pingRangeBT.addActionListener(this::pingRangeBTActionPerformed);
        lowerMenu.add(pingRangeBT);

        statusMapBT.setText(rb.getString("GUI_statusMapButton"));
        statusMapBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statusMapBT.addActionListener(this::statusMapBTActionPerformed);
        lowerMenu.add(statusMapBT);

        problemsBT.setText(rb.getString("GUI_connectionProblemsButton"));
        problemsBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemsBT.addActionListener(this::problemsBTActionPerformed);
        lowerMenu.add(problemsBT);

        menuJP.add(lowerMenu);

        graphJP.setPreferredSize(new java.awt.Dimension(500, 600));
        graphJP.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(graphJP, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menuJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
        );
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menuJP, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                        .addComponent(graphJP, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );

        menuBar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        modeMenu.setText(rb.getString("GUI_modeMenu"));

        simulationMI.setText(rb.getString("GUI_simulation"));
        simulationMI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        simulationMI.setSelected(simulation);
        simulationMI.addActionListener(_ -> simulation = simulationMI.isSelected());
        modeMenu.add(simulationMI);
        menuBar.add(modeMenu);

        ayudaMenu.setText(rb.getString("GUI_helpMenu"));

        javaDocMI.setText(rb.getString("GUI_javadoc"));
        javaDocMI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        javaDocMI.addActionListener(this::javaDocMIActionPerformed);
        ayudaMenu.add(javaDocMI);

        documentacionMI.setText(rb.getString("GUI_documentation"));
        documentacionMI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        documentacionMI.addActionListener(this::documentacionMIActionPerformed);
        ayudaMenu.add(documentacionMI);

        menuBar.add(ayudaMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 577, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                                        .addGap(0, 0, 0)))
        );

        // Set the preferred size of the frame
        setPreferredSize(new java.awt.Dimension(WIDTH_FRAME, HEIGHT_FRAME));

        pack();
    }

    /**
     * Action performed when the "Equipos" button is clicked.
     * Opens the TableEquiposDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void equiposBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableEquiposDialog(this, true, coordinator);
    }

    /**
     * Action performed when the "Conexiones" button is clicked.
     * Opens the TableConexionesDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void conexionesBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableConexionesDialog(this, true, coordinator);
    }

    /**
     * Action performed when the "Tipos de Equipos" button is clicked.
     * Opens the TableTiposEquiposDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void tiposEquiposBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableTiposEquiposDialog(this, true, coordinator);
    }

    /**
     * Action performed when the "Tipos de Cables" button is clicked.
     * Opens the TableTiposCablesDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void tiposCablesBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableTiposCablesDialog(this, true, coordinator);
    }

    /**
     * Action performed when the "Tipos de Puertos" button is clicked.
     * Opens the TableTiposPuertosDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void tiposPuertosBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableTiposPuertosDialog(this, true, coordinator);
    }

    /**
     * Action performed when the "Ubicaciones" button is clicked.
     * Opens the TableUbicacionesDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void ubicacionesBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableUbicacionesDialog(this, true, coordinator);
    }

    /**
     * Action performed when the "Traceroute" button is clicked.
     * Opens the TraceRouteDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void tracerouteBTActionPerformed(java.awt.event.ActionEvent evt) {
        if (!simulation) {
            new TraceRouteProgressBarDialog(this, true, coordinator);
        } else {
            TraceRouteDialog traceRouteDialog = new TraceRouteDialog(this, true, coordinator);
            traceRouteDialog.setVisible(true);
        }
    }

    /**
     * Action performed when the "Ping" button is clicked.
     * Opens the PingDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void pingBTActionPerformed(java.awt.event.ActionEvent evt) {
        if (!simulation) {
            new PingProgressBarDialog(this, true, coordinator);
        } else {
            PingDialog pingDialog = new PingDialog(this, true, coordinator);
            pingDialog.setVisible(true);
        }
    }

    /**
     * Action performed when the "Ping Range" button is clicked.
     * Opens the PingRangeDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void pingRangeBTActionPerformed(java.awt.event.ActionEvent evt) {
        PingRangeDialog pingRangeDialog = new PingRangeDialog(this, true, coordinator);
        pingRangeDialog.setVisible(true);
    }

    /**
     * Action performed when the "Status Map" button is clicked.
     * Opens the StateMapEquiposDialog.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void statusMapBTActionPerformed(java.awt.event.ActionEvent evt) {
        StateMapEquiposDialog stateMapEquiposDialog = new StateMapEquiposDialog(this, true, coordinator);
        stateMapEquiposDialog.setVisible(true);
    }

    /**
     * Action performed when the "Problems" button is clicked.
     * Opens the ConnectivityProblemsDialog after selecting an equipment.
     *
     * @param evt the ActionEvent triggered by the button click
     */
    private void problemsBTActionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox<String> comboBox = new JComboBox<>(coordinator.getEquiposKeys());
        // Show the JOptionPane with the JComboBox
        int resultado = JOptionPane.showConfirmDialog(null, comboBox, "Selecciona un equipo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            ConnectivityProblemsDialog connectivityProblemsDialog = new ConnectivityProblemsDialog(this, true, coordinator, comboBox.getSelectedItem().toString());
            connectivityProblemsDialog.setVisible(true);
        }
    }

    /**
     * Action performed when the "Javadoc" menu item is clicked.
     * Opens the Javadoc documentation in the default browser.
     *
     * @param evt the ActionEvent triggered by the menu item click
     */
    private void javaDocMIActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Create a URI object with the path to the documentation
            URI uri = new File(JAVADOC_PATH).toURI();

            // Get the desktop instance and open the URI in the default browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            } else {
                System.err.println("Desktop is not supported. Cannot open the documentation.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Action performed when the "Documentation" menu item is clicked.
     * Opens the project scope documentation file in the default application.
     *
     * @param evt the ActionEvent triggered by the menu item click
     */
    private void documentacionMIActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Create a File object with the path to the documentation
            File file = new File(DOC_PATH);

            // Get the desktop instance and open the file in the default application
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file);
                } else {
                    System.err.println("The file does not exist.");
                }
            } else {
                System.err.println("Desktop is not supported. Cannot open the documentation.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the styles for the mxGraph component.
     * Disables the creation of new edges and allows only vertices to be moved.
     */
    private void initMxGraphStyle() {
        mxGraph = new mxGraph() {
            @Override
            public boolean isCellConnectable(Object cell) {
                return false; // Disable the creation of new edges
            }

            @Override
            public boolean isCellMovable(Object cell) {
                return !getModel().isEdge(cell); // Allow only vertices to be moved
            }
        };
        vertexMap = new HashMap<>();
    }

    /**
     * Visualizes the graph by adding vertices and edges, and applying a hierarchical layout.
     *
     * @param equipos    List of Equipo objects representing the vertices of the graph.
     * @param conexiones List of Conexion objects representing the edges of the graph.
     */
    private void visualizeGraph(List<Equipo> equipos, List<Conexion> conexiones) {

        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Insert vertices for each Equipo object
            for (Equipo equipo : equipos) {
                insertColoredVertex(equipo);
            }
            // Insert edges for each Conexion object
            for (Conexion conexion : conexiones) {
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
     * Adds a visual representation of a vertex to the graph.
     *
     * @param equipo The Equipo object representing the vertex to be added.
     */
    public void addVisualVertex(Equipo equipo) {

        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Insert the vertex with the specified color
            insertColoredVertex(equipo);
        } finally {
            // End updating the graph model and repaint the panel
            mxGraph.getModel().endUpdate();
            graphJP.repaint();
        }
    }

    /**
     * Removes a visual representation of a vertex from the graph.
     *
     * @param equipo The Equipo object representing the vertex to be removed.
     */
    public void removeVisualVertex(Equipo equipo) {
        // Get the default parent for the graph

        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Get the vertex associated with the Equipo object
            Object v = vertexMap.get(equipo);

            // Remove all edges connected to the vertex
            Object[] edges = mxGraph.getEdges(v);
            for (Object edge : edges) {
                mxGraph.getModel().remove(edge);
            }

            // Remove the vertex from the graph model
            mxGraph.getModel().remove(v);

            // Remove the vertex from the vertex map
            vertexMap.remove(equipo);
        } finally {
            // End updating the graph model and repaint the panel
            mxGraph.getModel().endUpdate();
            graphJP.repaint();
        }
    }


    /**
     * Modifies the visual representation of a vertex in the graph.
     * This method updates the vertex style and value based on the new Equipo object,
     * and also updates the styles of all edges connected to the vertex.
     *
     * @param o The original Equipo object representing the vertex to be modified.
     * @param n The new Equipo object representing the updated vertex.
     */
    public void modifyVisualVertex(Equipo o, Equipo n) {
        // Get the default parent for the graph

        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Get the vertex associated with the original Equipo object
            Object v = vertexMap.get(o);

            // Set the vertex style based on the estado of the new Equipo object
            if (n.isEstado()) {
                mxGraph.getModel().setStyle(v, VERTEX_STYLE + "green");
            } else {
                mxGraph.getModel().setStyle(v, VERTEX_STYLE + "red");
            }

            // Get all edges connected to the vertex and set their style based on the estado of the new Equipo object
            Object[] edges = mxGraph.getEdges(v);
            for (Object edge : edges) {
                if (n.isEstado()) {
                    mxGraph.getModel().setStyle(edge, EDGE_STYLE + "green");
                } else {
                    mxGraph.getModel().setStyle(edge, EDGE_STYLE + "red");
                }
            }

            // Update the vertex value with the codigo of the new Equipo object
            mxGraph.getModel().setValue(v, n.getCodigo());

            // Update the vertex value with the IP address of the new Equipo object, if available
            if (!n.getDireccionesIp().isEmpty()) {
                mxGraph.getModel().setValue(v, n.getCodigo() + "\n" + n.getDireccionesIp().get(0));
            }

            // Update the vertex map with the new Equipo object
            vertexMap.remove(o);
            vertexMap.put(n, v);
        } finally {
            // End updating the graph model and repaint the panel
            mxGraph.getModel().endUpdate();
            graphJP.repaint();
        }
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
     * Adds a visual representation of an edge to the graph.
     *
     * @param conexion The Conexion object representing the edge to be added.
     */
    public void addVisualEdge(Conexion conexion) {

        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Insert the edge with the specified color
            insertColoredEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
        } finally {
            // End updating the graph model and repaint the panel
            mxGraph.getModel().endUpdate();
            graphJP.repaint();
        }
    }

    /**
     * Removes a visual representation of an edge from the graph.
     *
     * @param conexion The Conexion object representing the edge to be removed.
     */
    public void removeVisualEdge(Conexion conexion) {

        // Begin updating the graph model
        mxGraph.getModel().beginUpdate();
        try {
            // Get the source and target vertices associated with the Conexion object
            Equipo source = conexion.getEquipo1();
            Equipo target = conexion.getEquipo2();

            // Get the edge between the source and target vertices
            Object edge = mxGraph.getEdgesBetween(vertexMap.get(source), vertexMap.get(target))[0];

            // Remove the edge from the graph model
            mxGraph.getModel().remove(edge);

        } finally {
            // End updating the graph model and repaint the panel
            mxGraph.getModel().endUpdate();
            graphJP.repaint();
        }
    }

    /**
     * Modifies the visual representation of an edge in the graph.
     * This method removes the existing edge and adds a new edge with the updated connection.
     *
     * @param o The original Conexion object representing the edge to be modified.
     * @param n The new Conexion object representing the updated edge.
     */
    public void modifyVisualEdge(Conexion o, Conexion n) {
        removeVisualEdge(o);
        addVisualEdge(n);
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

    /**
     * Sets the coordinator instance.
     *
     * @param coordinator the Coordinator instance to set
     */
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

}

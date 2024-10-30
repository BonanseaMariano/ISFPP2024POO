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

public class Gui extends javax.swing.JFrame {
    private Coordinator coordinator;
    private static final String VERTEX_STYLE = "fontColor=white;strokeColor=black;fillColor=";
    private static final String EDGE_STYLE = "endArrow=none;strokeColor=";
    private static final int VERTEX_WIDTH = 80;
    private static final int VERTEX_HEIGHT = 30;

    private static final String DOC_PATH = "Documentacion/Alcance del proyecto - Bonansea Mareano y Rivero Lucia.docx";
    private static final String JAVADOC_PATH = "Documentacion/Javadoc/index.html";

    private com.mxgraph.view.mxGraph mxGraph;
    private Map<Equipo, Object> vertexMap;

    /**
     * Creates new form Gui
     */
    public Gui() {
        initComponents();
        initStyles();
        initMxGraphStyle();
    }

    private void initStyles() {
        this.setLocationRelativeTo(null);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menuJP = new javax.swing.JPanel();
        upperMenu = new javax.swing.JPanel();
        titleLB = new javax.swing.JLabel();
        upperButtonsPanel = new javax.swing.JPanel();
        equiposBT = new javax.swing.JButton();
        conexionesBT = new javax.swing.JButton();
        tiposEquiposBT = new javax.swing.JButton();
        tiposCablesBT = new javax.swing.JButton();
        tiposPuertosBT = new javax.swing.JButton();
        ubicacionesBT = new javax.swing.JButton();
        lowerMenu = new javax.swing.JPanel();
        tracerouteBT = new javax.swing.JButton();
        pingBT = new javax.swing.JButton();
        pingRangeBT = new javax.swing.JButton();
        statusMapBT = new javax.swing.JButton();
        problemsBT = new javax.swing.JButton();
        graphJP = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ayudaMenu = new javax.swing.JMenu();
        javaDocMI = new javax.swing.JMenuItem();
        documentacionMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Red Lan");

        bg.setPreferredSize(new java.awt.Dimension(800, 600));

        menuJP.setMinimumSize(new java.awt.Dimension(0, 400));
        menuJP.setPreferredSize(new java.awt.Dimension(300, 600));
        menuJP.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        upperMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5));
        upperMenu.setLayout(new java.awt.GridLayout(2, 1));

        titleLB.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        titleLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLB.setText("Red Lan");
        upperMenu.add(titleLB);

        upperButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        upperButtonsPanel.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        equiposBT.setText("Equipos");
        equiposBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        equiposBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equiposBTActionPerformed(evt);
            }
        });
        upperButtonsPanel.add(equiposBT);

        conexionesBT.setText("Conexiones");
        conexionesBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        conexionesBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conexionesBTActionPerformed(evt);
            }
        });
        upperButtonsPanel.add(conexionesBT);

        tiposEquiposBT.setText("Tipos Equipos");
        tiposEquiposBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tiposEquiposBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiposEquiposBTActionPerformed(evt);
            }
        });
        upperButtonsPanel.add(tiposEquiposBT);

        tiposCablesBT.setText("Tipos Cables");
        tiposCablesBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tiposCablesBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiposCablesBTActionPerformed(evt);
            }
        });
        upperButtonsPanel.add(tiposCablesBT);

        tiposPuertosBT.setText("Tipos Puertos");
        tiposPuertosBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tiposPuertosBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiposPuertosBTActionPerformed(evt);
            }
        });
        upperButtonsPanel.add(tiposPuertosBT);

        ubicacionesBT.setText("Ubicaciones");
        ubicacionesBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ubicacionesBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubicacionesBTActionPerformed(evt);
            }
        });
        upperButtonsPanel.add(ubicacionesBT);

        upperMenu.add(upperButtonsPanel);

        menuJP.add(upperMenu);

        lowerMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5));
        lowerMenu.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        tracerouteBT.setText("Ruta entre Equipos");
        tracerouteBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tracerouteBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tracerouteBTActionPerformed(evt);
            }
        });
        lowerMenu.add(tracerouteBT);

        pingBT.setText("Ping");
        pingBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pingBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingBTActionPerformed(evt);
            }
        });
        lowerMenu.add(pingBT);

        pingRangeBT.setText("Ping Rango");
        pingRangeBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pingRangeBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingRangeBTActionPerformed(evt);
            }
        });
        lowerMenu.add(pingRangeBT);

        statusMapBT.setText("Mapa de estado equipos");
        statusMapBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statusMapBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusMapBTActionPerformed(evt);
            }
        });
        lowerMenu.add(statusMapBT);

        problemsBT.setText("Problemas Conectividad");
        problemsBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemsBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                problemsBTActionPerformed(evt);
            }
        });
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

        jMenu1.setText("File");
        menuBar.add(jMenu1);

        ayudaMenu.setText("Ayuda");

        javaDocMI.setText("JavaDoc");
        javaDocMI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        javaDocMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                javaDocMIActionPerformed(evt);
            }
        });
        ayudaMenu.add(javaDocMI);

        documentacionMI.setText("Documentacion");
        documentacionMI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        documentacionMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentacionMIActionPerformed(evt);
            }
        });
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tracerouteBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tracerouteBTActionPerformed
        // TODO add your handling code here:

        TraceRouteDialog traceRouteDialog = new TraceRouteDialog(this, true, coordinator);
        traceRouteDialog.setVisible(true);
    }//GEN-LAST:event_tracerouteBTActionPerformed

    private void pingBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingBTActionPerformed
        // TODO add your handling code here:

        PingDialog pingDialog = new PingDialog(this, true, coordinator);
        pingDialog.setVisible(true);
    }//GEN-LAST:event_pingBTActionPerformed

    private void statusMapBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusMapBTActionPerformed
        System.out.println("\t---- Status Map ----");
        for (Map.Entry<Equipo, Boolean> entry : coordinator.mapStatus().entrySet()) {
            System.out.println(entry.getKey().getCodigo() + " " + entry.getValue());
        }
        // TODO add your handling code here:
        StateMapEquiposDialog stateMapEquiposDialog = new StateMapEquiposDialog(this, true, coordinator);
        stateMapEquiposDialog.setVisible(true);
    }//GEN-LAST:event_statusMapBTActionPerformed

    private void problemsBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_problemsBTActionPerformed
        // TODO add your handling code here:

        JComboBox<String> comboBox = new JComboBox<>(coordinator.getEquiposKeys());
        // Mostrar el JOptionPane con el JComboBox
        int resultado = JOptionPane.showConfirmDialog(null, comboBox, "Selecciona un equipo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            ConnectivityProblemsDialog connectivityProblemsDialog = new ConnectivityProblemsDialog(this, true, coordinator, comboBox.getSelectedItem().toString());

            connectivityProblemsDialog.setVisible(true);
        }
    }//GEN-LAST:event_problemsBTActionPerformed

    private void equiposBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableEquiposDialog(this, true, coordinator);
    }

    private void conexionesBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableConexionesDialog(this, true, coordinator);
    }

    private void tiposEquiposBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableTiposEquiposDialog(this, true, coordinator);
    }

    private void tiposCablesBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableTiposCablesDialog(this, true, coordinator);
    }

    private void tiposPuertosBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableTiposPuertosDialog(this, true, coordinator);
    }

    private void ubicacionesBTActionPerformed(java.awt.event.ActionEvent evt) {
        new TableUbicacionesDialog(this, true, coordinator);
    }

    private void pingRangeBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingRangeBTActionPerformed
        // TODO add your handling code here:

        PingRangeDialog pingRangeDialog = new PingRangeDialog(this, true, coordinator);
        pingRangeDialog.setVisible(true);
    }//GEN-LAST:event_pingRangeBTActionPerformed

    private void javaDocMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaDocMIActionPerformed
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
    }//GEN-LAST:event_javaDocMIActionPerformed

    private void documentacionMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentacionMIActionPerformed
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
    }//GEN-LAST:event_documentacionMIActionPerformed

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
     * Visualizes the graph by adding vertices and edges, and applying a hierarchical layout.
     *
     * @param equipos    List of Equipo objects representing the vertices of the graph.
     * @param conexiones List of Conexion objects representing the edges of the graph.
     */
    public void visualizeGraph(List<Equipo> equipos, List<Conexion> conexiones) {

        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

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
        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

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
        Object parent = mxGraph.getDefaultParent();

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
        Object parent = mxGraph.getDefaultParent();

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
        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

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
        // Get the default parent for the graph
        Object parent = mxGraph.getDefaultParent();

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

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ayudaMenu;
    private javax.swing.JPanel bg;
    private javax.swing.JButton conexionesBT;
    private javax.swing.JMenuItem documentacionMI;
    private javax.swing.JButton equiposBT;
    private javax.swing.JPanel graphJP;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem javaDocMI;
    private javax.swing.JPanel lowerMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel menuJP;
    private javax.swing.JButton pingBT;
    private javax.swing.JButton pingRangeBT;
    private javax.swing.JButton problemsBT;
    private javax.swing.JButton statusMapBT;
    private javax.swing.JButton tiposCablesBT;
    private javax.swing.JButton tiposEquiposBT;
    private javax.swing.JButton tiposPuertosBT;
    private javax.swing.JLabel titleLB;
    private javax.swing.JButton tracerouteBT;
    private javax.swing.JButton ubicacionesBT;
    private javax.swing.JPanel upperButtonsPanel;
    private javax.swing.JPanel upperMenu;
    // End of variables declaration//GEN-END:variables
}

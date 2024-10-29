/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
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
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucia
 */
public class ConnectivityProblemsDialog extends javax.swing.JDialog {

    private static final String VERTEX_STYLE = "fontColor=white;strokeColor=black;fillColor=";
    private static final String EDGE_STYLE = "endArrow=none;strokeColor=";
    private static final int VERTEX_WIDTH = 80;
    private static final int VERTEX_HEIGHT = 30;

    private com.mxgraph.view.mxGraph mxGraph;
    private Map<Equipo, Object> vertexMap;
    private Coordinator coordinator;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel parentJP;
    private javax.swing.JPanel graphJP;
    private javax.swing.JPanel tileJP;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form ConnectivityProblemsDialog
     */
    public ConnectivityProblemsDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator, String equipo) {
        super(parent, modal);
        this.coordinator = coordinator;
        initComponents();
        initMxGraphStyle();
        visualizeGraph(equipo);
        initStyles();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentJP = new javax.swing.JPanel();
        graphJP = new javax.swing.JPanel();
        tileJP = new javax.swing.JPanel();
        titleJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Connectivity Problems");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(graphJP);
        graphJP.setPreferredSize(new java.awt.Dimension(500, 600));
        graphJP.setLayout(new java.awt.BorderLayout());

        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("Problemas de conexion");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(tileJP);
        tileJP.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(parentJP);
        parentJP.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tileJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(graphJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tileJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(graphJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(parentJP, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
     * @param vEquipo   String of Equipo object representing the vertices of the graph.
     * //@param conexiones List of Conexion objects representing the edges of the graph.
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

    private void initStyles() {
        this.setLocationRelativeTo(null);
    }

}

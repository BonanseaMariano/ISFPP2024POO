package gui;

import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import models.Conexion;
import models.Equipo;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPanel extends JPanel {
    private JPanel graphPanel;
    private JPanel menuPanel;

    public MainPanel() {
        graphPanel = new JPanel(new BorderLayout());
        menuPanel = new JPanel(new FlowLayout());
        initStyle();
    }

    private void initStyle() {
        setLayout(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.SOUTH);
    }

    public void visualizeGraph(List<Equipo> equipos, List<Conexion> conexiones) {
        mxGraph mxGraph = new mxGraph() {
            @Override
            public boolean isCellConnectable(Object cell) {
                return false; // Deshabilitar la creación de nuevas aristas
            }

            @Override
            public boolean isCellMovable(Object cell) {
                return !getModel().isEdge(cell); // Permitir mover solo los vértices
            }
        };
        Object parent = mxGraph.getDefaultParent();

        mxGraph.getModel().beginUpdate();
        try {
            Map<Equipo, Object> vertexMap = new HashMap<>();
            for (Equipo equipo : equipos) {
                Object v = mxGraph.insertVertex(parent, null, equipo.getCodigo(), 0, 0, 40, 30);
                vertexMap.put(equipo, v);
            }
            for (Conexion conexion : conexiones) {
                Equipo source = conexion.getEquipo1();
                Equipo target = conexion.getEquipo2();
                String edgeStyle = "endArrow=none";
                mxGraph.insertEdge(parent, null, conexion.getTipoCable().getVelocidad(), vertexMap.get(source), vertexMap.get(target), edgeStyle);
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }

        mxOrganicLayout layout = new mxOrganicLayout(mxGraph);
        layout.execute(mxGraph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        graphComponent.setConnectable(false); // Deshabilitar la conexión de nuevos arcos
        graphComponent.zoomAndCenter(); // Centrar y ajustar el zoom del grafo

        graphPanel.removeAll();
        graphPanel.add(graphComponent, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }
}

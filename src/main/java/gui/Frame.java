package gui;

import controller.Coordinator;
import models.Conexion;
import models.Equipo;

import javax.swing.*;
import java.util.List;

public class Frame extends JFrame {
    private Coordinator coordinator;
    private MainPanel mainPanel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public Frame() {
        initStyle();
        mainPanel = new MainPanel();
        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void initStyle() {
        setTitle("Redes");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void visualizeGraph(List<Equipo> equipos, List<Conexion> conexiones) {
        mainPanel.visualizeGraph(coordinator.getEquipos(), coordinator.getConexiones());
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}

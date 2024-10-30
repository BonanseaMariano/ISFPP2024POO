package test;

import config.Config;
import controller.Coordinator;
import gui.Gui;
import logic.Logic;
import logic.Red;
import models.Conexion;

public class GuiTest {
    Red red;
    Logic logic;
    Config config;
    Gui gui;
    Coordinator coordinator;

    /**
     * The main method to run the GUI test.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GuiTest guiTest = new GuiTest();
        guiTest.setUp();
    }

    /**
     * Sets up the test environment by instantiating the necessary classes and establishing relationships between them.
     */
    private void setUp() {
        // Instantiate the classes
        red = Red.getRed();
        logic = new Logic();
        gui = new Gui();
        config = Config.getConfig();
        coordinator = new Coordinator();

        // Establish relationships between classes
        logic.setCoordinator(coordinator);
        red.setCoordinator(coordinator);
        gui.setCoordinator(coordinator);
        config.setCoordinator(coordinator);

        // Establish relationships with the coordinator class
        coordinator.setRed(red);
        coordinator.setLogic(logic);
        coordinator.setGui(gui);
        coordinator.setConfig(config);

        // Update data and print connections
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());
        for (Conexion conexion : coordinator.getConexiones()) {
            System.out.println(conexion.getEquipo1().getCodigo() + " -> " + conexion.getEquipo2().getCodigo());
        }

        // Visualize the graph and show the GUI
        gui.init();
        gui.setVisible(true);
    }
}
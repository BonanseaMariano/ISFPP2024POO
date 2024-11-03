package app;

import config.Config;
import controller.Coordinator;
import gui.Gui;
import logic.Logic;
import logic.Red;

/**
 * Main class for the RedLan application.
 * This class sets up the environment by instantiating the necessary classes and establishing relationships between them.
 */
public class RedLan {

    /**
     * The main method to start the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        RedLan redLan = new RedLan();
        redLan.setUp();
    }

    /**
     * Sets up the test environment by instantiating the necessary classes and establishing relationships between them.
     */
    private void setUp() {
        // Instantiate the classes
        //Logica
        //The network logic.
        Red red = Red.getRed();
        //The application logic.
        Logic logic = new Logic();
        //The configuration settings.
        Config config = Config.getConfig();

        //Vista
        //The graphical user interface.
        Gui gui = new Gui();

        //Controlador
        //The coordinator that manages interactions between components.
        Coordinator coordinator = new Coordinator();

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

        // Update data of logic
        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());

        // Visualize the graph and show the GUI
        gui.init();
        gui.setVisible(true);

    }
}
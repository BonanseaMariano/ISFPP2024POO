package test;

import controller.Coordinator;
import gui.Gui;
import logic.Logic;
import logic.Red;
import models.Conexion;

public class GuiTest {
    Red red;
    Logic logic;
    Coordinator coordinator;
    Gui gui;

    public static void main(String[] args) {
        GuiTest guiTest = new GuiTest();
        guiTest.setUp();
    }


    private void setUp() {
        /* Se instancian las clases */
        red = Red.getRed();
        logic = new Logic();
        gui = new Gui();
        coordinator = new Coordinator();


        /* Se establecen las relaciones entre clases */
        logic.setCoordinator(coordinator);
        red.setCoordinator(coordinator);
        gui.setCoordinator(coordinator);

        /* Se establecen relaciones con la clase coordinador */
        coordinator.setRed(red);
        coordinator.setLogic(logic);
        coordinator.setGui(gui);

        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());
        for (Conexion conexion : coordinator.getConexiones()) {
            System.out.println(conexion.getEquipo1().getCodigo() + " -> " + conexion.getEquipo2().getCodigo());
        }

        gui.visualizeGraph(coordinator.getEquipos(), coordinator.getConexiones());
        gui.show();
    }
}

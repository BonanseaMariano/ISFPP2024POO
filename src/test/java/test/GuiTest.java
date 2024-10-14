package test;

import controller.Coordinator;
import gui.Frame;
import logic.Logic;
import logic.Red;
import models.Conexion;

public class GuiTest {
    Red red;
    Logic logic;
    Coordinator coordinator;
    Frame frame;

    public static void main(String[] args) {
        GuiTest guiTest = new GuiTest();
        guiTest.setUp();
    }


    private void setUp() {
        /* Se instancian las clases */
        red = Red.getRed();
        logic = new Logic();
        frame = new Frame();
        coordinator = new Coordinator();

        /* Se establecen las relaciones entre clases */
        logic.setCoordinator(coordinator);
        frame.setCoordinator(coordinator);

        /* Se establecen relaciones con la clase coordinador */
        coordinator.setRed(red);
        coordinator.setLogic(logic);
        coordinator.setGui(frame);

        logic.updateData(coordinator.getEquipos(), coordinator.getConexiones());
        for (Conexion conexion : coordinator.getConexiones()) {
            System.out.println(conexion.getEquipo1().getCodigo() + " -> " + conexion.getEquipo2().getCodigo());
        }
        frame.visualizeGraph(coordinator.getEquipos(), coordinator.getConexiones());
    }


}

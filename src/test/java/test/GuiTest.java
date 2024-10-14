package test;

import controller.Coordinator;
import gui.Frame;
import logic.Logic;
import logic.Red;

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
        System.out.println(coordinator.getEquipos());
        System.out.println(coordinator.getConexiones());
        frame.visualizeGraph(coordinator.getEquipos(), coordinator.getConexiones());
    }


}

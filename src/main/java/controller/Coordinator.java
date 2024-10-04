package controller;

import logic.Logic;
import models.Red;

public class Coordinator {
    private Red red;
    private Logic logic;

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }


}

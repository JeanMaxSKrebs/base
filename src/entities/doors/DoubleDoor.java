package entities.doors;

import java.awt.image.BufferedImage;

public class DoubleDoor extends Door {

    private int secondDoorX;
    private int secondDoorY;

    public DoubleDoor(int x, int y, int width, int height, BufferedImage sprite, int secondDoorX, int secondDoorY) {
        super(x, y, width, height, sprite);
        this.secondDoorX = secondDoorX;
        this.secondDoorY = secondDoorY;
    }

    public int getSecondDoorX() {
        return secondDoorX;
    }

    public void setSecondDoorX(int secondDoorX) {
        this.secondDoorX = secondDoorX;
    }

    public int getSecondDoorY() {
        return secondDoorY;
    }

    public void setSecondDoorY(int secondDoorY) {
        this.secondDoorY = secondDoorY;
    }
}

package allClasses;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Walnut extends Plant{
    private Image fullHealthGIF;
    private Image halfHealthGIF;
    private Image dyingGIF;

    public Walnut(int x, int y, ArrayList zombies) {
        super(100, x, y, "images/active_walnut.png","images/inactive_walnut.png", zombies);
        fullHealthGIF = new Image("images/walnut.gif");
        halfHealthGIF = new Image("images/walnut_half_life.gif");
        dyingGIF = new Image("images/walnut_dead.gif");
    }

    public Image getFullHealthGIF() {
        return fullHealthGIF;
    }

    public Image getHalfHealthGIF() {
        return halfHealthGIF;
    }

    public Image getDyingGIF() {
        return dyingGIF;
    }

    @Override
    public void killPlant() {
    }

    @Override
    public void detectCollisions(boolean activate) {

    }
}

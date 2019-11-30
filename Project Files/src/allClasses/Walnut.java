package allClasses;

import javafx.scene.image.Image;

public class Walnut extends Plant{
    private Image fullHealthGIF;
    private Image halfHealthGIF;
    private Image dyingGIF;

    public Walnut(int x, int y) {
        super(100, x, y, "images/active_walnut.png","images/inactive_walnut.png");
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
}

package allClasses;

import javafx.scene.image.Image;

public abstract class DrawableZombie {
    protected Image aliveGIF;
    protected Image dyingGIF;

    public Image getAliveGIF() {
        return aliveGIF;
    }

    public Image getDyingGIF() {
        return dyingGIF;
    }

    public abstract int getHealth();
}

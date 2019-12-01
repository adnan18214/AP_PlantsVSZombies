package allClasses;

import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class DrawableZombie implements Serializable {
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

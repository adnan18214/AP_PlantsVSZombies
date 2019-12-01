package allClasses;

import javafx.scene.image.Image;

import java.io.Serializable;

public class LocalZombie extends DrawableZombie implements Serializable {
    private int health;

    public LocalZombie(){
        health = 25;
        aliveGIF = new Image("images/zombie_normal.gif");
        dyingGIF = new Image("images/zombie_normal_dying.gif");
    }

    public int getHealth(){
        return health;
    }
}

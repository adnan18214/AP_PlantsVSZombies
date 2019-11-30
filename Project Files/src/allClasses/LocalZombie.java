package allClasses;

import javafx.scene.image.Image;

public class LocalZombie extends DrawableZombie {
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

package allClasses;

import javafx.scene.image.Image;

public class FootballZombie extends DrawableZombie {
    private int health;

    public FootballZombie(){
        health = 35;
        aliveGIF = new Image("images/zombie_football.gif");
        dyingGIF = new Image("images/zombie_football_dying.gif");
    }

    public int getHealth(){
        return health;
    }
}

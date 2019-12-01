package allClasses;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.Serializable;

public class Zombie implements Serializable
{
    private double posX;
    private double posY;
    private boolean alive;
    private int row;
    private int health;
    private ImageView zombieIV;
    private PathTransition movement;
    private AnchorPane animationLayer;

    public Zombie(ImageView z, double x, double y, int r, AnchorPane animationLayer, int health)
    {
        this.posX = x;
        this.row = r;
        this.posY = y;
        this.health = health;
        zombieIV = z;
        alive = true;
        this.animationLayer = animationLayer;

    }

    public int getRow() {
        return row;
    }

    public ImageView getZombieIV() {
        return zombieIV;
    }

    public void attackZombie(int damage){
        health -= damage;
        if(health < 0) {
            health = 0;
            alive = false;
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public int getHealth() {
        return health;
    }

    public void setMovement(PathTransition movement) {
        this.movement = movement;
    }

    public void killZombie(){
        Lawn lawn = Lawn.getLawn();

        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.millis(400));
        pause.setOnFinished(e -> {
            animationLayer.getChildren().removeAll(zombieIV);
        });

        Image dying = new Image("./images/zombie_normal_dying.gif");
        zombieIV.setImage(dying);
        lawn.removeZombie(this);
        movement.stop();
        pause.play();
    }

    public void stopZombie(){
        movement.pause();
    }

    public void resumeZombie(){
        movement.play();
    }
}

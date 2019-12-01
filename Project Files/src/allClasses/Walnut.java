package allClasses;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Walnut extends Plant implements Serializable {

    private int halfHealth;
    private transient Image halfHealthGIF;
    private transient Timeline collider;
    private transient final Bounds plantBounds;


    public Walnut(int x, int y, ArrayList zombies, ImageView plant) {
        super(25, 200, x, y, "images/active_walnut.png","images/inactive_walnut.png", zombies);
        aliveGIF = new Image("images/walnut.gif");
        halfHealthGIF = new Image("images/walnut_half_life.gif");
        dyingGIF = new Image("images/walnut_dead.gif");
        plantIV = plant;
        halfHealth = this.getHealth()/2;

        if(zombies != null)
            plantBounds = ((GridPane) plantIV.getParent()).localToParent(plantIV.getBoundsInParent());
        else
            plantBounds = null;

        AtomicBoolean flag = new AtomicBoolean(true);
        collider = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            if(isZombieAttacking()) {
                for (Zombie frontZombie: attackingZombies ) {
                    if(plantBounds.intersects(frontZombie.getZombieIV().getBoundsInParent())){
                        frontZombie.stopZombie();
                        this.attackPlant(4);
                        if(this.getHealth() < halfHealth && flag.get()){
                            plantIV.setImage(halfHealthGIF);
                            flag.set(false);
                        }
                    }
                    if(!frontZombie.isAlive()){
                        frontZombie.stopZombie();
                        frontZombie.killZombie();
                    }
                    if(!this.isAlive()){
                        attackingZombies.forEach((Zombie z)->{
                            z.resumeZombie();
                        });
                        this.killPlant();
                        break;
                    }
                }
            }
        }));
        collider.setCycleCount(Animation.INDEFINITE);
    }

    public Image getAliveGIF() {
        return aliveGIF;
    }

    public Image getHalfHealthGIF() {
        return halfHealthGIF;
    }

    public Image getDyingGIF() {
        return dyingGIF;
    }

    /**
     * stops animations and removes the plant.
     */
    @Override
    public void killPlant() {
        collider.stop();
        Lawn lawn = Lawn.getLawn();

        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.millis(500));
        pause.setOnFinished(e -> {
            ((GridPane) plantIV.getParent()).getChildren().removeAll(plantIV);
        });

        plantIV.setImage(dyingGIF);
        lawn.removePlant(this);
        pause.play();
    }

    /**
     * activates collision detection animation.
     * @param activate
     */
    @Override
    public void detectCollisions(boolean activate) {
        if(activate)
            collider.play();
        else
            collider.pause();
    }
}

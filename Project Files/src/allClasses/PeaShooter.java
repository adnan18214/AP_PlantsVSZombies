package allClasses;

import FXMLs.HouseAndLawnController;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

public class PeaShooter extends Plant implements shooterPlant, Serializable {
    private transient Image peaBullet;
    private transient ImageView bulletIV;
    private transient AnchorPane pane;
    private transient PathTransition movingBullet;
    private transient Timeline collider;
    private transient final Bounds plantBounds;

    public PeaShooter(int x, int y, ArrayList zombies, ImageView plant){
        super(100, 100, x, y,"images/active_peashooter.png","images/inactive_peashooter.png", zombies);
        aliveGIF = new Image("images/peashooter.gif");
        dyingGIF = new Image("images/peashooter_dying.gif");
        peaBullet = new Image("images/Pea.png");
        plantIV = plant;

        if(zombies != null)
            plantBounds = ((GridPane) plantIV.getParent()).localToParent(plantIV.getBoundsInParent());
        else
            plantBounds = null;

        collider = new Timeline(new KeyFrame(Duration.millis(70), e -> {
            if(isZombieAttacking()) {
                for (int i = 0; i < attackingZombies.size(); i++) {
                    Zombie frontZombie = attackingZombies.get(i);
                    if (bulletIV.getBoundsInParent().intersects(frontZombie.getZombieIV().getBoundsInParent()) && !bulletIV.isDisabled()) {
                        bulletIV.setVisible(false);
                        bulletIV.setDisable(true);
                        frontZombie.attackZombie(5);
//                        System.out.println(frontZombie.getHealth());
                    }
                    if (plantBounds.intersects(frontZombie.getZombieIV().getBoundsInParent())) {
                        frontZombie.stopZombie();
                        this.attackPlant(4);
                    }
                    if (!frontZombie.isAlive()) {
                        i--;
                        frontZombie.stopZombie();
                        frontZombie.killZombie();
                    }
                    if (!this.isAlive()) {
                        attackingZombies.forEach((Zombie z) -> {
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

    public Image getDyingGIF() {
        return dyingGIF;
    }

    /**
     * Creates animated bullets.
     * @param target represents ImageView where plant is dropped.
     * @return movingBullet represents the animation.
     */
    public PathTransition shootBullets(ImageView target) {
        int row = GridPane.getRowIndex(target);
        int col = GridPane.getColumnIndex(target);
        GridPane g = (GridPane) target.getParent();
        bulletIV = new ImageView(peaBullet);

        bulletIV.setX(g.getLayoutX() + (col - 1) * 90 + 69);
        bulletIV.setY(g.getLayoutY() + (row - 1) * 109 + 37);

        pane = (AnchorPane) target.getParent().getParent();
        pane.getChildren().add(bulletIV);
        animatePea(bulletIV);
        movingBullet.play();
        return movingBullet;
    }

    /**
     * Sets the default PathTransition animation of bullet moving.
     * @param PEA represents the bullet image.
     */
    private void animatePea(ImageView PEA){
        PathTransition movePea = new PathTransition();
        Line pPath = new Line(PEA.getX(), PEA.getY()+5, PEA.getX()+1000, PEA.getY()+5);
        movePea.setNode(PEA);
        movePea.setPath(pPath);
        movePea.setDuration(Duration.seconds(1));
        movePea.setCycleCount(1);
        movingBullet = movePea;
    }

    /**
     * Stops animations and removes the plant.
     */
    @Override
    public void killPlant() {
        collider.stop();
        HouseAndLawnController.removeFromAnimationGroup(movingBullet);
        movingBullet.stop();
        pane.getChildren().remove(bulletIV);

        Lawn lawn = Lawn.getLawn();

        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.millis(400));
        pause.setOnFinished(e -> {
            ((GridPane) plantIV.getParent()).getChildren().removeAll(plantIV);
        });

        plantIV.setImage(dyingGIF);
        lawn.removePlant(this);
        pause.play();
    }

    /**
     * activates collision timeline.
     * @param activate
     */
    @Override
    public void detectCollisions(boolean activate) {
        if(activate)
            collider.play();
        else
            collider.pause();

    }

    public PathTransition getAnimation() {
        return movingBullet;
    }

    public ImageView getBullet() {
        return bulletIV;
    }
}

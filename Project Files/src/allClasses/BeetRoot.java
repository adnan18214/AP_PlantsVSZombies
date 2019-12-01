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

import java.util.ArrayList;

public class BeetRoot extends Plant implements shooterPlant{
    private Image beetBullet;
    private PathTransition movingBullet;
    private ImageView bulletIV;
    private AnchorPane pane;
    private Timeline collider;
    private final Bounds plantBounds;

    public BeetRoot(int x, int y, ArrayList zombies, ImageView plant){
        super(125, 100, x, y, "images/active_beetroot.png", "images/inactive_beetroot.png", zombies);
        aliveGIF = new Image("images/beetroot.gif");
        dyingGIF = new Image("images/beetroot_dying.gif");
        beetBullet = new Image("images/beetbullet.png");
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
        bulletIV = new ImageView(beetBullet);

        bulletIV.setX(g.getLayoutX() + (col - 1) * 90 + 69);
        bulletIV.setY(g.getLayoutY() + (row - 1) * 109 + 62);

        pane = (AnchorPane) target.getParent().getParent();
        pane.getChildren().add(bulletIV);
        animateBeet(bulletIV);
        movingBullet.play();
        return movingBullet;
    }

    /**
     * Sets the default PathTransition animation of bullet moving.
     * @param BEET represents the bullet image.
     */
    private void animateBeet(ImageView BEET){
        PathTransition moveBeet = new PathTransition();
        Line pPath = new Line(BEET.getX(), BEET.getY()+5, BEET.getX()+1000, BEET.getY()+5);
        moveBeet.setNode(BEET);
        moveBeet.setPath(pPath);
        moveBeet.setDuration(Duration.seconds(2));
        moveBeet.setCycleCount(1);
        movingBullet = moveBeet;
    }

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

        for(int i = 1; i <= 5; i++){
            ArrayList<Zombie> allZombies= lawn.getZombies(i);
            for(int n = 0; n < allZombies.size(); i++){
                if (plantBounds.intersects(allZombies.get(n).getZombieIV().getBoundsInParent())){
                    n--;
                    allZombies.get(0).stopZombie();
                    allZombies.get(0).killZombie();
                }
            }
        }
//        for (int i = 0; i < attackingZombies.size(); i++) {
//            Zombie frontZombie = attackingZombies.get(i);
//            if (plantBounds.intersects(frontZombie.getZombieIV().getBoundsInParent())){
//                i--;
//                frontZombie.stopZombie();
//                frontZombie.killZombie();
//            }
//        }

    }

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

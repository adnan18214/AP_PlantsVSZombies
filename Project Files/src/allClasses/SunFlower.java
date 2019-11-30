package allClasses;

import FXMLs.HouseAndLawnController;
import FXMLs.HouseAndLawnParent;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class SunFlower extends Plant{
    private Text sunTokenCount;
    private ImageView token;
    private AnchorPane pane;
    private SequentialTransition showingToken;
    private Timeline collider;
    private final Bounds plantBounds;

    public SunFlower(int x, int y, Text sCount, ImageView targetIV, ArrayList zombies) {
        super(50, 100, x, y,"images/active_sunflower.png","images/inactive_sunflower.png", zombies);
        aliveGIF = new Image("images/sunflower.gif");
        dyingGIF = new Image("images/sunflower_dying.gif");
        sunTokenCount = sCount;
        plantIV = targetIV;

        if(zombies != null)
            plantBounds = ((GridPane) plantIV.getParent()).localToParent(plantIV.getBoundsInParent());
        else
            plantBounds = null;

        collider = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            if(isZombieAttacking()) {
                for (Zombie frontZombie: attackingZombies ) {
                    if (plantBounds.intersects(frontZombie.getZombieIV().getBoundsInParent())) {
                        frontZombie.stopZombie();
                        this.attackPlant(5);
                    }
                    if (!frontZombie.isAlive()) {
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

    public SequentialTransition generateTokens(Text sunCounter){
        SunToken sun = new SunToken(sunTokenCount);
        token  = sun.getSuntoken(plantIV.getParent().getLayoutX() + plantIV.getBoundsInParent().getCenterX(), plantIV.getParent().getLayoutY() + plantIV.getBoundsInParent().getCenterY());
        token.setOpacity(0);
        pane = (AnchorPane) plantIV.getParent().getParent();

        FadeTransition appear = new FadeTransition(Duration.seconds(0.5), token);
        appear.setFromValue(0.0);
        appear.setToValue(1.0);

        FadeTransition disappear = new FadeTransition(Duration.seconds(0.5), token);
        disappear.setFromValue(1.0);
        disappear.setToValue(0.0);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        showingToken = new SequentialTransition(appear,pause,disappear);
        showingToken.setCycleCount(1);
        showingToken.setDelay(Duration.seconds(2));

        pane.getChildren().add(token);
        return showingToken;
    }

    public SequentialTransition getAnimation() {
        return showingToken;
    }

    @Override
    public void killPlant() {
        collider.stop();
        HouseAndLawnParent.removeFromAnimationGroup(showingToken);
        showingToken.stop();
        pane.getChildren().remove(token);

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

    @Override
    public void detectCollisions(boolean activate) {
        if(activate)
            collider.play();
        else
            collider.pause();
    }

    public ImageView getToken() {
        return token;
    }
}

package allClasses;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class SunFlower extends Plant{
    private Image aliveGIF;
    private Image dyingGIF;
    private Text sunTokenCount;
    private ImageView plantIV;
    private ImageView token;
    private AnchorPane pane;
    private SequentialTransition showingToken;

    public SunFlower(int x, int y, Text sCount, ImageView targetIV, ArrayList zombies) {
        super(100, x, y, zombies);
        aliveGIF = new Image("images/sunflower.gif");
        dyingGIF = new Image("images/sunflower_dying.gif");
        sunTokenCount = sCount;
        plantIV = targetIV;
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
        showingToken.setDelay(Duration.seconds(3));

        pane.getChildren().add(token);
        return showingToken;
    }

    public SequentialTransition getAnimation() {
        return showingToken;
    }

    @Override
    public void killPlant() {
        showingToken.stop();
        pane.getChildren().remove(token);
    }

    @Override
    public void detectCollisions(boolean activate) {

    }

    public ImageView getToken() {
        return token;
    }
}

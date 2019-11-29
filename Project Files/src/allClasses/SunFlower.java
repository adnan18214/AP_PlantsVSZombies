package allClasses;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class SunFlower extends Plant{
    private Image aliveGIF;
    private Image dyingGIF;
    private Text sunTokenCount;
    private ImageView targetIV;
    private ImageView token;
    private AnchorPane pane;
    private SequentialTransition showingToken;

    public SunFlower(int x, int y, Text sCount, ImageView targetIV) {
        super(100, x, y);
        aliveGIF = new Image("images/sunflower.gif");
        dyingGIF = new Image("images/sunflower_dying.gif");
        sunTokenCount = sCount;
        this.targetIV = targetIV;
    }

    public Image getAliveGIF() {
        return aliveGIF;
    }

    public Image getDyingGIF() {
        return dyingGIF;
    }

    public SequentialTransition generateTokens(Text sunCounter){
        SunToken sun = new SunToken(sunTokenCount);
        token  = sun.getSuntoken(targetIV.getParent().getLayoutX() + targetIV.getBoundsInParent().getCenterX(), targetIV.getParent().getLayoutY() + targetIV.getBoundsInParent().getCenterY());
        token.setOpacity(0);
        pane = (AnchorPane) targetIV.getParent().getParent();

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
        showingToken.setOnFinished(e -> {
            showingToken.setDelay(Duration.seconds(ThreadLocalRandom.current().nextInt(5,8)));
            if(token.getImage() == null)
                token.setImage(sun.getSunImage());
            showingToken.play();
        });
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
}

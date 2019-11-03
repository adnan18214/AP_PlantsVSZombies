package FXMLs;

import gameRunner.Main;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class HouseAndLawnController implements Initializable {
    @FXML
    private ImageView shade;
    @FXML
    private ImageView sunToken;
    @FXML
    private AnchorPane animationLayer;
    @FXML
    private ImageView pea2;
    @FXML
    private ImageView pea1;
    @FXML
    private ImageView lawnMower5;
    @FXML
    private Text countDown;

    private static final Integer WAVETIME = 20;
    private Integer timeSeconds = WAVETIME;
    private Timeline zombieAnimation;
    private Timeline counter;
    private ParallelTransition allTempTransitions;
    private PathTransition moveSun;

    private void animateZombie(Image moving, Image dying, int x, int y){
        ImageView zombie = new ImageView(moving);
        zombie.setX(x);
        zombie.setY(y);
        animationLayer.getChildren().addAll(zombie);

        PathTransition moveZombie = new PathTransition();
        Line zPath = new Line(zombie.getX(), zombie.getY()+zombie.getFitHeight()/2, zombie.getX()-500, zombie.getY()+zombie.getFitHeight()/2);
        moveZombie.setNode(zombie);
        moveZombie.setPath(zPath);
        moveZombie.setDuration(Duration.seconds(5));
        moveZombie.setOnFinished((e)-> {
            Image i = new Image("./images/zombie_normal_dying.gif");
            zombie.setImage(dying);
        });

        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.millis(300));
        pause.setOnFinished(e -> {
            animationLayer.getChildren().removeAll(zombie);
        });

        SequentialTransition s = new SequentialTransition(moveZombie,pause);
        s.play();
    }

    private void animateSunToken(){
        moveSun = new PathTransition();
        double x = sunToken.getX();
        double y = sunToken.getY();
        Line sPath = new Line(x, y+10, x, y+800);
        moveSun.setNode(sunToken);
        moveSun.setPath(sPath);
        moveSun.setDuration(Duration.seconds(5));
        moveSun.setCycleCount(1);
        moveSun.setDelay(Duration.seconds(3));

        moveSun.setOnFinished(e -> {
            double newX = ThreadLocalRandom.current().nextDouble(550);
            moveSun.setPath(new Line(newX, y+10, newX, y+800));
            moveSun.play();
        });
        moveSun.play();
    }

    private PathTransition animatePea(ImageView PEA){
        PathTransition movePea = new PathTransition();
        Line pPath = new Line(PEA.getX(), PEA.getY()+5, PEA.getX()+1000, PEA.getY()+5);
        movePea.setNode(PEA);
        movePea.setPath(pPath);
        movePea.setDuration(Duration.seconds(2));
        movePea.setCycleCount(Timeline.INDEFINITE);
        return movePea;
    }

    private PathTransition animateLawnMower(ImageView LM){
        PathTransition moveLM = new PathTransition();
        Line lmPath = new Line(LM.getX()+20, LM.getY()+30, LM.getX()+1400, LM.getY()+30);
        moveLM.setNode(LM);
        moveLM.setPath(lmPath);
        moveLM.setDuration(Duration.seconds(4));
        moveLM.setDelay(Duration.seconds(4));
        moveLM.setCycleCount(Timeline.INDEFINITE);
        return moveLM;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScaleTransition open  = new ScaleTransition(Duration.seconds(2), shade);
        open.setByX(80);
        open.setByY(75);

        zombieAnimation = new Timeline(new KeyFrame(Duration.seconds(6), (e)-> {
            animateZombie(new Image("./images/zombie_normal.gif"), new Image("./images/zombie_normal_dying.gif"), 1100, 325);
            animateZombie(new Image("./images/zombie_football.gif"), new Image("./images/zombie_football_dying.gif"), 1100, 535);

        }));
        zombieAnimation.setCycleCount(10);

        countDown.setText(timeSeconds.toString());
        counter = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            countDown.setText(timeSeconds.toString());
            timeSeconds--;
            if(timeSeconds < 0){
                countDown.setText("NEXT WAVE");
                if(timeSeconds < -3)
                    timeSeconds = WAVETIME;
            }
        }));
        counter.setCycleCount(Timeline.INDEFINITE);

        open.play();
        open.setOnFinished((e)-> {
            shade.setVisible(false);
            allTempTransitions = new ParallelTransition(animatePea(pea1), animatePea(pea2), animateLawnMower(lawnMower5));
            animateSunToken();
//            animatePea(pea1);
//            animatePea(pea2);
//            animateLawnMower(lawnMower5);
            allTempTransitions.play();
            zombieAnimation.play();
            counter.play();
        });

    }


    public void makeGlow(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(new Glow(0.5));
    }

    public void removeEffect(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);
    }

    public void makeDark(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(new InnerShadow());
    }

    public void pauseGame(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);


        try {
            Stage primaryStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
            Parent next = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("./FXMLs/pausemenu.fxml")));
//            zombieAnimation.pause();
//            moveSun.pause();
//            counter.pause();
//            allTempTransitions.pause();

            Main.saveScene(primaryStage.getScene());
            primaryStage.setScene(new Scene(next));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    public void resumeAnimations(){
//        zombieAnimation.play();
//        moveSun.play();
//        counter.play();
//        allTempTransitions.play();
//    }

}

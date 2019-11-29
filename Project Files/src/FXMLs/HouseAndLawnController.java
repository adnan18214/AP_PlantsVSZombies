package FXMLs;

import allClasses.BeetRoot;
import allClasses.Main;
import allClasses.PeaShooter;
import allClasses.shooterPlant;
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
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private AnchorPane animationLayer;
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
    private boolean dragSuccessful;
    private boolean shovelActivated;
    @FXML
    private Text sunTokenCount;
    @FXML
    private ImageView shovel;
    @FXML
    private GridPane gardenGRID;

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
        Image sun = new Image("./images/sunToken.gif");

        ImageView sunToken = new ImageView(sun);
        double x = ThreadLocalRandom.current().nextDouble(200,850);
        double y = 105;
        sunToken.setX(x);
        sunToken.setY(y);
        sunToken.setFitWidth(64);
        sunToken.setFitHeight(58);

        sunToken.setOnMouseClicked((e)-> {
            sunToken.setImage(null);
            int count = Integer.parseInt(sunTokenCount.getText());
            count += 50;
            sunTokenCount.setText(Integer.toString(count));
        });

        Line sPath = new Line(x, y+10, x, y+800);
        moveSun.setNode(sunToken);
        moveSun.setPath(sPath);
        moveSun.setDuration(Duration.seconds(5));
        moveSun.setCycleCount(1);
        moveSun.setDelay(Duration.seconds(3));
        animationLayer.getChildren().add(sunToken);

        moveSun.setOnFinished(e -> {
            double newX = ThreadLocalRandom.current().nextDouble(200,850);
            moveSun.setPath(new Line(newX, y+10, newX, y+800));
            moveSun.setDelay(Duration.seconds(ThreadLocalRandom.current().nextInt(2,6)));
            if(sunToken.getImage() == null)
                sunToken.setImage(sun);
            moveSun.play();
        });
        moveSun.play();
    }

    private void animateLawnMower(ImageView LM){
        PathTransition moveLM = new PathTransition();
        Line lmPath = new Line(LM.getX()+20, LM.getY()+30, LM.getX()+1400, LM.getY()+30);
        moveLM.setNode(LM);
        moveLM.setPath(lmPath);
        moveLM.setDuration(Duration.seconds(4));
//        moveLM.setDelay(Duration.seconds(4));
        moveLM.setCycleCount(1);
        moveLM.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shovelActivated = false;
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
            allTempTransitions = new ParallelTransition();
            animateSunToken();
            allTempTransitions.play();
            zombieAnimation.play();
            counter.play();
        });

    }


    public void makeGlow(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        if(!button.getImage().getUrl().contains("inactive"))
            button.setEffect(new Glow(0.5));
    }

    public void removeEffect(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);
    }

    public void makeDark(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        if(!button.getImage().getUrl().contains("inactive"))
            button.setEffect(new InnerShadow());
    }

    public void pauseGame(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);


        try {
            Stage primaryStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
            Parent next = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("./FXMLs/pausemenu.fxml")));
            zombieAnimation.pause();
            moveSun.pause();
            counter.pause();
            allTempTransitions.pause();

            Main.saveScene(primaryStage.getScene());
            primaryStage.setScene(new Scene(next));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void resumeAnimations(){
        zombieAnimation.play();
        moveSun.play();
        counter.play();
        allTempTransitions.play();
    }

    @FXML
    private void manageDragDetected(MouseEvent mouseEvent) {
        dragSuccessful = false;
        ImageView source = (ImageView) mouseEvent.getSource();
        if(source.getImage().getUrl().contains("shovel")){
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(source.getImage());
            cbContent.putString(source.getImage().getUrl());
            db.setContent(cbContent);
        }else if(!source.getImage().getUrl().contains("inactive")) {
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(source.getImage());
            cbContent.putString(source.getImage().getUrl());
            db.setContent(cbContent);
        }
        mouseEvent.consume();
    }

    @FXML
    private void manageDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasImage()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void manageDragDrop(DragEvent dragEvent) {
        if(dragEvent.getDragboard().getString().contains("shovel")){
            ImageView target = (ImageView) dragEvent.getTarget();
            if(gardenGRID.getChildren().contains(target)){
                target.setImage(null);
            }
        } else {
            String location = dragEvent.getDragboard().getString();
//            location = location.replace("active_", "");
//            location = location.replace("png", "gif");
//            Image CharacterGIF = new Image(location);
//            ImageView target = (ImageView) dragEvent.getTarget();
//            target.setImage(CharacterGIF);
            if(location.contains("peashooter") || location.contains("beetroot")){
                shooterPlant p;
                if(location.contains("peashooter"))
                    p = new PeaShooter();
                else
                    p = new BeetRoot();

                ImageView target = (ImageView) dragEvent.getTarget();
                target.setImage(p.getAliveGIF());
                addToAnimationGroup(p.shootBullets(target));
            } else {
                // POTATO and SUNFLOWER
            }
            dragSuccessful = true;
//
//            if (location.contains("peashooter") || location.contains("beetroot")) {
//                ImageView p;
//                if (location.contains("peashooter"))
//                    p = new ImageView(new Image("./images/Pea.png"));
//                else
//                    p = new ImageView(new Image("./images/beetbullet.png"));
//
//                int r = GridPane.getRowIndex(target);
//                int c = GridPane.getColumnIndex(target);
//                GridPane g = (GridPane) target.getParent();
//
//                p.setX(g.getLayoutX() + (c - 1) * 90 + 69);
//                if (location.contains("peashooter"))
//                    p.setY(g.getLayoutY() + (r - 1) * 109 + 37);
//                else
//                    p.setY(g.getLayoutY() + (r - 1) * 109 + 62);
//                ((AnchorPane) target.getParent().getParent()).getChildren().add(p);
//                PathTransition pAnimate = animatePea(p);
//                pAnimate.play();
//                allTempTransitions.getChildren().add(pAnimate);
//            }
        }
    }

    @FXML
    private void manageDragDone(DragEvent dragEvent) {
        if(dragSuccessful){
            String url = ((ImageView) dragEvent.getSource()).getImage().getUrl();
            url = url.replace("active", "inactive");
            ((ImageView) dragEvent.getSource()).setImage(new Image(url));
            dragSuccessful = false;
        }
    }

    @FXML
    private void testLawnMower(MouseEvent mouseEvent) {
        animateLawnMower((ImageView) mouseEvent.getSource());
    }

    @FXML
    private void activateShovelAction(MouseEvent mouseEvent) {
        shovelActivated = true;

    }

    private void addToAnimationGroup(Animation a){
        allTempTransitions.getChildren().add(a);
    }
}

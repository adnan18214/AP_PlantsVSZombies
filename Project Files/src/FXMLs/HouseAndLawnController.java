package FXMLs;

import allClasses.*;
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
    private Lawn lawn;
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
        double x = ThreadLocalRandom.current().nextDouble(200,850);
        double y = 105;
        SunToken s = new SunToken(sunTokenCount);
        ImageView sunToken = s.getSuntoken(x,y);

        Line sPath = new Line(x+32, y+29, x+32, y+800);
        moveSun.setNode(sunToken);
        moveSun.setPath(sPath);
        moveSun.setDuration(Duration.seconds(5));
        moveSun.setCycleCount(1);
        moveSun.setDelay(Duration.seconds(3));
        animationLayer.getChildren().add(sunToken);
        sunToken.setOpacity(0);

        FadeTransition appearToken = new FadeTransition(Duration.seconds(2), sunToken);
        appearToken.setToValue(1);
        appearToken.setCycleCount(1);
        appearToken.play();

        moveSun.setOnFinished(e -> {
            double newX = ThreadLocalRandom.current().nextDouble(200,850);
            moveSun.setPath(new Line(newX, y+10, newX, y+800));
            moveSun.setDelay(Duration.seconds(ThreadLocalRandom.current().nextInt(5,10)));
            if(sunToken.getImage() == null)
                sunToken.setImage(s.getSunImage());
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
        lawn = Lawn.getLawn();

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
        if(source.getImage().getUrl().contains("shovel") || !source.getImage().getUrl().contains("inactive")){
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(source.getImage());
            cbContent.putString(source.getImage().getUrl());
            db.setContent(cbContent);
        }
//        else if()) {
//            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
//
//            ClipboardContent cbContent = new ClipboardContent();
//            cbContent.putImage(source.getImage());
//            cbContent.putString(source.getImage().getUrl());
//            db.setContent(cbContent);
//        }
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
        ImageView target = (ImageView) dragEvent.getTarget();
        if(dragEvent.getDragboard().getString().contains("shovel")){
            if(gardenGRID.getChildren().contains(target)){
                target.setImage(null);
                Plant p = lawn.getPlant(GridPane.getColumnIndex(target), GridPane.getRowIndex(target));
                lawn.removePlant(p);
                if(p instanceof shooterPlant){
                    removeFromAnimationGroup(((shooterPlant) p).getAnimation());
                } else if(p instanceof SunFlower){
                    removeFromAnimationGroup(((SunFlower) p).getAnimation());
                }
                p.killPlant();
            }
        } else {
            String location = dragEvent.getDragboard().getString();
            if(location.contains("peashooter") || location.contains("beetroot")){
                shooterPlant p;
                if(location.contains("peashooter"))
                    p = new PeaShooter(GridPane.getColumnIndex(target), GridPane.getRowIndex(target));
                else
                    p = new BeetRoot(GridPane.getColumnIndex(target), GridPane.getRowIndex(target));

                target.setImage(p.getAliveGIF());
                addToAnimationGroup(p.shootBullets(target));
                lawn.addPlant((Plant) p);
//                ImageView x = p.getBullet();
//                new AnimationTimer() {
//                    @Override
//                    public void handle(long now){
//                        System.out.println(x.getBoundsInParent());
//                    }
//                }.start();

            } else {
                if(location.contains("walnut")){
                    Walnut w = new Walnut(GridPane.getColumnIndex(target), GridPane.getRowIndex(target));
                    target.setImage(w.getFullHealthGIF());
                    lawn.addPlant(w);
                } else if(location.contains("sunflower")){
                    SunFlower s = new SunFlower(GridPane.getColumnIndex(target), GridPane.getRowIndex(target), sunTokenCount, target);
                    target.setImage(s.getAliveGIF());
                    SequentialTransition tokengen = s.generateTokens(sunTokenCount);
                    tokengen.play();
//                    addToAnimationGroup(tokengen);
                    lawn.addPlant(s);
                }
            }
            dragSuccessful = true;
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

    private void removeFromAnimationGroup(Animation a){
        allTempTransitions.getChildren().remove(a);
    }
}

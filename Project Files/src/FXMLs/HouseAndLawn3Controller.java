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
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HouseAndLawn3Controller extends HouseAndLawnParent implements Initializable, Serializable {
    @FXML
    private ImageView shade;
    @FXML
    private AnchorPane animationLayer;
    @FXML
    private ImageView lawnMower5;
    @FXML
    private ImageView lawnMower4;
    @FXML
    private ImageView lawnMower3;
    @FXML
    private ImageView lawnMower2;
    @FXML
    private ImageView lawnMower1;
    @FXML
    private Text countDown;
    @FXML
    private ImageView peaShooter;
    @FXML
    private ImageView sunFlower;
    @FXML
    private ImageView walNut;

    private static final Integer WAVETIME = 8;
    private Integer timeSeconds = WAVETIME;
    private Timeline zombieAnimation;
    private Timeline counter;
    private PathTransition moveSun;
    private boolean dragSuccessful;
    private boolean shovelActivated;
    private Lawn lawn;
    private ArrayList<Plant> market = new ArrayList<Plant>();
    private SunToken s;

    @FXML
    private Text sunTokenCount;
    @FXML
    private ImageView shovel;
    @FXML
    private GridPane gardenGRID;
    public ArrayList<Integer> shuffle = new ArrayList<Integer>();
    public Random rand;
    @FXML
    private ImageView house;


    public HouseAndLawn3Controller()
    {
        level = 3;
        allTempTransitions = new ParallelTransition();
        super.setAllTempTransitions(allTempTransitions);
        this.market.add(new PeaShooter(0,0, null, null));
        this.market.add(new SunFlower(0,0, null, null, null));
        this.market.add(new Walnut(0,0,null,null));
        shuffle.add(225);
        shuffle.add(325);
        shuffle.add(435);
        shuffle.add(535);
        shuffle.add(650);
        rand = new Random();
    }

    private void animateZombie(DrawableZombie zImage, int x){

        ImageView zombie = new ImageView(zImage.getAliveGIF());
        zombie.setX(x);

        int p = rand.nextInt(5);
        zombie.setY(shuffle.get(p));
        Zombie z = new Zombie(zombie, zombie.getX(), zombie.getY(), p+1, animationLayer, zImage.getHealth());

        animationLayer.getChildren().addAll(zombie);
        lawn.addZombie(z);

        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.millis(300));
        pause.setOnFinished(e -> {
            animationLayer.getChildren().removeAll(zombie);
        });

        PathTransition moveZombie = new PathTransition();
        Line zPath = new Line(zombie.getX(), zombie.getY()+zombie.getFitHeight()/2, zombie.getX()-1000, zombie.getY()+zombie.getFitHeight()/2);
        moveZombie.setNode(zombie);
        moveZombie.setPath(zPath);
        moveZombie.setDuration(Duration.seconds(25));
        moveZombie.setOnFinished((e)-> {
            Image i = new Image("./images/zombie_normal_dying.gif");
            zombie.setImage(zImage.getDyingGIF());
            lawn.removeZombie(z);
            pause.play();
        });

        moveZombie.play();
        z.setMovement(moveZombie);
    }

    private void animateSunToken(){
        moveSun = new PathTransition();
        double x = ThreadLocalRandom.current().nextDouble(200,850);
        double y = 105;
        s = new SunToken(sunTokenCount);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AtomicInteger count = new AtomicInteger();
        AtomicReference<Boolean> flag2 = new AtomicReference<>(true);

        shovelActivated = false;
        lawn = Lawn.getLawn();

        ScaleTransition open  = new ScaleTransition(Duration.seconds(2), shade);
        open.setByX(80);
        open.setByY(75);

        lawn.addLawnMower(lawnMower1, 1);
        lawn.addLawnMower(lawnMower2, 2);
        lawn.addLawnMower(lawnMower3, 3);
        lawn.addLawnMower(lawnMower4, 4);
        lawn.addLawnMower(lawnMower5, 5);

        zombieAnimation = new Timeline(new KeyFrame(Duration.seconds(10), (e)-> {
            count.getAndIncrement();
            int sel = rand.nextInt((2));
            if (sel == 0) {
                animateZombie(new LocalZombie(), 1100);
            }
            else {
                animateZombie(new FootballZombie(), 1100);
            }
        }));
        zombieAnimation.setCycleCount(15);

        countDown.setText(timeSeconds.toString());
        counter = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            update();
            countDown.setText(timeSeconds.toString());
            timeSeconds--;
            if(timeSeconds < 0){
                countDown.setText("NEXT WAVE");
                if(timeSeconds < -3)
                    timeSeconds = WAVETIME;
            }
            flag2.set(true);
            for(int i = 1; i <= 5; i++){
                ArrayList<Zombie> zombies = lawn.getZombies(i);
                if(!zombies.isEmpty()){
                    flag2.set(false);
                    if(house.getBoundsInParent().intersects(zombies.get(0).getZombieIV().getBoundsInParent())){
                        stopAnimations();
                        youLostGame();
                        break;
                    }
                }
            }
            if (count.get() == 15 && flag2.get()) {
                stopAnimations();
                youWin();
            }
        }));
        counter.setCycleCount(Timeline.INDEFINITE);

        open.play();
        open.setOnFinished((e)-> {
            shade.setVisible(false);
            animateSunToken();
            allTempTransitions.play();
            zombieAnimation.play();
            counter.play();

        });
    }

    public void youWin()
    {
        ScaleTransition close  = new ScaleTransition(Duration.seconds(1), shade);
        close.setByX(-78);
        close.setByY(-73);
        shade.toFront();
        shade.setVisible(true);
        close.play();

        close.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/winLevel.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void update()
    {
        for (Plant plant : market) {
            Image currentStatus;
            if ((Integer.parseInt(sunTokenCount.getText())) >= plant.getCost())
                currentStatus = new Image(plant.getActiveUrl());
            else
                currentStatus = new Image(plant.getInactiveUrl());

            if (plant.getActiveUrl().contains("sunflower")) {
                sunFlower.setImage(currentStatus);
            } else if (plant.getActiveUrl().contains("peashooter")) {
                peaShooter.setImage(currentStatus);
            } else if (plant.getActiveUrl().contains("walnut")) {
                walNut.setImage(currentStatus);
            }
        }

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

    public void stopAnimations(){
        zombieAnimation.stop();
        moveSun.stop();
        counter.stop();
        allTempTransitions.stop();
    }

    private void youLostGame(){
        ScaleTransition close  = new ScaleTransition(Duration.seconds(1), shade);
        close.setByX(-78);
        close.setByY(-73);
        shade.toFront();
        shade.setVisible(true);
        close.play();

        close.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/gameOver.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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
            // Shovel Action

            if(gardenGRID.getChildren().contains(target)){
                // If dragged on a plant

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
            // Plant a new plant

            String location = dragEvent.getDragboard().getString();
            if(location.contains("peashooter")){
                shooterPlant p;
                p = new PeaShooter(GridPane.getColumnIndex(target), GridPane.getRowIndex(target), lawn.getZombies(GridPane.getRowIndex(target)), target);

                target.setImage(p.getAliveGIF());
                PathTransition movingBullet = p.shootBullets(target);
                ImageView Bullet = p.getBullet();

                // Shoot only when zombie present
                movingBullet.setOnFinished(e->{
                    removeFromAnimationGroup(movingBullet);
                    if(((Plant) p).isZombieAttacking()){
                        ((Plant) p).detectCollisions(true);
                        movingBullet.setNode(Bullet);
                        Bullet.setDisable(false);
                        Bullet.setVisible(true);
                    } else {
                        ((Plant) p).detectCollisions(false);
                        movingBullet.setNode(new ImageView());
                        Bullet.setDisable(true);
                        Bullet.setVisible(false);
                    }
                    movingBullet.play();
                    addToAnimationGroup(movingBullet);
                });
                addToAnimationGroup(movingBullet);
                lawn.addPlant((Plant) p);

            } else if(location.contains("walnut")){
                Walnut w = new Walnut(GridPane.getColumnIndex(target), GridPane.getRowIndex(target), lawn.getZombies(GridPane.getRowIndex(target)), target);
                target.setImage(w.getAliveGIF());
                ((Plant) w).detectCollisions(true);
                lawn.addPlant(w);

            } else if(location.contains("sunflower")){
                SunFlower s = new SunFlower(GridPane.getColumnIndex(target), GridPane.getRowIndex(target), sunTokenCount, target, lawn.getZombies(GridPane.getRowIndex(target)));
                target.setImage(s.getAliveGIF());
                ((Plant) s).detectCollisions(true);

                // Generate sun tokens
                SequentialTransition tokengen = s.generateTokens(sunTokenCount);
                tokengen.setOnFinished(e->{
                    tokengen.setDelay(Duration.seconds(ThreadLocalRandom.current().nextInt(5,8)));
                    if(s.getToken().getImage() == null)
                        s.getToken().setImage((new SunToken(sunTokenCount)).getSunImage());
                    tokengen.play();

                    if(((Plant) s).isZombieAttacking())
                        ((Plant) s).detectCollisions(true);
                    else
                        ((Plant) s).detectCollisions(false);
                });
                tokengen.play();
                lawn.addPlant(s);
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
            int suns = Integer.parseInt(sunTokenCount.getText());
            if (url.contains("peashooter"))
            {
                suns-=100;
                sunTokenCount.setText(Integer.toString(suns));
            }
            else if (url.contains("sunflower"))
            {
                suns-=50;
                sunTokenCount.setText(Integer.toString(suns));
            }
            else if (url.contains("walnut"))
            {
                suns-=25;
                sunTokenCount.setText(Integer.toString(suns));
            }
            dragSuccessful = false;
        }
    }

    @FXML
    private void activateShovelAction(MouseEvent mouseEvent) {
        shovelActivated = true;

    }

    @Override
    public int getSuntokenCount() {
        return Integer.parseInt(sunTokenCount.getText());
    }

    @Override
    public void setSuntokenCount(int s) {
        sunTokenCount.setText(Integer.toString(s));
    }
}

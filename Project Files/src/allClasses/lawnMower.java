package allClasses;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

public class lawnMower implements Serializable {
    private ImageView lawnMowerIV;
    private PathTransition moveLM;
    private ArrayList<Zombie> attackingZombies;
    private Timeline collider;
    private final Bounds LMBounds;

    public lawnMower(ImageView LMIV, ArrayList zombies){

        lawnMowerIV = LMIV;
        attackingZombies = zombies;
        animationLawnMower(lawnMowerIV);
        LMBounds = lawnMowerIV.getBoundsInParent();

        collider = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if(isZombieAttacking()) {
                Zombie frontZombie = attackingZombies.get(0);
                if(LMBounds.intersects(frontZombie.getZombieIV().getBoundsInParent())){
                    triggerLM();
                }
            }
        }));
        collider.setCycleCount(Animation.INDEFINITE);
        collider.play();
    }

    private void animationLawnMower(ImageView LM){
        moveLM = new PathTransition();
        Line lmPath = new Line(LM.getX()+20, LM.getY()+30, LM.getX()+1400, LM.getY()+30);
        moveLM.setNode(LM);
        moveLM.setPath(lmPath);
        moveLM.setDuration(Duration.seconds(8));
        moveLM.setCycleCount(1);
        moveLM.setOnFinished(e-> {
            collider.stop();
        });
    }

    public void triggerLM(){
        moveLM.play();
        while(!attackingZombies.isEmpty()){
            attackingZombies.get(0).killZombie();
        }
    }

    private boolean isZombieAttacking(){
        return !attackingZombies.isEmpty();
    }

}

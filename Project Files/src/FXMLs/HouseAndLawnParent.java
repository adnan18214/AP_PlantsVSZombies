package FXMLs;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;

import java.io.Serializable;

public class HouseAndLawnParent implements Serializable {
    protected static ParallelTransition allTempTransitions;

    public static void addToAnimationGroup(Animation a){
        allTempTransitions.getChildren().add(a);
    }

    public static void removeFromAnimationGroup(Animation a){
        allTempTransitions.getChildren().remove(a);
    }

    public void setAllTempTransitions(ParallelTransition PT){
        allTempTransitions = PT;
    }

    public abstract void resumeAnimations();
    public abstract void stopAnimations();
}

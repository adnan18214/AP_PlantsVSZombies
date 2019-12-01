package FXMLs;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;

public abstract class HouseAndLawnParent {
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

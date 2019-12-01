package FXMLs;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;

import java.io.Serializable;

public abstract class HouseAndLawnParent implements Serializable {
    protected int level;
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

    protected int getLevel() {
        return level;
    }

    public abstract void resumeAnimations();
    public abstract void stopAnimations();
    public abstract int getSuntokenCount();
    public abstract void setSuntokenCount(int s);
}

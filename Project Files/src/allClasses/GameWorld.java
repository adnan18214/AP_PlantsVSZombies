package allClasses;

import javafx.scene.Scene;

import java.io.Serializable;

public class GameWorld implements Serializable {
    private Lawn lawn;
    private int suntokenCount;
    private int level;

    public Lawn getLawn() {
        return lawn;
    }

    public void setLawn(Lawn lawn) {
        this.lawn = lawn;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getSuntokenCount() {
        return suntokenCount;
    }

    public void setSuntokenCount(int suntokenCount) {
        this.suntokenCount = suntokenCount;
    }
}

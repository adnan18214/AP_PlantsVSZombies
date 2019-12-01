package allClasses;

import javafx.scene.Scene;

import java.io.Serializable;

public class GameWorld implements Serializable {
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;

    }
}

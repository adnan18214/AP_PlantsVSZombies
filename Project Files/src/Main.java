import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./FXMLs/selectPlayerMenu.fxml"));
        primaryStage.setTitle("Plants VS Zombies - AP Project");
        primaryStage.setScene(new Scene(root));
        FadeTransition fadein = new FadeTransition(Duration.seconds(3),root);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.setCycleCount(1);
        fadein.play();
        FadeTransition fadeout = new FadeTransition(Duration.seconds(1.5),root);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.setCycleCount(1);
        fadein.setOnFinished((e)-> {
        fadeout.play();
                });
        fadeout.setOnFinished((e)-> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("./FXMLs/selectPlayerMenu.fxml"));
                primaryStage.setScene(new Scene(root2));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        primaryStage.show();
    }
}

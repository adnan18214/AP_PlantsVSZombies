package gameRunner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static Stack<Scene> scenes;

    public static void saveScene(Scene s){
        scenes.push(s);
    }

    public static Scene getLastScene(){
        return scenes.pop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        scenes = new Stack<>();
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLs/WelcomeLogo.fxml"));
        primaryStage.setTitle("Plants VS Zombies - AP Project");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}

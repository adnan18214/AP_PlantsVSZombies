package FXMLs;

import gameRunner.Main;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class pausemenuController implements Initializable {

    @FXML
    private ImageView shade;
    
    public void makeGlow(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);
    }


    public void removeEffect(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);
    }

    public void makeDark(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(new InnerShadow());
    }

    public void goToMenu(MouseEvent mouseEvent) {
    }

    public void gotoMenu(MouseEvent mouseEvent) {
        ScaleTransition close  = new ScaleTransition(Duration.seconds(1), shade);
        close.setByX(-78);
        close.setByY(-73);
        shade.setVisible(true);
        close.play();

        close.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/mainMenu.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
                Main.getLastScene();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void gotoSettings(MouseEvent mouseEvent) {
    }

    public void resumeGame(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.setScene(Main.getLastScene());
        FXMLLoader l = (FXMLLoader) primaryStage.getScene().getUserData();
        HouseAndLawnController h = l.getController();
        h.resumeAnimations();
    }

    public void restartGame(MouseEvent mouseEvent) {
        ScaleTransition close  = new ScaleTransition(Duration.seconds(1), shade);
        close.setByX(-78);
        close.setByY(-73);
        shade.setVisible(true);
        close.play();

        close.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
                Main.getLastScene();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shade.setScaleX(80);
        shade.setScaleY(75);
        shade.setVisible(false);
    }
}



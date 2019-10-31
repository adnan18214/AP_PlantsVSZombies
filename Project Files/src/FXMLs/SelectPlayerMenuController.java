package FXMLs;

import gameRunner.Main;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectPlayerMenuController implements Initializable {
    @FXML
    private ImageView existingPlayer;
    @FXML
    private ImageView playGuest;
    @FXML
    private ImageView newPlayer;
    @FXML
    private ImageView exitButton;
    @FXML
    private ImageView shade;

    @FXML
    private void makeGlow(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(new Glow(0.5));
    }

    @FXML
    private void removeEffect(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);
    }

    @FXML
    private void makeDark(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(new InnerShadow());
    }

    @FXML
    private void endGame(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScaleTransition open  = new ScaleTransition(Duration.seconds(2), shade);
        open.setByX(80);
        open.setByY(75);
        open.play();
        open.setOnFinished((e)-> {
            shade.setVisible(false);
        });
    }

    @FXML
    private void enterPlayerName(MouseEvent mouseEvent) {
        ImageView button = (ImageView) mouseEvent.getSource();
        button.setEffect(null);

        try {
            Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/enterName.fxml"));
            Stage primaryStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
            Main.saveScene(primaryStage.getScene());
            primaryStage.setScene(new Scene(next));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

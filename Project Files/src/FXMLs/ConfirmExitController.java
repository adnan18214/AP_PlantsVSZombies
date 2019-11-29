package FXMLs;

import allClasses.Main;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmExitController implements Initializable {
    @FXML
    private ImageView shade;

    @FXML
    private void exitGame(ActionEvent actionEvent) {
        ScaleTransition close  = new ScaleTransition(Duration.seconds(1), shade);
        close.setByX(-78);
        close.setByY(-73);
        shade.setVisible(true);
        close.play();

        close.setOnFinished((e)-> {
            Platform.exit();
        });
    }

    @FXML
    private void goBackToLogin(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.setScene(Main.getLastScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shade.setScaleX(80);
        shade.setScaleY(75);
        shade.setVisible(false);
    }
}

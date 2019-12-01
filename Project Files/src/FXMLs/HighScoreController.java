package FXMLs;

import allClasses.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.Serializable;

public class HighScoreController implements Serializable {
    @FXML
    private void goBackToMainMenu(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.setScene(Main.getLastScene());
    }
}

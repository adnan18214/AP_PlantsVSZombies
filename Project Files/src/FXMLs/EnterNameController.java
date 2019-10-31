package FXMLs;
import gameRunner.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EnterNameController {
    @FXML
    private void goBackToLogin(ActionEvent actionEvent) {
        try {
            Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/selectPlayerMenu.fxml"));
            Node source = (Node) actionEvent.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();
            primaryStage.setScene(Main.getLastScene());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

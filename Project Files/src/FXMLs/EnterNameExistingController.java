package FXMLs;
import allClasses.Main;

import allClasses.Serializer;
import allClasses.User;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class EnterNameExistingController implements Initializable, Serializable {
    @FXML
    private ImageView shade;
    @FXML
    private TextField nameText;
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

    @FXML
    private void goToMainMenu(ActionEvent actionEvent) {
        String userName = nameText.getText();
        User user = null;
        try {
            user = Serializer.deserialize(userName);
        } catch (FileNotFoundException e){
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/UserNotFound.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        Main.setCurrentUser(user);
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}

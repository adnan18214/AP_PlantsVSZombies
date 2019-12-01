package FXMLs;

import allClasses.Main;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
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
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class SavedGamesController implements Initializable , Serializable {
    @FXML
    private ImageView shade;
    @FXML
    private ImageView saveGame1;
    @FXML
    private ImageView saveGame2;
    @FXML
    private ImageView saveGame3;

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
    private void goBackToMainMenu(ActionEvent actionEvent) {
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

    @FXML
    private void sGame1(MouseEvent mouseEvent)
    {
        ScaleTransition close1  = new ScaleTransition(Duration.seconds(1), shade);
        close1.setByX(-78);
        close1.setByY(-73);
        shade.setVisible(true);
        close1.play();

        close1.setOnFinished((e)-> {
            Stage primaryStage = (Stage) shade.getScene().getWindow();
            try {
                Scene currentScene = Main.getCurrentUser().getGame(0).getScene();
                FXMLLoader l = (FXMLLoader) currentScene.getUserData();
                HouseAndLawnParent h = l.getController();
                h.resumeAnimations();

                primaryStage.setScene(currentScene);
            } catch (ArrayIndexOutOfBoundsException f){
                // No game saved
            }
        });
    }

    @FXML
    private void sGame2(MouseEvent mouseEvent)
    {
        ScaleTransition close2  = new ScaleTransition(Duration.seconds(1), shade);
        close2.setByX(-78);
        close2.setByY(-73);
        shade.setVisible(true);
        close2.play();

        close2.setOnFinished((e)-> {
            Stage primaryStage = (Stage) shade.getScene().getWindow();
            try {
                Scene currentScene = Main.getCurrentUser().getGame(1).getScene();
                FXMLLoader l = (FXMLLoader) currentScene.getUserData();
                HouseAndLawnParent h = l.getController();
                h.resumeAnimations();

                primaryStage.setScene(currentScene);
            } catch (ArrayIndexOutOfBoundsException f){
                // No game saved
            }
        });
    }

    @FXML
    private void sGame3(MouseEvent mouseEvent)
    {
        ScaleTransition close3  = new ScaleTransition(Duration.seconds(1), shade);
        close3.setByX(-78);
        close3.setByY(-73);
        shade.setVisible(true);
        close3.play();

        close3.setOnFinished((e)-> {
            Stage primaryStage = (Stage) shade.getScene().getWindow();
            try {
                Scene currentScene = Main.getCurrentUser().getGame(2).getScene();
                FXMLLoader l = (FXMLLoader) currentScene.getUserData();
                HouseAndLawnParent h = l.getController();
                h.resumeAnimations();

                primaryStage.setScene(currentScene);
            } catch (ArrayIndexOutOfBoundsException f){
                // No game saved
            }
        });
    }
}

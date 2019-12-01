package FXMLs;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable, Serializable
    {
        @FXML
        private ImageView shade;

        public GameOverController() {
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> {
                goToMainMenu();
            });
            pause.play();
            pause.setCycleCount(1);
        }

        private void goToMainMenu() {
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
    }

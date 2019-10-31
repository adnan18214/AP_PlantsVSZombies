package FXMLs;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeLogoController implements Initializable {
    @FXML
    private ImageView shade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ScaleTransition open  = new ScaleTransition(Duration.seconds(5), shade);
        open.setByX(80);
        open.setByY(75);
        ScaleTransition close  = new ScaleTransition(Duration.seconds(1), shade);
        close.setByX(-80);
        close.setByY(-75);

        open.play();
        open.setOnFinished((e) -> {
            close.play();
        });
        close.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/selectPlayerMenu.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}

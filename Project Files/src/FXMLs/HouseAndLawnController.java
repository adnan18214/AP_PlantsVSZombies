package FXMLs;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HouseAndLawnController implements Initializable {
    @FXML
    private ImageView shade;

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

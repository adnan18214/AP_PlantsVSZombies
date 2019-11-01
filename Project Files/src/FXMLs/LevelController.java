package FXMLs;

import gameRunner.Main;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelController implements Initializable{
    @FXML
    private ImageView level1;
    @FXML
    private ImageView level2;
    @FXML
    private ImageView level3;
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

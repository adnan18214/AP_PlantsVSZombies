package FXMLs;

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
    private void level1(MouseEvent mouseEvent)
    {
        ScaleTransition close1  = new ScaleTransition(Duration.seconds(1), shade);
        close1.setByX(-78);
        close1.setByY(-73);
        shade.setVisible(true);
        close1.play();

        close1.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void level2(MouseEvent mouseEvent)
    {
        ScaleTransition close2  = new ScaleTransition(Duration.seconds(1), shade);
        close2.setByX(-78);
        close2.setByY(-73);
        shade.setVisible(true);
        close2.play();

        close2.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn2.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void level3(MouseEvent mouseEvent)
    {
        ScaleTransition close3  = new ScaleTransition(Duration.seconds(1), shade);
        close3.setByX(-78);
        close3.setByY(-73);
        shade.setVisible(true);
        close3.play();

        close3.setOnFinished((e)-> {
            try {
                Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn3.fxml"));
                Stage primaryStage = (Stage) shade.getScene().getWindow();
                primaryStage.setScene(new Scene(next));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}

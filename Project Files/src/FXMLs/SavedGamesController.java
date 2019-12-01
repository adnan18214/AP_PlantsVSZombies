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
                if(Main.getCurrentUser() == null)
                    throw new IOException();
                int level = Main.getCurrentUser().getGame(0).getLevel();
                FXMLLoader next = null;
                switch(level){
                    case 1: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn.fxml")); break;
                    case 2: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn2.fxml")); break;
                    case 3: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn3.fxml")); break;
                    case 4: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn4.fxml")); break;
                    case 5: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn5.fxml")); break;
                }
                Parent nParent = next.load();
                Scene nextScene = new Scene(nParent);
                next.<HouseAndLawnParent>getController().setSuntokenCount(Main.getCurrentUser().getGame(0).getSuntokenCount());
                primaryStage.setScene(nextScene);
            } catch (ArrayIndexOutOfBoundsException | IOException f){
                try {
                    Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/gameNotFound.fxml"));
                    primaryStage.setScene(new Scene(next));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                if(Main.getCurrentUser() == null)
                    throw new IOException();
                int level = Main.getCurrentUser().getGame(1).getLevel();
                FXMLLoader next = null;
                switch(level){
                    case 1: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn.fxml")); break;
                    case 2: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn2.fxml")); break;
                    case 3: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn3.fxml")); break;
                    case 4: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn4.fxml")); break;
                    case 5: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn5.fxml")); break;
                }
                Parent nParent = next.load();
                Scene nextScene = new Scene(nParent);
                next.<HouseAndLawnParent>getController().setSuntokenCount(Main.getCurrentUser().getGame(1).getSuntokenCount());
                primaryStage.setScene(nextScene);
            } catch (ArrayIndexOutOfBoundsException | IOException f){
                try {
                    Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/gameNotFound.fxml"));
                    primaryStage.setScene(new Scene(next));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                if(Main.getCurrentUser() == null)
                    throw new IOException();
                int level = Main.getCurrentUser().getGame(2).getLevel();
                FXMLLoader next = null;
                switch(level){
                    case 1: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn.fxml")); break;
                    case 2: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn2.fxml")); break;
                    case 3: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn3.fxml")); break;
                    case 4: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn4.fxml")); break;
                    case 5: next = new FXMLLoader(getClass().getClassLoader().getResource("./FXMLs/houseAndLawn5.fxml")); break;
                }
                Parent nParent = next.load();
                Scene nextScene = new Scene(nParent);
                next.<HouseAndLawnParent>getController().setSuntokenCount(Main.getCurrentUser().getGame(2).getSuntokenCount());
                primaryStage.setScene(nextScene);
            } catch (ArrayIndexOutOfBoundsException | IOException f){
                try {
                    Parent next = FXMLLoader.load(getClass().getClassLoader().getResource("./FXMLs/gameNotFound.fxml"));
                    primaryStage.setScene(new Scene(next));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

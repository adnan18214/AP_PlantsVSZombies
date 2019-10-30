package FXMLs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SelectPlayerMenuController {
    @FXML
    private ImageView existingPlayer;
    @FXML
    private ImageView playGuest;
    @FXML
    private ImageView newPlayer;
    @FXML
    private ImageView exitButton;

    @FXML
    private void makeGlow(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        i.setEffect(new Glow(0.5));
    }

    @FXML
    private void removeEffect(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        i.setEffect(null);
    }

    @FXML
    private void makeDark(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        i.setEffect(new InnerShadow());
    }

    @FXML
    private void endGame(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        i.setEffect(null);
        Platform.exit();
    }
}

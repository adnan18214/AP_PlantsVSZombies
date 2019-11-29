package allClasses;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class BeetRoot implements shooterPlant{
    private Image aliveGIF;
    private Image dyingGIF;
    private Image beetBullet;
    private PathTransition movingBullet;

    public BeetRoot(){
        aliveGIF = new Image("images/beetroot.gif");
        dyingGIF = new Image("images/beetroot_dying.gif");
        beetBullet = new Image("images/beetbullet.png");
    }

    public Image getAliveGIF() {
        return aliveGIF;
    }

    public Image getDyingGIF() {
        return dyingGIF;
    }

    public PathTransition shootBullets(ImageView target) {
        int row = GridPane.getRowIndex(target);
        int col = GridPane.getColumnIndex(target);
        GridPane g = (GridPane) target.getParent();
        ImageView b = new ImageView(beetBullet);

        b.setX(g.getLayoutX() + (col - 1) * 90 + 69);
        b.setY(g.getLayoutY() + (row - 1) * 109 + 62);

        ((AnchorPane) target.getParent().getParent()).getChildren().add(b);
        animateBeet(b);
        movingBullet.play();
        return movingBullet;
    }

    private void animateBeet(ImageView BEET){
        PathTransition moveBeet = new PathTransition();
        Line pPath = new Line(BEET.getX(), BEET.getY()+5, BEET.getX()+1000, BEET.getY()+5);
        moveBeet.setNode(BEET);
        moveBeet.setPath(pPath);
        moveBeet.setDuration(Duration.seconds(2));
        moveBeet.setCycleCount(Timeline.INDEFINITE);
        movingBullet = moveBeet;
    }
}

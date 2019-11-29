package allClasses;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class PeaShooter implements shooterPlant{
    private Image aliveGIF;
    private Image dyingGIF;
    private Image peaBullet;
    private PathTransition movingBullet;

    public PeaShooter(){
        aliveGIF = new Image("images/peashooter.gif");
        dyingGIF = new Image("images/peashooter_dying.gif");
        peaBullet = new Image("images/Pea.png");
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
        ImageView p = new ImageView(peaBullet);

        p.setX(g.getLayoutX() + (col - 1) * 90 + 69);
        p.setY(g.getLayoutY() + (row - 1) * 109 + 37);

        ((AnchorPane) target.getParent().getParent()).getChildren().add(p);
        animatePea(p);
        movingBullet.play();
        return movingBullet;
    }

    private void animatePea(ImageView PEA){
        PathTransition movePea = new PathTransition();
        Line pPath = new Line(PEA.getX(), PEA.getY()+5, PEA.getX()+1000, PEA.getY()+5);
        movePea.setNode(PEA);
        movePea.setPath(pPath);
        movePea.setDuration(Duration.seconds(2));
        movePea.setCycleCount(Timeline.INDEFINITE);
        movingBullet = movePea;
    }
}

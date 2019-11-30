package allClasses;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class BeetRoot extends Plant implements shooterPlant{
    private Image aliveGIF;
    private Image dyingGIF;
    private Image beetBullet;
    private PathTransition movingBullet;
    private ImageView bulletIV;
    private AnchorPane pane;

    public BeetRoot(int x, int y){
        super(100, x, y, "images/active_beetroot.png","images/inactive_beetroot.png");
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

    /**
     * Creates animated bullets.
     * @param target represents ImageView where plant is dropped.
     * @return movingBullet represents the animation.
     */
    public PathTransition shootBullets(ImageView target) {
        int row = GridPane.getRowIndex(target);
        int col = GridPane.getColumnIndex(target);
        GridPane g = (GridPane) target.getParent();
        bulletIV = new ImageView(beetBullet);

        bulletIV.setX(g.getLayoutX() + (col - 1) * 90 + 69);
        bulletIV.setY(g.getLayoutY() + (row - 1) * 109 + 62);

        pane = (AnchorPane) target.getParent().getParent();
        pane.getChildren().add(bulletIV);
        animateBeet(bulletIV);
        movingBullet.play();
        return movingBullet;
    }

    /**
     * Sets the default PathTransition animation of bullet moving.
     * @param BEET represents the bullet image.
     */
    private void animateBeet(ImageView BEET){
        PathTransition moveBeet = new PathTransition();
        Line pPath = new Line(BEET.getX(), BEET.getY()+5, BEET.getX()+1000, BEET.getY()+5);
        moveBeet.setNode(BEET);
        moveBeet.setPath(pPath);
        moveBeet.setDuration(Duration.seconds(2));
        moveBeet.setCycleCount(Timeline.INDEFINITE);
        movingBullet = moveBeet;
    }

    @Override
    public void killPlant() {
        movingBullet.stop();
        pane.getChildren().remove(bulletIV);
    }

    public PathTransition getAnimation() {
        return movingBullet;
    }

    public ImageView getBullet() {
        return bulletIV;
    }
}

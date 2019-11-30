package allClasses;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class PeaShooter extends Plant implements shooterPlant{
    private Image aliveGIF;
    private Image dyingGIF;
    private Image peaBullet;
    private ImageView bulletIV;
    private AnchorPane pane;
    private PathTransition movingBullet;

    public PeaShooter(int x, int y){
        super(100, x, y,"images/active_peashooter.png","images/inactive_peashooter.png");
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



    /**
     * Creates animated bullets.
     * @param target represents ImageView where plant is dropped.
     * @return movingBullet represents the animation.
     */
    public PathTransition shootBullets(ImageView target) {
        int row = GridPane.getRowIndex(target);
        int col = GridPane.getColumnIndex(target);
        GridPane g = (GridPane) target.getParent();
        bulletIV = new ImageView(peaBullet);

        bulletIV.setX(g.getLayoutX() + (col - 1) * 90 + 69);
        bulletIV.setY(g.getLayoutY() + (row - 1) * 109 + 37);

        pane = (AnchorPane) target.getParent().getParent();
        pane.getChildren().add(bulletIV);
        animatePea(bulletIV);
        movingBullet.play();
        return movingBullet;
    }

    /**
     * Sets the default PathTransition animation of bullet moving.
     * @param PEA represents the bullet image.
     */
    private void animatePea(ImageView PEA){
        PathTransition movePea = new PathTransition();
        Line pPath = new Line(PEA.getX(), PEA.getY()+5, PEA.getX()+1000, PEA.getY()+5);
        movePea.setNode(PEA);
        movePea.setPath(pPath);
        movePea.setDuration(Duration.seconds(2));
        movePea.setCycleCount(Timeline.INDEFINITE);
        movingBullet = movePea;
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

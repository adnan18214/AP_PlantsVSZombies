package allClasses;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface shooterPlant {
    public Image getAliveGIF();
    public Image getDyingGIF();
    public PathTransition shootBullets(ImageView target);
}

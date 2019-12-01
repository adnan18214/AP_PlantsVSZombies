package allClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.Serializable;

public class SunToken implements Serializable {
    private Image sun;
    private Text sunTokenCount;


    public SunToken(Text sCount){
        sunTokenCount = sCount;
        sun = new Image("images/sunToken.gif");
    }

    public ImageView getSuntoken(double x, double y){
        ImageView sunToken = new ImageView(sun);
        sunToken.setX(x);
        sunToken.setY(y);
        sunToken.setFitWidth(64);
        sunToken.setFitHeight(58);

        sunToken.setOnMouseClicked((e)-> {
            if(sunToken.getImage() != null || sunToken.getOpacity() != 0) {
                sunToken.setImage(null);
                int count = Integer.parseInt(sunTokenCount.getText());
                count += 50;
                sunTokenCount.setText(Integer.toString(count));
            }
        });
        return sunToken;
    }

    public Image getSunImage() {
        return sun;
    }


}

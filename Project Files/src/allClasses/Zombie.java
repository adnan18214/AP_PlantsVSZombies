package allClasses;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Zombie
{
    private int posX;
    private int posY;
    private int health;
    private ImageView zombieImage;

    public Zombie(ImageView i)
    {
        this.zombieImage = i;
        this.posX = 1100;
        this.health = 30;
    }
}

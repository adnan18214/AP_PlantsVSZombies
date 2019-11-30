package allClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Plant {
    protected final int X_COORDINATE;
    protected final int Y_COORDINATE;
    protected final ArrayList<Zombie> attackingZombies;
    protected ImageView plantIV;
    protected Image aliveGIF;
    protected Image dyingGIF;
    private int health;
    private int cost;
    private boolean alive;
    private String active;
    private String inactive;


    public Plant(int c, int h, int x, int y, String ac, String in, ArrayList zombies){
        active = ac;
        inactive = in;
        health = h;
        cost = c;
        X_COORDINATE = x;
        Y_COORDINATE = y;
        attackingZombies = zombies;
        alive = true;
    }

    public void attackPlant(int damage){
        health -= damage;
        if(health < 0) {
            health = 0;
            alive = false;
        }
    }

    public String getInactiveUrl()
    {
        return inactive;
    }

    public String getActiveUrl()
    {
        return active;
    }

    public int getHealth(){return health;}

    public boolean isAlive(){
        return alive;
    }

    public int getX_COORDINATE() {
        return X_COORDINATE;
    }

    public int getY_COORDINATE() {
        return Y_COORDINATE;
    }

    public boolean isZombieAttacking(){
        return !attackingZombies.isEmpty();
    }

    public int getCost() {
        return cost;
    }

    public abstract void killPlant();
    public abstract void detectCollisions(boolean activate);
}

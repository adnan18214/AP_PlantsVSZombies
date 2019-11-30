package allClasses;

import java.util.ArrayList;

public abstract class Plant {
    protected final int X_COORDINATE;
    protected final int Y_COORDINATE;
    protected final ArrayList<Zombie> attackingZombies;
    private int health;
    private boolean alive;

    public Plant(int h, int x, int y, ArrayList zombies){
        health = h;
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

    public abstract void killPlant();
    public abstract void detectCollisions(boolean activate);
}

package allClasses;

import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class Lawn implements Serializable {
    private Plant[][] grid;
    private ArrayList<Plant> plantsPresent;
    private ArrayList<Zombie>[] zombiesPresent;
    private ArrayList<lawnMower> allLawnMowers;
    private static Lawn lawn;
    private int j=0;

    private Lawn()
    {
        this.grid = new Plant[5][9];
        plantsPresent = new ArrayList<>();
        zombiesPresent = new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            zombiesPresent[i] = new ArrayList<>();
        }
        allLawnMowers = new ArrayList<>();
    }

    public static Lawn getLawn()
    {
        if (lawn == null)
        {
            lawn = new Lawn();
        }
        return lawn;
    }

    public void addLawnMower(ImageView lm, int r){
        allLawnMowers.add(new lawnMower(lm, zombiesPresent[r-1]));
    }

    public void addPlant(Plant p){
        grid[p.getY_COORDINATE()-1][p.getX_COORDINATE()-1] = p;
        plantsPresent.add(p);
    }

    public Plant getPlant(int x, int y){
        return grid[y-1][x-1];
    }

    public void removePlant(Plant p){
        grid[p.getY_COORDINATE()-1][p.getX_COORDINATE()-1] = null;
        plantsPresent.remove(p);
    }

    public void addZombie(Zombie z){
        zombiesPresent[z.getRow()-1].add(z);
    }

    public ArrayList<Zombie> getZombies(int r){
        return zombiesPresent[r-1];
    }

    public void removeZombie(Zombie z){
        zombiesPresent[z.getRow()-1].remove(z);
    }

}

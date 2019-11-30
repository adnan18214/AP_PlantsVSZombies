package allClasses;

import java.util.ArrayList;

public class Lawn {
    private Plant[][] grid;
    private ArrayList<Plant> plantsPresent;
    private ArrayList<Zombie>[] zombiesPresent;
    private static Lawn lawn;

    private Lawn()
    {
        this.grid = new Plant[5][9];
        plantsPresent = new ArrayList<>();
        zombiesPresent = new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            zombiesPresent[i] = new ArrayList<>();
        }
    }

    public static Lawn getLawn()
    {
        if (lawn == null)
        {
            lawn = new Lawn();
        }
        return lawn;
    }

    public void addPlant(Plant p){
        grid[p.getX_COORDINATE()-1][p.getY_COORDINATE()-1] = p;
        plantsPresent.add(p);
    }

    public Plant getPlant(int x, int y){
        return grid[x-1][y-1];
    }

    public void removePlant(Plant p){
        grid[p.getX_COORDINATE()-1][p.getY_COORDINATE()-1] = null;
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

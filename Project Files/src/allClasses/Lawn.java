package allClasses;

import java.util.ArrayList;

public class Lawn {
    private Plant[][] grid;
    private ArrayList<Plant> plantsPresent = new ArrayList<Plant>();
    private static Lawn lawn;

    private Lawn()
    {
        this.grid = new Plant[5][9];
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
}

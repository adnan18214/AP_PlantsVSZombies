package allClasses;

import java.util.ArrayList;

public class Lawn {
    private Plant[][] grid;
    private ArrayList<Plant> plantsPresent = new ArrayList<Plant>();
    private static Lawn lawn;
    private Zombie[][] zombieList;
    private int j=0;

    private Lawn()
    {
        this.grid = new Plant[5][9];
        this.zombieList = new Zombie[5][9];
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

    public void addZombie(Zombie z, int y)
    {
        zombieList[j][y] = z;
        j+=1;
    }

    public Plant getPlant(int x, int y){
        return grid[x-1][y-1];
    }

    public void getZombie()
    {
        for (int i=0; i<5; i++)
        {
            for (int j=0; j<9; j++)
            {
                System.out.println(zombieList[i][j]);
            }
        }
    }

    public void removePlant(Plant p){
        grid[p.getX_COORDINATE()-1][p.getY_COORDINATE()-1] = null;
        plantsPresent.remove(p);
    }
}

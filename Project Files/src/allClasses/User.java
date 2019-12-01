package allClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String name;
    private int highScore=0;
    private ArrayList<GameWorld> savedGames;

    public User(String name)
    {
        this.name = name;
        this.savedGames = new ArrayList<GameWorld>();
    }

    public String getName()
    {
        return this.name;
    }

    public int getHighScore()
    {
        return this.highScore;
    }

    public GameWorld getGame(int i)
    {
        return savedGames.get(i);
    }

    public void saveGame(GameWorld g){
        savedGames.add(g);
    }
}

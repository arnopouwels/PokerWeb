package Users;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by Dihan on 1/20/2015.
 */

@Entity
public class Game {

    @Id
    @Size(max = 100)
    private String gameName;

    public Game(){}

    public Game(String _gameName)
    {
        gameName = _gameName;
    }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String _gameName) { this.gameName = _gameName; }
}

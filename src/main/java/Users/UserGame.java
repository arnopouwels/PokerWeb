package Users;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Dihan on 1/20/2015.
 */

@Entity
public class UserGame implements Serializable {

    @Id
    @Size(max = 12)
    private String username;
    @Id
    @Size(max = 100)
    private String gameName;
    private String hand;

    public UserGame(){}

    public UserGame(String _username, String _gameName, String _hand)
    {
        username = _username;
        gameName = _gameName;
        hand = _hand;
    }

    public String getUsername(){
        return username;
    }

    public String getGameName(){
        return gameName;
    }

    public String getHand(){return hand;}

    public void setUsername(String user) { this.username = user; }
    public void setGameName(String _gameName) { this.gameName = _gameName; }
    public void setHand(String _hand){this.hand = _hand;}
}

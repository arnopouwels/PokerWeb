package Users;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@IdClass(id_UserGame.class)
public class UserGame implements Serializable {

    @Id
    @Size(max = 12)
    private String username;
    @Id
    @Size(max = 100)
    private String gameName;
    private String hand;

    @ManyToOne
    @JoinColumn(name="USERNAME", referencedColumnName="USERNAME", insertable = false, updatable = false)
    private User u;

    @JoinColumn(name="GAMENAME", referencedColumnName="GAMENAME", insertable = false, updatable = false)
    @ManyToOne
    private Game g;

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

    public User getU() {return u;}

    public Game getG() {return g;}

    public void setUsername(String user) { this.username = user; }
    public void setGameName(String _gameName) { this.gameName = _gameName; }
    public void setHand(String _hand){this.hand = _hand;}

    public void setU(User _u) {this.u = _u;}
    public void setG(Game _g) {this.g = _g;}
}

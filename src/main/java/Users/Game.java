package Users;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Game {

    @Id
    @Size(max = 100)
    private String gameName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfGame;

    @OneToMany(mappedBy = "g", fetch = FetchType.EAGER)
    private List<UserGame> users = new ArrayList<>();

    private boolean active;

    private String host;

    private int costToPlay;

    public void addUser(User _user) {
        UserGame userGame = new UserGame();
        userGame.setUsername(_user.getUsername());
        userGame.setGameName(this.gameName);
        userGame.setU(_user);
        userGame.setG(this);
        users.add(userGame);
    }

    public Game(){}

    public Game(String _gameName, Date _date)
    {
        gameName = _gameName;
        dateOfGame = _date;
    }

    public String getHost() { return host; }

    public void setHost(String _host) { this.host = _host; }

    public boolean getActive() { return active; }

    public void setActive(boolean _active) { this.active = _active; }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String _gameName) { this.gameName = _gameName; }

    public Date getDateOfGame() {return dateOfGame;}

    public void setDateOfGame(Date date) { this.dateOfGame = date;}

    public int getCostToPlay() {return costToPlay;}

    public void setCostToPlay(int costToPlayInRands) { this.costToPlay = costToPlayInRands;}
}

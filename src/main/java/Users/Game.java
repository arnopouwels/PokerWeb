package Users;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dihan on 1/20/2015.
 */

@Entity
public class Game {

    @Id
    @Size(max = 100)
    private String gameName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfGame;

    @OneToMany(mappedBy = "g")
    private List<UserGame> users = new ArrayList<>();

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

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String _gameName) { this.gameName = _gameName; }

    public Date getDateOfGame() {return dateOfGame;}

    public void setDateOfGame(Date date) { this.dateOfGame = date;}
}

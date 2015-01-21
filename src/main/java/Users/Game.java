package Users;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.security.Timestamp;
import java.util.Date;

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

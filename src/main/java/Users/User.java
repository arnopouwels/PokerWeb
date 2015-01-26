package Users;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User {

    @Id
    @Size(max = 12)
    private String username;
    private String password;

    @OneToMany(mappedBy = "u", fetch = FetchType.EAGER)
    private List<UserGame> games = new ArrayList<>();

    public void addGame(Game _game) {
        UserGame userGame = new UserGame();
        userGame.setUsername(this.getUsername());
        userGame.setGameName(_game.getGameName());
        userGame.setU(this);
        userGame.setG(_game);
        games.add(userGame);
    }


    public User(){}

    public User(String _username, String _password)
    {
        username = _username;
        password = _password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String user) { this.username = user; }
    public void setPassword(String pass) { this.password = pass; }


}

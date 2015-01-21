package Users;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by Andre on 2015-01-16.
 */

@Entity
public class User {

    @Id
    @Size(max = 12)
    private String username;
    private String password;

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

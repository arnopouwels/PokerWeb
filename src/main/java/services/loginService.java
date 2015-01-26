package services;

import Users.User;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public class loginService  {

    public boolean findUser(String _username) {
        for (User user : RegisterService.getUsers()){
            if (user.getUsername().compareTo(_username) == 0)
                return true;
        }
        return false;
    }

    public boolean isLogged(String _username)
    {
        return false;
    }

}

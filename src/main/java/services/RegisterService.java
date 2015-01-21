package services;

import Users.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import controllers.ApplicationController;
import ninja.Result;
import ninja.Results;
import ninja.Router;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
//import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 2015-01-16.
 */

@Singleton
public class RegisterService {

    public static String output()
    {
        return "Register to poker service";

    }

    public static boolean createUser(String name, String pass)
    {
        User u = new User(name, pass);
        for (User user : userList) {
            if (user.getUsername().compareTo(name) == 0)
                return false;

        }

        userList.add(u);
        return true;
    }

    public static List<User> getUsers()
    {
        return userList;
    }

    private static List<User> userList = new ArrayList<>();

    //Router router;

    @Inject private Provider<EntityManager> entityManagerProvider;
    @Transactional
    public boolean userStore(User user) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.persist(user);
        return true;

    }

    @UnitOfWork
    public void getAllUsers(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT x FROM User x");

        List<User> users = (List<User>) q.getResultList();
        if (users != null)
            for (int i = 0; i < users.size(); i++)
                System.out.println(users.get(i).getUsername());
        //return "No users";
    }

    @UnitOfWork
    public boolean userGet(String _username) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM User x where x.username = :usr");
        q.setParameter("usr", _username);
        List<User> users = (List<User>) q.getResultList();

        if (users == null)
            return false;

        for (int i = 0; i < users.size(); i++)
            System.out.println(users.get(i).getUsername() + " " + users.get(i).getPassword());
        return true;
    }

}

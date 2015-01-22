package services;

import Users.Game;
import Users.UserGame;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Dihan on 1/22/2015.
 */
@Singleton
public class userGameService {

    @Inject
    private Provider<EntityManager> entityManagerProvider;
    @Transactional
    public boolean userGameStore(UserGame Ugame) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.persist(Ugame);
        return true;

    }

    @UnitOfWork
    public void getAllUserGames(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT x FROM UserGame x");

        List<UserGame> Ugames = (List<UserGame>) q.getResultList();
        if (Ugames != null)
            for (int i = 0; i < Ugames.size(); i++)
                System.out.println(Ugames.get(i).getGameName());
        //return "No users";
    }

    @UnitOfWork
    public boolean UserGameGet(String gamename) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM UserGame x where x.gameName = :gme");
        q.setParameter("gme", gamename);
        List<UserGame> games = (List<UserGame>) q.getResultList();

        if (games == null)
            return false;

        for (int i = 0; i < games.size(); i++)
            System.out.println(games.get(i).getGameName());
        return true;
    }
}


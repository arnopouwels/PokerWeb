package services;

import Users.Game;
import Users.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Dihan on 1/20/2015.
 */

@Singleton
public class multiplayerService {

    @Inject
    private Provider<EntityManager> entityManagerProvider;
    @Transactional
    public boolean gameStore(Game game) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.persist(game);
        return true;

    }

    @UnitOfWork
    public void getAllGames(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT x FROM Game x");

        List<Game> games = (List<Game>) q.getResultList();
        if (games != null)
            for (int i = 0; i < games.size(); i++)
                System.out.println(games.get(i).getGameName());
        //return "No users";
    }

    @UnitOfWork
    public boolean gameGet(String gamename) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM Game x where x.gameName = :gme");
        q.setParameter("gme", gamename);
        List<Game> games = (List<Game>) q.getResultList();

        if (games == null)
            return false;

        for (int i = 0; i < games.size(); i++)
            System.out.println(games.get(i).getGameName() + " " + games.get(i).getDateOfGame());
        return true;
    }
}

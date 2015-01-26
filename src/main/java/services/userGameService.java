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
    public List<UserGame> getAllUserGames(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT x FROM UserGame x");

        List<UserGame> Ugames = (List<UserGame>) q.getResultList();

        return Ugames;
    }

    @UnitOfWork
    public List<UserGame> getListOfGames(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT DISTINCT x.gameName FROM UserGame x");

        List<UserGame> listGames = (List<UserGame>) q.getResultList();

        return listGames;
    }

    @UnitOfWork
    public List<UserGame> UserGameGet(String gamename) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM UserGame x where x.gameName = :gme");
        q.setParameter("gme", gamename);
        List<UserGame> games = (List<UserGame>) q.getResultList();

        return games;
    }

    @UnitOfWork
    public List<UserGame> UserGameGameGet(String gamename, String username) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM UserGame x where x.gameName = :gme AND x.username = :usr");
        q.setParameter("gme", gamename);
        q.setParameter("usr", username);
        List<UserGame> games = (List<UserGame>) q.getResultList();

        return games;
    }
}


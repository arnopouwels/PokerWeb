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
    public List<Game> getAllActiveGames(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT x FROM Game x where x.active = true");

        List<Game> games = (List<Game>) q.getResultList();

        return games;
    }

    @UnitOfWork
    public List<Game> getAllGames(){
        EntityManager entityManager = entityManagerProvider.get();
        String out = "";
        Query q = entityManager.createQuery("SELECT x FROM Game x");

        List<Game> games = (List<Game>) q.getResultList();

        return games;
    }

    @UnitOfWork
    public List<Game> gameGet(String gamename) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM Game x where x.gameName = :gme");
        q.setParameter("gme", gamename);
        List<Game> games1 = (List<Game>) q.getResultList();

        return games1;
    }
}

package Repositories;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
//import java.security.Provider;

/**
 * Created by Andre on 2015-01-20.
 */
public class BaseRepository<T> {
    @Inject private Provider<EntityManager> entityManagerProvider;

    @Transactional
    public void persist(T entity){
        //getEntityManager()
    }
}

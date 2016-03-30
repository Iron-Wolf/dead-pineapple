package com.deadpineapple.dal.persistence;

import com.deadpineapple.dal.entity.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mikael on 30/03/16.
 */
@Service
@Transactional
public class main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("deadpineapple-jpa");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        UserAccount u = new UserAccount();
        u.setEmail("email@email.com");
        u.setCreationDate(new Date());

        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            em.close();
        }


    }
}

package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.dal.entity.UserAccount_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikael on 30/03/16.
 */
@Stateless
public class UserDao implements IUserDao {

    // manager de persistance
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("deadpineapple-jpa");
    private static EntityManager em = emf.createEntityManager();

    @Override
    public UserAccount createUser(UserAccount user){
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

        }catch(Exception e){

            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserAccount updateUser(UserAccount newUser){
        // update the user's data
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        /* NE MARCHE PAS POUR L'INSTANT

        CriteriaUpdate<UserAccount> criteriaUpdate = cb.createCriteriaUpdate(UserAccount.class);
        Root<UserAccount> root = criteriaUpdate.from(UserAccount.class);

        criteriaUpdate.set(UserAccount_.firstName, newUser.getFirstName());
        criteriaUpdate.set(UserAccount_.lastName, newUser.getLastName());
        criteriaUpdate.set(UserAccount_.email, newUser.getEmail());
        criteriaUpdate.set(UserAccount_.password, newUser.getPassword());
        criteriaUpdate.where(cb.equal(root.get(UserAccount_.id), newUser.getId()));

        int result = em.createQuery(criteriaUpdate).executeUpdate();
        em.getTransaction().commit();

        if (result != 0)
        {
            // update is OK, so we return the user
            return newUser;
        }
        */
        return new UserAccount();
    }

    @Override
    public Long totalUsers(){
        // retrieve the total number of users
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<UserAccount> root = criteriaQuery.from(UserAccount.class);

        criteriaQuery.select(cb.countDistinct(root));
        Long result = em.createQuery(criteriaQuery).getSingleResult();

        em.getTransaction().commit();
        return result;
    }

    @Override
    public UserAccount find(String email) {
        // retrieve a user according to the given e-mail
        em.getTransaction().begin();
        UserAccount user = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<UserAccount> criteriaQuery = cb.createQuery(UserAccount.class);
        Root<UserAccount> emailUser = criteriaQuery.from(UserAccount.class);
        criteriaQuery.where( cb.equal(emailUser.get(UserAccount_.email), email));


        try{
            user = em.createQuery(criteriaQuery).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            return null;
        }
        return user;
    }

    @Override
    public UserAccount checkCredentials(String login, String password)
    {
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<UserAccount> criteriaQuery = cb.createQuery(UserAccount.class);
        Root<UserAccount> credentialsUser = criteriaQuery.from(UserAccount.class);

        //list of predicates
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (login != null)
        {
            predicates.add(cb.equal
                    (credentialsUser.get(UserAccount_.email), login));
        }

        if (password != null)
        {
            predicates.add(cb.equal
                    (credentialsUser.get(UserAccount_.password), password));
        }

        criteriaQuery.where(
                predicates.toArray(new Predicate[predicates.size()]));


        try {
            // send the request
            UserAccount result = em.createQuery(criteriaQuery).getSingleResult();
            em.getTransaction().commit();

            if (result.getEmail().equals(login))
            {
                // if the result send by the DB is equal,
                // the user is returned
                return result;
            }
        }
        catch(Exception e){
            return null;
        }


        return null;
    }
}

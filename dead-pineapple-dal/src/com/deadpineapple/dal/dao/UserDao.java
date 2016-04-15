package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.UserAccount;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created by mikael on 30/03/16.
 */
public class UserDao implements IUserDao {

    //private HibernateTemplate hibernateTemplate;
    private SessionFactory sessFact;

    public UserDao(){}

    public UserDao (SessionFactory sessionFactory)
    {
        this.sessFact = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        //this.hibernateTemplate = new HibernateTemplate(sessionFactory);
        //this.hibernateTemplate.setCheckWriteOperations(false);
        this.sessFact = sessionFactory;
    }

    @Override
    public UserAccount find(String email) {
        // retrieve a user according to the given e-mail
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();

        UserAccount user = null;

        Criteria criteria = sess.createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("email",email));

        user = (UserAccount) criteria.uniqueResult();

        tx.commit();
        sess.close();
        return user;
    }

    @Override
    public UserAccount checkCredentials(String login, String password)
    {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();
        UserAccount user = null;

        Criteria criteria = sess.createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("email",login));
        criteria.add(Restrictions.eq("password", password));

        user = (UserAccount) criteria.uniqueResult();

        if (user.getEmail().equals(login)) {
            // if the result send by the DB is equal,
            // the user is returned
            return user;
        }

        return null;
    }


    @Override
    public void saveUser(UserAccount user) {
        //hibernateTemplate.saveOrUpdate(user);
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();
        sess.saveOrUpdate(user);
        tx.commit();
        sess.close();
    }
}
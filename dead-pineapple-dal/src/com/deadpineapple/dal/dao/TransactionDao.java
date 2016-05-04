package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.Transaction;
import com.deadpineapple.dal.entity.UserAccount;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import java.util.List;

/**
 * Created by mikael on 03/05/16.
 */
public class TransactionDao implements ITransactionDao {

    private SessionFactory sessFact;

    public TransactionDao(){};

    public TransactionDao (SessionFactory sessionFactory)
    {
        this.sessFact = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessFact = sessionFactory;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        org.hibernate.Session sess = sessFact.openSession();
        org.hibernate.Transaction tx = sess.beginTransaction();
        sess.saveOrUpdate(transaction);
        tx.commit();
        sess.close();
        return transaction;
    }

    @Override
    public int getNextIdTransaction() {
        // return the next possible value for a group of transaction
        org.hibernate.Session sess = sessFact.openSession();
        org.hibernate.Transaction tx = sess.beginTransaction();

        DetachedCriteria maxId = DetachedCriteria.forClass(Transaction.class)
                .setProjection( Projections.max("idTransaction") );
        Criteria criteria = sess.createCriteria(Transaction.class)
                .add( Property.forName("idTransaction").eq(maxId) );

        Transaction maxT = (Transaction) criteria.uniqueResult();

        tx.commit();
        sess.close();
        return maxT.getIdTransaction()+1;
    }

    @Override
    public List<Transaction> getTransByIdTransaction(int id)
    {
        org.hibernate.Session sess = sessFact.openSession();
        org.hibernate.Transaction tx = sess.beginTransaction();

        Criteria criteria = sess.createCriteria(Transaction.class);
        criteria.add(Restrictions.eq("idTransaction",id));

        List<Transaction> trans = (List<Transaction>) criteria.list();

        sess.close();
        return trans;
    }
}

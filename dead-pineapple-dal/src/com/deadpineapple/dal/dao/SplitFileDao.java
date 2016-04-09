package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.SplitFile;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created by mikael on 31/03/16.
 */

public class SplitFileDao implements ISplitFileDao {

    private SessionFactory sessFact;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessFact = sessionFactory;
    }


    @Override
    public SplitFile createFile(SplitFile file) {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();

        sess.saveOrUpdate(file);

        tx.commit();
        sess.close();
        return file;
    }

    @Override
    public SplitFile updateFile(SplitFile file) {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();

        sess.update(file);

        tx.commit();
        sess.close();
        return file;
    }

    @Override
    public SplitFile findById(Long id) {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();
        SplitFile sFile = null;

        Criteria criteria = sess.createCriteria(SplitFile.class);
        criteria.add(Restrictions.eq("id",id));

        sFile = (SplitFile) criteria.uniqueResult();

        return sFile;
    }

}
package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.ConvertedFile;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 * Created by mikael on 31/03/16.
 */

public class ConvertedFileDao implements IConvertedFileDao {

    private SessionFactory sessFact;

    public ConvertedFileDao(){}

    public ConvertedFileDao (SessionFactory sessionFactory)
    {
        this.sessFact = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessFact = sessionFactory;
    }

    @Override
    public ConvertedFile createFile(ConvertedFile file) {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();

        sess.saveOrUpdate(file);

        tx.commit();
        sess.close();
        return file;
    }

    @Override
    public ConvertedFile updateFile(ConvertedFile file) {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();

        sess.update(file);

        tx.commit();
        sess.close();
        return file;
    }

    @Override
    public ConvertedFile findById(Long id) {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();
        ConvertedFile cFile = null;

        Criteria criteria = sess.createCriteria(ConvertedFile.class);
        criteria.add(Restrictions.eq("id",id));

        cFile = (ConvertedFile) criteria.uniqueResult();

        sess.close();
        return cFile;
    }

    @Override
    public void deleteFile(ConvertedFile convertedFile)
    {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();
        sess.delete(convertedFile);
        tx.commit();
        sess.close();
    }

    @Override
    public void deleteFileById(Long id)
    {
        org.hibernate.Session sess = sessFact.openSession();
        Transaction tx = sess.beginTransaction();

        // only need to specify the ID to delete it
        ConvertedFile splitFile = (ConvertedFile) sess.createCriteria(ConvertedFile.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        sess.delete(splitFile);

        tx.commit();
        sess.close();
    }
}
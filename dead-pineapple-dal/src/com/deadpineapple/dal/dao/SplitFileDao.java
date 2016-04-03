package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.SplitFile;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by mikael on 31/03/16.
 */
@Stateless
public class SplitFileDao implements ISplitFileDao {

    // manager de persistance
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("deadpineapple-jpa");
    private static EntityManager em = emf.createEntityManager();


    @Override
    public SplitFile createFile(SplitFile file) {
        try {
            em.getTransaction().begin();
            em.persist(file);
            em.getTransaction().commit();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return file;
    }

    @Override
    public SplitFile updateFile(SplitFile newFile) {
        return null;
    }

    @Override
    public SplitFile findById(Long splitFileId) {
        return em.find(SplitFile.class, splitFileId);
    }
}

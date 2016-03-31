package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.dal.entity.ConvertedFile_;
import com.deadpineapple.dal.entity.UserAccount;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by mikael on 31/03/16.
 */
@Stateless
public class ConvertedFileDao implements IConvertedFileDao {

    // manager de persistance
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("deadpineapple-jpa");
    private static EntityManager em = emf.createEntityManager();

    @Override
    public ConvertedFile createFile(ConvertedFile file) {
        try{
            em.getTransaction().begin();
            em.persist(file);
            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public ConvertedFile updateFile(ConvertedFile newFile) {
        return null;
    }

    @Override
    public Long totalConvertedFile() {
        // retrieve the total number of Converted files
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<ConvertedFile> root = criteriaQuery.from(ConvertedFile.class);

        criteriaQuery.select(cb.countDistinct(root));
        Long result = em.createQuery(criteriaQuery).getSingleResult();

        em.getTransaction().commit();
        return result;
    }

    @Override
    public ConvertedFile findByOriginalName(String name) {
        // retrieve a Converted file according to the original name
        em.getTransaction().begin();
        ConvertedFile file = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<ConvertedFile> criteriaQuery = cb.createQuery(ConvertedFile.class);
        Root<ConvertedFile> cfByName = criteriaQuery.from(ConvertedFile.class);
        criteriaQuery.where( cb.equal(cfByName.get(ConvertedFile_.originalName), name));


        try{
            file = em.createQuery(criteriaQuery).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            return null;
        }
        return file;
    }
}

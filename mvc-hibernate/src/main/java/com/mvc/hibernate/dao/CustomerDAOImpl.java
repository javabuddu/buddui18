package com.mvc.hibernate.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.hibernate.entity.Customer;

@Repository
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List < Customer > getCustomers() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery < Customer > cq = cb.createQuery(Customer.class);
        Root < Customer > root = cq.from(Customer.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = sessionFactory.openSession();
       Transaction tran= session.beginTransaction();
        Customer book = session.byId(Customer.class).load(id);
        session.delete(book);
        tran.commit();
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        Session currentSession = sessionFactory.openSession();
        Transaction tran= currentSession.beginTransaction();
        currentSession.saveOrUpdate(theCustomer);
        tran.commit();
    }

    @Override
    public Customer getCustomer(int theId) {
        Session currentSession = sessionFactory.openSession();
        Customer theCustomer = currentSession.get(Customer.class, theId);
        return theCustomer;
    }
}
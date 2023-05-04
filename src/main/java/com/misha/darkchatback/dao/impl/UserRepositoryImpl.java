package com.misha.darkchatback.dao.impl;

import com.misha.darkchatback.dao.UserRepository;
import com.misha.darkchatback.exception.DataProcessingException;
import com.misha.darkchatback.model.User;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory factory;

    public UserRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert user (id="
                    + user.getId() + "to db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM User u "
                            + "WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("User with login "
                    + login + " not found", e);
        }
    }
}

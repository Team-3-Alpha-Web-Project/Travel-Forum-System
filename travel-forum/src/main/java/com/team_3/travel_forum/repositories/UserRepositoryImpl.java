package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> get() {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User", User.class);

            return query.list();
        }
    }

    @Override
    public User get(int id) {
        try (Session session = sessionFactory.openSession()) {

            User user = session.find(User.class, id);

            if (user == null) {
                throw new EntityNotFoundException("User");
            }

            return user;
        }
    }

    @Override
    public User get(String username) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User where username = :username", User.class);

            query.setParameter("username", username);

            List<User> result = query.list();

            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "username", username);
            }

            return result.get(0);
        }
    }

    @Override
    public List<User> searchByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User where lower(username) like lower(:username)", User.class);

            query.setParameter("username", "%" + username + "%");

            return query.list();
        }
    }

    @Override
    public List<User> searchByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User where lower(email) like lower(:email)", User.class);

            query.setParameter("email", "%" + email + "%");

            return query.list();
        }
    }

    @Override
    public List<User> searchByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User where lower(firstName) like lower(:firstName)", User.class);

            query.setParameter("firstName", "%" + firstName + "%");

            return query.list();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(u) from User u " + "where lower(u.username) = lower(:username)", Long.class);

            query.setParameter("username", username);

            return query.uniqueResult() > 0;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "select count(u) from User u " + "where lower(u.email) = lower(:email)", Long.class);

            query.setParameter("email", email);

            return query.uniqueResult() > 0;
        }
    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.merge(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public long countAllUsers() {
        try (Session session = sessionFactory.openSession()) {

            Query<Long> query = session.createQuery(
                    "select count(u) from User u", Long.class
            );

            return query.getSingleResult();
        }
    }
}

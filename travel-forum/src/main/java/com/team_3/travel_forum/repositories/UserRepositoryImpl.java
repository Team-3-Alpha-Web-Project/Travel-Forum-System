package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.User;
import jakarta.persistence.EntityManagerFactory;
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
//    public UserRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }


    //защо е толкова различно и трябва ли да започваме проекта на ново, за да може да следваме предишните проекти
    public UserRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    public List<User> get() {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery(
                    "from User",
                    User.class
            );

            return query.list();
        }
    }

    @Override
    public User get(int id) {
        try (Session session = sessionFactory.openSession()) {

//            User user = session.get(User.class, id);

            //така ли да го използваме това
            User user = session.find(User.class, id);

            if (user == null) {
                throw new RuntimeException("User not found.");
            }

            return user;
        }
    }

    @Override
    public User get(String username) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery(
                    "from User where username = :username",
                    User.class
            );

            query.setParameter("username", username);

            List<User> result = query.list();

            if (result.isEmpty()) {
                throw new RuntimeException("User not found.");
            }

            return result.get(0);
        }
    }

    @Override
    public List<User> searchByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery(
                    "from User where lower(username) like lower(:username)",
                    User.class
            );

            query.setParameter("username", "%" + username + "%");

            return query.list();
        }
    }

    @Override
    public List<User> searchByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery(
                    "from User where lower(email) like lower(:email)",
                    User.class
            );

            query.setParameter("email", "%" + email + "%");

            return query.list();
        }
    }

    @Override
    public List<User> searchByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery(
                    "from User where lower(firstName) like lower(:firstName)",
                    User.class
            );

            query.setParameter("firstName", "%" + firstName + "%");

            return query.list();
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
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

//            User user = session.get(User.class, id);

            User user = session.find(User.class, id);
            if (user == null) {
                throw new RuntimeException("User not found.");
            }

            session.remove(user);

            session.getTransaction().commit();
        }
    }


}

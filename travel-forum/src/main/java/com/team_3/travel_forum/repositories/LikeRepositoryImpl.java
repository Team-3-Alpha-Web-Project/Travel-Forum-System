package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.Like;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public LikeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Like get(int id) {
        try (Session session = sessionFactory.openSession()) {

            Like like = session.find(Like.class, id);
            if (like == null) {
                throw new EntityNotFoundException("Like");
            }

            return like;
        }
    }

    @Override
    public void create(Like like) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(like);

            session.getTransaction().commit();
        }

    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            Like like = session.find(Like.class, id);
            if (like == null) {
                throw new EntityNotFoundException("Like");
            }

            session.remove(like);

            session.getTransaction().commit();

        }
    }

    @Override
    public Like findByPostAndUser(int postId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Like> query = session.createQuery(
                    "select l from Like l where l.post.id = :postId and l.user.id = :userId",
                    Like.class
            );
            query.setParameter("postId", postId);
            query.setParameter("userId", userId);

            List<Like> results = query.list();
            if (results.isEmpty()) {
                return null;
            }

            return results.get(0);
        }
    }

    @Override
    public long countByPost(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "select count(l) from Like l where l.post.id = :postId",
                    Long.class
            );

            query.setParameter("postId", postId);

            return query.getSingleResult();
        }
    }

}
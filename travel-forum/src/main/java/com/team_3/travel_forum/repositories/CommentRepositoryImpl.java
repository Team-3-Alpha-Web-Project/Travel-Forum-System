package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comment> get() {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery(
                    "from Comment", Comment.class
            );

            return query.list();
        }
    }

    @Override
    public Comment get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Comment comment = session.find(Comment.class, id);

            if (comment == null) {
                throw new EntityNotFoundException("Comment not found.");
            }

            return comment;
        }
    }

    @Override
    public void create(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(comment);

            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(comment);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Comment comment = session.find(Comment.class, id);
            if (comment == null) {
                throw new EntityNotFoundException("Comment not found.");
            }

            session.remove(comment);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<Comment> searchByPost(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery(
                    "from Comment c where c.post.id =:postId", Comment.class
            );

            query.setParameter("postId", postId);

            return query.list();
        }
    }

    @Override
    public List<Comment> searchByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery(
                    "from Comment c where c.user.id = :userId", Comment.class
            );

            query.setParameter("userId", userId);

            return query.list();
        }
    }


    @Override
    public long countByPost(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "select count(c) from Comment c where c.post.id = :postId",
                    Long.class
            );

            query.setParameter("postId", postId);

            return query.getSingleResult();
        }
    }
}
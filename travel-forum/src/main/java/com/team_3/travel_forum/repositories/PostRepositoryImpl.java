package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.Post;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    public List<Post> get() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery(
                    "from Post order by createdAt desc",
                    Post.class
            );
            return query.list();
        }
    }

    @Override
    public Post get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post not found.");
            }
            return post;
        }
    }

    @Override
    public List<Post> getByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery(
                    "from Post p where p.user.id = :userId order by p.createdAt desc",
                    Post.class
            );
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    @Override
    public List<Post> search(String keyword, String sortBy) {
        try (Session session = sessionFactory.openSession()) {
            boolean hasKeyword = keyword != null && !keyword.isBlank();

            String hql = "from Post p ";

            if (hasKeyword) {
                hql = hql + "where lower(p.title) like :likeKeyword " +
                        "or lower(p.content) like :likeKeyword ";
            }

            hql = hql + "order by " + resolveSortClause(sortBy);

            Query<Post> query = session.createQuery(hql, Post.class);

            if (hasKeyword) {
                query.setParameter("likeKeyword", "%" + keyword.toLowerCase() + "%");
            }

            return query.list();
        }
    }

    @Override
    public void create(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Post post = session.find(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post not found.");
            }
            session.remove(post);
            session.getTransaction().commit();
        }
    }

    private String resolveSortClause(String sortBy) {
        if (sortBy == null) {
            return "p.createdAt desc";
        }

        return switch (sortBy) {
            case "mostLiked" -> "(select count(l) from PostLike l where l.post = p) desc";
            case "mostCommented" -> "(select count(c) from Comment c where c.post = p) desc";
            default -> "p.createdAt desc";
        };
    }
}
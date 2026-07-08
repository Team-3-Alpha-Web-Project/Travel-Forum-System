package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager entityManager;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> get() {
        return entityManager.createQuery(
                "from Post p order by p.createdAt desc",
                Post.class
        ).getResultList();
    }

    @Override
    public Post get(int id) {
        Post post = entityManager.find(Post.class, id);

        if (post == null) {
            throw new EntityNotFoundException("Post not found.");
        }

        return post;
    }

    @Override
    public List<Post> getByUser(int userId) {
        TypedQuery<Post> query = entityManager.createQuery(
                "from Post p where p.user.id = :userId order by p.createdAt desc",
                Post.class
        );

        query.setParameter("userId", userId);

        return query.getResultList();
    }

    @Override
    public List<Post> search(String keyword, String sortBy) {
        boolean hasKeyword = keyword != null && !keyword.isBlank();

        String hql = "from Post p ";

        if (hasKeyword) {
            hql = hql + "where lower(p.title) like :likeKeyword " +
                    "or lower(p.content) like :likeKeyword ";
        }

        hql = hql + "order by " + resolveSortClause(sortBy);

        TypedQuery<Post> query = entityManager.createQuery(hql, Post.class);

        if (hasKeyword) {
            query.setParameter("likeKeyword", "%" + keyword.toLowerCase() + "%");
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public void create(Post post) {
        entityManager.persist(post);
    }

    @Override
    @Transactional
    public void update(Post post) {
        entityManager.merge(post);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Post post = entityManager.find(Post.class, id);

        if (post == null) {
            throw new EntityNotFoundException("Post not found.");
        }

        entityManager.remove(post);
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
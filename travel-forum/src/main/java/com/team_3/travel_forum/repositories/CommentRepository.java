package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> get();

    Comment get(int id);

    void create(Comment comment);

    void update(Comment comment);

    void delete(int id);

    List<Comment> searchByPost(int postId);

    List<Comment> searchByUser(int userId);

    long countByPost(int postId);
}
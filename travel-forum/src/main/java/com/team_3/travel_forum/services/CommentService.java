package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.User;

import java.util.List;

public interface CommentService {

    Comment get(int id);

    List<Comment> getByPost(int postId, User currentUser);

    List<Comment> getByUser(int userId, User currentUser);

    void create(Comment comment, User currentUser);

    void update(Comment comment, User currentUser);

    void delete(int commentId, User currentUser);

    long countByPost(int postId);
}
package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.User;

import java.util.List;

public interface CommentService {

    Comment get(int id);

    void create(String content, int postId, User currentUser);

    void update(int commentId, String updatedContent, User currentUser);

    void delete(int commentId, User currentUser);

    List<Comment> getCommentsByPost(int postId, User currentUser);

    List<Comment> getCommentsByUser(int userId, User currentUser);

    long countByPost(int postId);
}
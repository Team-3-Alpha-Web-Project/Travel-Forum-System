package com.team_3.travel_forum.services;

import com.team_3.travel_forum.exceptions.UnauthorisedAccessException;
import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.Role;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.CommentRepository;
import com.team_3.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> get() {
        return commentRepository.get();
    }

    @Override
    public Comment get(int id) {
        return commentRepository.get(id);
    }

    @Override
    public void create(String content, int postId, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorisedAccessException("You must be logged in to post a comment.");
        }

        if (currentUser.isBlocked()) {
            throw new UnauthorisedAccessException("Your account has been blocked and you cannot post comments.");
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(currentUser);
        //TODO comment.setPost

        commentRepository.create(comment);
    }

    @Override
    public void update(int commentId, String updatedContent, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorisedAccessException("You must be logged in to edit a comment.");
        }

        Comment currentComment = commentRepository.get(commentId);

        if (currentComment.getUser().getId() != currentUser.getId() && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorisedAccessException("You do not have permission to edit this comment.");
        }

        currentComment.setContent(updatedContent);
        commentRepository.update(currentComment);
    }

    @Override
    public void delete(int commentId, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorisedAccessException("You must be logged in to delete a comment.");
        }

        Comment commentToDelete = commentRepository.get(commentId);
        if (commentToDelete.getUser().getId() != currentUser.getId() && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorisedAccessException("You do not have permission to delete this comment.");
        }

        commentRepository.delete(commentId);
    }

    @Override
    public List<Comment> getCommentsByPost(int postId, User currentUser) {
        //TODO check if post exists
        return commentRepository.searchByPost(postId);
    }

    @Override
    public List<Comment> getCommentsByUser(int userId, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorisedAccessException("You must be logged in view a user's comments.");
        }

        userRepository.get(userId);

        return commentRepository.searchByUser(userId);
    }

    @Override
    public long countByPost(int postId) {
        //TODO check post exists
        //postRepository...

        return commentRepository.countByPost(postId);
    }
}

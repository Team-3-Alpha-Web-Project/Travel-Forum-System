package com.team_3.travel_forum.services;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.Role;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.CommentRepository;
import com.team_3.travel_forum.repositories.PostRepository;
import com.team_3.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment get(int id) {
        return commentRepository.get(id);
    }

    @Override
    public List<Comment> getByPost(int postId, User currentUser) {
       postRepository.get(postId);

        return commentRepository.searchByPost(postId);
    }

    @Override
    public List<Comment> getByUser(int userId, User currentUser) {
        userRepository.get(userId);

        return commentRepository.searchByUser(userId);
    }

    @Override
    public void create(Comment comment, User currentUser) {
        if (currentUser.isBlocked()) {
            throw new BlockedUserException("Your account has been blocked and you cannot post comments.");
        }

        commentRepository.create(comment);
    }

    @Override
    public void update(Comment comment, User currentUser) {
        if (currentUser.isBlocked()) {
            throw new BlockedUserException("Your account has been blocked and you cannot update comments.");
        }

        Comment existingComment = commentRepository.get(comment.getId());
        checkModifyPermissions(existingComment, currentUser);

        existingComment.setContent(comment.getContent());
        commentRepository.update(existingComment);
    }

    @Override
    public void delete(int commentId, User currentUser) {
        Comment commentToDelete = commentRepository.get(commentId);

        if (currentUser.isBlocked()) {
            throw new BlockedUserException("Your account has been blocked and you cannot delete comments.");
        }

        checkModifyPermissions(commentToDelete, currentUser);

        commentRepository.delete(commentId);
    }

    @Override
    public long countByPost(int postId) {
        postRepository.get(postId);

        return commentRepository.countByPost(postId);
    }

    private void checkModifyPermissions(Comment comment, User user) {
        boolean isOwner = comment.getUser().getId() == user.getId();
        boolean isAdmin = user.getRole() == Role.ROLE_ADMIN;

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedAccessException("You do not have permission to modify this comment.");
        }
    }
}
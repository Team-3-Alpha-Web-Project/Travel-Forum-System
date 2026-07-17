package com.team_3.travel_forum.services;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.Role;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> get() {
        return postRepository.get();
    }

    @Override
    public Post get(int id) {
        return postRepository.get(id);
    }

    @Override
    public List<Post> getByUser(int userId) {
        return postRepository.getByUser(userId);
    }

    @Override
    public List<Post> search(String keyword, String sortBy) {
        return postRepository.search(keyword, sortBy);
    }

    @Override
    public void create(Post post, User currentUser) {
        if (currentUser.isBlocked()) {
            throw new BlockedUserException("Blocked users cannot create posts.");
        }
        post.setUser(currentUser);
        postRepository.create(post);
    }

    @Override
    public void update(Post post, User currentUser) {
        Post existing = postRepository.get(post.getId());
        checkModifyPermissions(existing, currentUser);

        existing.setTitle(post.getTitle());
        existing.setContent(post.getContent());
        postRepository.update(existing);
    }

    @Override
    public void delete(int id, User currentUser) {
        Post existing = postRepository.get(id);
        checkModifyPermissions(existing, currentUser);
        postRepository.delete(id);
    }

    @Override
    public long countAllPosts() {
        return postRepository.countAllPosts();
    }

    @Override
    public List<Post> getTop10MostCommented() {
        return postRepository.getTop10MostCommented();
    }

    @Override
    public List<Post> getTop10Recent() {
        return postRepository.getTop10Recent();
    }

    private void checkModifyPermissions(Post post, User user) {
        boolean isOwner = post.getUser().getId() == user.getId();
        boolean isAdmin = user.getRole() == Role.ROLE_ADMIN;

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedAccessException("You do not have permission to modify this post.");
        }
    }
}
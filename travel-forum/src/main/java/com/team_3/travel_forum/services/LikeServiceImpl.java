package com.team_3.travel_forum.services;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.Like;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.LikeRepository;
import com.team_3.travel_forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addLike(int postId, User currentUser) {
        if (currentUser.isBlocked()) {
            throw new BlockedUserException("Your account has been blocked and you cannot like posts.");
        }

        Post currentPost = postRepository.get(postId);

        if (likeRepository.findByPostAndUser(postId, currentUser.getId()) != null) {
            return;
        }

        Like newLike = new Like();
        newLike.setUser(currentUser);

        newLike.setPost(currentPost);

        likeRepository.create(newLike);

    }

    @Override
    public void removeLike(int postId, User currentUser) {
        if (currentUser.isBlocked()) {
            throw new BlockedUserException("Your account has been blocked and you cannot unlike posts.");
        }

        postRepository.get(postId);

        Like existingLike = likeRepository.findByPostAndUser(postId, currentUser.getId());
        if (existingLike == null) {
            throw new EntityNotFoundException("You have not liked this post yet.");
        }

        likeRepository.delete(existingLike.getId());
    }

    @Override
    public long countByPost(int postId) {
        postRepository.get(postId);

        return likeRepository.countByPost(postId);
    }
}
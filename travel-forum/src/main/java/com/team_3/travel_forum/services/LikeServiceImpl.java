package com.team_3.travel_forum.services;

import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.exceptions.UnauthorisedAccessException;
import com.team_3.travel_forum.models.Like;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void addLike(int postId, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorisedAccessException("You must be logged in to like a post.");
        }

        //TODO discuss if blocked users should be able to like posts
        if (currentUser.isBlocked()) {
            throw new UnauthorisedAccessException("Your account has been blocked and you cannot like posts.");
        }

        //need Post class
        //Post currentPost = postRepository.get(postId);

        if (likeRepository.findByPostAndUser(postId, currentUser.getId()) != null) {
            return; //post already liked, nothing to do
        }

        Like newLike = new Like();
        newLike.setUser(currentUser);
        // need Post class
//        newLike.setPost(currentPost);

        likeRepository.create(newLike);

    }

    @Override
    public void removeLike(int postId, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorisedAccessException("You must be logged in to unlike a post.");
        }
        //TODO discuss if blocked users should be able to like posts
        if (currentUser.isBlocked()) {
            throw new UnauthorisedAccessException("Your account has been blocked and you cannot unlike posts.");
        }

        //need Post class
//        Post post = postRepository.get(postId);

        Like existingLike = likeRepository.findByPostAndUser(postId, currentUser.getId());
        if (existingLike == null) {
            throw new EntityNotFoundException("You have not liked this post yet.");
        }

        likeRepository.delete(existingLike.getId());
    }

    @Override
    public long getLikeCountForPost(int postId) {
        //need Post class
//        postRepository.get(postId);
        return likeRepository.countByPost(postId);
    }
}

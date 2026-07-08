package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.User;

public interface LikeService {

    void addLike(int postId, User currentUser);

    void removeLike(int postId, User currentUser);

    long getLikeCountForPost(int postId);
}

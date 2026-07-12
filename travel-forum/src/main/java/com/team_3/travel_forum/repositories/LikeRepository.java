package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.Like;

public interface LikeRepository {

    Like get(int id);

    void create(Like like);

    void delete(int id);

    Like findByPostAndUser(int postId, int userId);

    long countByPost(int postId);

}
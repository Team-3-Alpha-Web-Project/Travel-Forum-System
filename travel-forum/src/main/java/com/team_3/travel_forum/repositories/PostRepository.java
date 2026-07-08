package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.Post;

import java.util.List;

public interface PostRepository {

    List<Post> get();

    Post get(int id);

    List<Post> getByUser(int userId);

    List<Post> search(String keyword, String sortBy);

    void create(Post post);

    void update(Post post);

    void delete(int id);
}
package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;

import java.util.List;

public interface PostService {

    List<Post> get();

    Post get(int id);

    List<Post> getByUser(int userId);

    List<Post> search(String keyword, String sortBy);

    void create(Post post, User currentUser);

    void update(Post post, User currentUser);

    void delete(int id, User currentUser);

    long countAllPosts();

    List<Post> getTop10MostCommented();

    List<Post> getTop10Recent();
}
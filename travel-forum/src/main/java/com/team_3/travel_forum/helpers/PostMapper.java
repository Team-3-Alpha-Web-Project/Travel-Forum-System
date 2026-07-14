package com.team_3.travel_forum.helpers;

import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.dtos.PostRequestDTO;
import com.team_3.travel_forum.models.dtos.PostResponseDTO;
import com.team_3.travel_forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    private final PostService postService;

    @Autowired
    public PostMapper(PostService postService) {
        this.postService = postService;
    }

    public Post fromDto(PostRequestDTO dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return post;
    }


    public Post fromDto(int id, PostRequestDTO dto) {
        Post post = postService.get(id);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return post;
    }

    public PostResponseDTO toDto(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setAuthorUsername(post.getUser().getUsername());

        // TODO: wire up once Comment/PostLike services exist
        dto.setLikesCount(0);
        dto.setCommentsCount(0);

        return dto;
    }

    public List<PostResponseDTO> toDto(List<Post> posts) {
        List<PostResponseDTO> postsDto = new ArrayList<>();

        for (Post post : posts) {
            postsDto.add(toDto(post));
        }

        return postsDto;
    }
}
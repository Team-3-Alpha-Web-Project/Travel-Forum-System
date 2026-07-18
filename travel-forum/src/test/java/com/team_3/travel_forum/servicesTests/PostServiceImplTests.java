package com.team_3.travel_forum.servicesTests;

import static com.team_3.travel_forum.Helpers.*;
import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.PostRepository;
import com.team_3.travel_forum.services.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTests {

    @Mock
    PostRepository mockPostRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    public void create_Should_CallRepository_When_UserIsNotBlocked() {
        User mockUser = createMockUser();
        Post mockPost = createMockPost(mockUser);

        postService.create(mockPost, mockUser);

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .create(mockPost);

        Assertions.assertEquals(mockUser, mockPost.getUser());
    }

    @Test
    public void create_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setBlocked(true);
        Post mockPost = createMockPost(mockUser);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> postService.create(mockPost, mockUser)
        );

        Mockito.verify(mockPostRepository, Mockito.never())
                .create(Mockito.any(Post.class));
    }

    @Test
    public void update_Should_CallRepository_When_UserIsOwner() {
        User owner = createMockUser();
        Post existingPost = createMockPost(owner);

        Post updatedPost = createMockPost(owner);
        updatedPost.setId(existingPost.getId());
        updatedPost.setTitle("Updated post title");
        updatedPost.setContent("Updated post content");

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        postService.update(updatedPost, owner);

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .update(existingPost);

        Assertions.assertEquals("Updated post title", existingPost.getTitle());
        Assertions.assertEquals("Updated post content", existingPost.getContent());
    }

    @Test
    public void update_Should_CallRepository_When_UserIsAdmin() {
        User owner = createMockUser();
        User admin = createMockAdmin();
        Post existingPost = createMockPost(owner);

        Post updatedPost = createMockPost(owner);
        updatedPost.setId(existingPost.getId());
        updatedPost.setTitle("Updated post title");
        updatedPost.setContent("Updated post content");

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        postService.update(updatedPost, admin);

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .update(existingPost);
    }

    @Test
    public void update_Should_ThrowException_When_UserIsNotOwnerOrAdmin() {
        User owner = createMockUser();
        User otherUser = createOtherUser();
        Post existingPost = createMockPost(owner);

        Post updatedPost = createMockPost(owner);
        updatedPost.setId(existingPost.getId());

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        Assertions.assertThrows(
                UnauthorizedAccessException.class,
                () -> postService.update(updatedPost, otherUser)
        );

        Mockito.verify(mockPostRepository, Mockito.never())
                .update(Mockito.any(Post.class));
    }

    @Test
    public void delete_Should_CallRepository_When_UserIsOwner() {
        User owner = createMockUser();
        Post existingPost = createMockPost(owner);

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        postService.delete(1, owner);

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .delete(1);
    }

    @Test
    public void delete_Should_CallRepository_When_UserIsAdmin() {
        User owner = createMockUser();
        User admin = createMockAdmin();
        Post existingPost = createMockPost(owner);

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        postService.delete(1, admin);

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .delete(1);
    }

    @Test
    public void delete_Should_ThrowException_When_UserIsNotOwnerOrAdmin() {
        User owner = createMockUser();
        User otherUser = createOtherUser();
        Post existingPost = createMockPost(owner);

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        Assertions.assertThrows(
                UnauthorizedAccessException.class,
                () -> postService.delete(1, otherUser)
        );

        Mockito.verify(mockPostRepository, Mockito.never())
                .delete(Mockito.anyInt());
    }
}

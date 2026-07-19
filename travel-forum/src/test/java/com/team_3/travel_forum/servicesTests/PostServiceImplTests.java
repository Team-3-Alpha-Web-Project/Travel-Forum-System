package com.team_3.travel_forum.servicesTests;

import static com.team_3.travel_forum.Helpers.*;
import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
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

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTests {

    @Mock
    PostRepository mockPostRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    public void get_Should_ReturnAllPosts() {
        List<Post> mockPosts = List.of(createMockPost());
        Mockito.when(mockPostRepository.get())
                .thenReturn(mockPosts);

        Assertions.assertEquals(mockPosts, postService.get());
    }

    @Test
    public void get_Should_ReturnPost_When_PostExists() {
        Post mockPost = createMockPost();
        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                        .thenReturn(mockPost);

        Assertions.assertEquals(mockPost, postService.get(1));

    }

    @Test
    public void get_Should_Throw_When_Post_NotExists() {
        Mockito.when(mockPostRepository.get(1))
                .thenThrow(new EntityNotFoundException("Post"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> postService.get(1));

    }

    @Test
    public void get_By_User_Should_ReturnPost_When_PostExists() {
        User mockUser = createMockUser();
        List<Post> mockPosts = List.of(createMockPost());

        Mockito.when(mockPostRepository.getByUser(mockUser.getId()))
                .thenReturn(mockPosts);

        Assertions.assertEquals(mockPosts, postService.getByUser(mockUser.getId()));

    }

    @Test
    public void search_Should_ReturnPosts_When_KeywordExists() {
        List<Post> mockPosts = List.of(createMockPost());

        Mockito.when(mockPostRepository.search("London", "mostLiked"))
                .thenReturn(mockPosts);

        Assertions.assertEquals(mockPosts, postService.search("London", "mostLiked"));
    }


    @Test
    public void create_Should_CallRepository_When_UserIsNotBlocked() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        Post mockPost = createMockPost(mockUser);

        postService.create(mockPost, mockUser);

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .create(mockPost);

        Assertions.assertEquals(mockUser, mockPost.getUser());
    }

    @Test
    public void create_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setId(5);
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
        owner.setId(5);
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

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .update(existingPost);
    }

    @Test
    public void update_Should_CallRepository_When_UserIsAdmin() {
        User owner = createMockUser();
        owner.setId(5);
        User admin = createMockAdmin();
        admin.setId(10);
        Post existingPost = createMockPost(owner);

        Post updatedPost = createMockPost(owner);
        updatedPost.setId(existingPost.getId());
        updatedPost.setTitle("Updated post title");
        updatedPost.setContent("Updated post content");

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(existingPost);

        postService.update(updatedPost, admin);

        Assertions.assertEquals("Updated post title", existingPost.getTitle());
        Assertions.assertEquals("Updated post content", existingPost.getContent());

        Mockito.verify(mockPostRepository, Mockito.times(1))
                .update(existingPost);
    }

    @Test
    public void update_Should_ThrowException_When_UserIsNotOwnerOrAdmin() {
        User owner = createMockUser();
        owner.setId(5);
        User otherUser = createOtherUser();
        otherUser.setId(10);
        Post existingPost = createMockPost(owner);

        Post updatedPost = createMockPost(owner);
        updatedPost.setId(existingPost.getId());
        updatedPost.setTitle("Updated post title");
        updatedPost.setContent("Updated post content");

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
    public void update_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        mockUser.setBlocked(true);

        Post existingPost = createMockPost(mockUser);
        Post updatedPost = createMockPost(mockUser);
        updatedPost.setId(existingPost.getId());
        updatedPost.setTitle("Updated post title");
        updatedPost.setContent("Updated post content");

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> postService.update(updatedPost, mockUser)
        );

        Mockito.verify(mockPostRepository, Mockito.never())
                .update(Mockito.any(Post.class));
    }

    @Test
    public void delete_Should_CallRepository_When_UserIsOwner() {
        User owner = createMockUser();
        owner.setId(5);
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
        owner.setId(5);
        User admin = createMockAdmin();
        admin.setId(10);
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
        owner.setId(5);
        User otherUser = createOtherUser();
        otherUser.setId(10);
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

    @Test
    public void delete_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        mockUser.setBlocked(true);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> postService.delete(1, mockUser)
        );

        Mockito.verify(mockPostRepository, Mockito.never())
                .delete(Mockito.anyInt());
    }

    @Test
    public void countAll_Posts_Should_Return_Valid_Number() {
        List<Post> mockPosts = List.of(createMockPost());
        Mockito.when(mockPostRepository.countAllPosts())
                .thenReturn((long) mockPosts.size());

        Assertions.assertEquals(mockPosts.size(), postService.countAllPosts());
    }

    @Test
    public void countAllPosts_Should_Return_0_When_NoPostsExist() {
        long expectedCount = 0L;
        Mockito.when(mockPostRepository.countAllPosts()).thenReturn(expectedCount);

        long actualCount = postService.countAllPosts();

        Assertions.assertEquals(expectedCount, actualCount);
        Mockito.verify(mockPostRepository,
                Mockito.times(1))
                .countAllPosts();
    }

    @Test
    public void getTop10MostCommented_Should_ReturnListOfPosts_When_PostsExist() {
        Post mockPost1 = createMockPost();
        Post mockPost2 = createMockPost();
        List<Post> mockList = new ArrayList<>();
        mockList.add(mockPost1);
        mockList.add(mockPost2);

        Mockito.when(mockPostRepository.getTop10MostCommented()).thenReturn(mockList);

        List<Post> actualList = postService.getTop10MostCommented();

        Assertions.assertEquals(mockList, actualList);
        Mockito.verify(mockPostRepository,
                Mockito.times(1))
                .getTop10MostCommented();
    }

    @Test
    public void getTop10MostCommented_Should_ReturnEmptyList_When_NoPosts() {
        List<Post> mockList = new ArrayList<>();

        Mockito.when(mockPostRepository.getTop10MostCommented()).thenReturn(mockList);

        List<Post> actualList = postService.getTop10MostCommented();

        Assertions.assertTrue(actualList.isEmpty());
        Mockito.verify(mockPostRepository,
                        Mockito.times(1))
                .getTop10MostCommented();
    }

    @Test
    public void getTop10Recent_Should_ReturnListOfPosts_When_PostsExist() {
        Post mockPost1 = createMockPost();
        Post mockPost2 = createMockPost();
        List<Post> mockList = new ArrayList<>();
        mockList.add(mockPost1);
        mockList.add(mockPost2);

        Mockito.when(mockPostRepository.getTop10Recent()).thenReturn(mockList);

        List<Post> actualList = postService.getTop10Recent();

        Assertions.assertEquals(mockList, actualList);
        Mockito.verify(mockPostRepository,
                        Mockito.times(1))
                .getTop10Recent();
    }

    @Test
    public void getTop10Recent_Should_ReturnEmptyList_When_NoPostsExist() {
        List<Post> mockList = new ArrayList<>();

        Mockito.when(mockPostRepository.getTop10Recent()).thenReturn(mockList);

        List<Post> actualList = postService.getTop10Recent();

        Assertions.assertTrue(actualList.isEmpty());
        Mockito.verify(mockPostRepository,
                        Mockito.times(1))
                .getTop10Recent();
    }
}

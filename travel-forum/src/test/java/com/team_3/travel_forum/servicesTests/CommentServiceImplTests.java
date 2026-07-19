package com.team_3.travel_forum.servicesTests;

import static com.team_3.travel_forum.Helpers.*;
import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.CommentRepository;
import com.team_3.travel_forum.repositories.PostRepository;
import com.team_3.travel_forum.repositories.UserRepository;
import com.team_3.travel_forum.services.CommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTests {

    @Mock
    CommentRepository mockCommentRepository;

    @Mock
    UserRepository mockUserRepository;

    @Mock
    PostRepository mockPostRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    public void get_Should_ReturnComment_When_CommentExists() {
        User owner = createMockUser();
        owner.setId(5);
        Comment mockComment = createMockComment(createMockUser());

        Mockito.when(mockCommentRepository.get(Mockito.anyInt()))
                .thenReturn(mockComment);

        Comment result = commentService.get(1);

        Assertions.assertEquals(mockComment, result);
    }

    @Test
    public void get_Should_ThrowException_When_CommentDoesNotExist() {
        int commentId = 300;
        Mockito.when(mockCommentRepository.get(commentId))
                .thenThrow(new EntityNotFoundException("Comment"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.get(commentId));
    }

    @Test
    public void getByPost_Should_ReturnComments_When_PostExists() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        Post mockPost = createMockPost(mockUser);
        List<Comment> comments = List.of(createMockComment(mockUser));

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockCommentRepository.searchByPost(Mockito.anyInt()))
                .thenReturn(comments);

        List<Comment> result = commentService.getByPost(1, mockUser);

        Assertions.assertEquals(comments, result);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .searchByPost(1);
    }

    @Test
    public void getByPost_Should_ThrowException_When_PostDoesNotExist() {
        int postId = 300;
        User mockUser = createMockUser();
        mockUser.setId(5);
        Mockito.when(mockPostRepository.get(postId))
                .thenThrow(new EntityNotFoundException("Post"));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> commentService.getByPost(postId, mockUser));
    }

    @Test
    public void getByUser_Should_ReturnComments_When_UserExists() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        List<Comment> comments = List.of(createMockComment(mockUser));

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        Mockito.when(mockCommentRepository.searchByUser(Mockito.anyInt()))
                .thenReturn(comments);

        List<Comment> result = commentService.getByUser(1, mockUser);

        Assertions.assertEquals(comments, result);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .searchByUser(1);
    }

    @Test
    public void getByUser_Should_ThrowException_When_UserDoesNotExist() {
        int userId = 5;
        User mockUser = createMockUser();
        mockUser.setId(userId);
        Mockito.when(mockUserRepository.get(userId)).thenThrow(new EntityNotFoundException("User"));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> commentService.getByUser(userId, mockUser));
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get(userId);
    }

    @Test
    public void create_Should_CallRepository_When_UserIsNotBlocked() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        Comment mockComment = createMockComment(mockUser);

        commentService.create(mockComment, mockUser);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .create(mockComment);
    }

    @Test
    public void create_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        mockUser.setBlocked(true);
        Comment mockComment = createMockComment(mockUser);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> commentService.create(mockComment, mockUser)
        );

        Mockito.verify(mockCommentRepository, Mockito.never())
                .create(Mockito.any(Comment.class));
    }

    @Test
    public void update_Should_CallRepository_When_UserIsOwner() {
        User owner = createMockUser();
        owner.setId(5);
        Comment mockComment = createMockComment(owner);
        mockComment.setContent("Updated comment");

        commentService.update(mockComment, owner);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .update(mockComment);
    }

    @Test
    public void update_Should_CallRepository_When_UserIsAdmin() {
        User owner = createMockUser();
        owner.setId(5);
        User admin = createMockAdmin();
        admin.setId(10);
        Comment mockComment = createMockComment(owner);
        mockComment.setContent("Updated comment");

        commentService.update(mockComment, admin);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .update(mockComment);
    }

    @Test
    public void update_Should_ThrowException_When_UserIsNotOwnerOrAdmin() {
        User owner = createMockUser();
        owner.setId(5);
        User otherUser = createOtherUser();
        otherUser.setId(10);
        Comment mockComment = createMockComment(owner);

        Assertions.assertThrows(
                UnauthorizedAccessException.class,
                () -> commentService.update(mockComment, otherUser)
        );

        Mockito.verify(mockCommentRepository, Mockito.never())
                .update(Mockito.any(Comment.class));
    }

    @Test
    public void update_Should_ThrowException_When_UserIsBlocked() {
        User owner = createMockUser();
        owner.setId(5);
        owner.setBlocked(true);
        Comment mockComment = createMockComment(owner);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> commentService.update(mockComment, owner)
        );

        Mockito.verify(mockCommentRepository, Mockito.never())
                .update(Mockito.any(Comment.class));
    }

    @Test
    public void delete_Should_CallRepository_When_UserIsOwner() {
        User owner = createMockUser();
        owner.setId(5);
        Comment mockComment = createMockComment(owner);

        Mockito.when(mockCommentRepository.get(Mockito.anyInt()))
                .thenReturn(mockComment);

        commentService.delete(1, owner);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .delete(1);
    }

    @Test
    public void delete_Should_CallRepository_When_UserIsAdmin() {
        User owner = createMockUser();
        owner.setId(5);
        User admin = createMockAdmin();
        admin.setId(10);
        Comment mockComment = createMockComment(owner);

        Mockito.when(mockCommentRepository.get(Mockito.anyInt()))
                .thenReturn(mockComment);

        commentService.delete(1, admin);

        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .delete(1);
    }

    @Test
    public void delete_Should_ThrowException_When_UserIsNotOwnerOrAdmin() {
        User owner = createMockUser();
        owner.setId(5);
        User otherUser = createOtherUser();
        otherUser.setId(10);
        Comment mockComment = createMockComment(owner);

        Mockito.when(mockCommentRepository.get(Mockito.anyInt()))
                .thenReturn(mockComment);

        Assertions.assertThrows(
                UnauthorizedAccessException.class,
                () -> commentService.delete(1, otherUser)
        );

        Mockito.verify(mockCommentRepository, Mockito.never())
                .delete(Mockito.anyInt());
    }

    @Test
    public void delete_Should_ThrowException_When_UserIsBlocked() {
        User owner = createMockUser();
        owner.setId(5);
        owner.setBlocked(true);
        Comment mockComment = createMockComment(owner);

        Mockito.when(mockCommentRepository.get(Mockito.anyInt()))
                .thenReturn(mockComment);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> commentService.delete(1, owner)
        );

        Mockito.verify(mockCommentRepository, Mockito.never())
                .delete(Mockito.anyInt());
    }

    @Test
    public void countByPost_Should_ReturnCount_When_PostExists() {
        Post mockPost = createMockPost(createMockUser());

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockCommentRepository.countByPost(Mockito.anyInt()))
                .thenReturn(3L);

        long result = commentService.countByPost(1);

        Assertions.assertEquals(3L, result);
    }

    @Test
    public void countByPost_Should_ThrowException_When_PostDoesNotExist() {
        int postId = 500;

        Mockito.when(mockPostRepository.get(postId))
                .thenThrow(new EntityNotFoundException("Post"));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> commentService.countByPost(postId));
        Mockito.verify(mockCommentRepository, Mockito.never())
                .countByPost(postId);
    }

    @Test
    public void countByPost_Should_Return0_When_NoCommentsExist() {
        Post mockPost = createMockPost(createMockUser());
        long expectedCount = 0L;

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockCommentRepository.countByPost(Mockito.anyInt()))
                .thenReturn(expectedCount);

        long result = commentService.countByPost(1);

        Assertions.assertEquals(expectedCount, result);
    }
}

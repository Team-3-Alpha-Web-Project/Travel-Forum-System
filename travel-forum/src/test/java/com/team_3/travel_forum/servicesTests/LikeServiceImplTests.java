package com.team_3.travel_forum.servicesTests;

import static com.team_3.travel_forum.Helpers.*;
import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.Like;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.LikeRepository;
import com.team_3.travel_forum.repositories.PostRepository;
import com.team_3.travel_forum.services.LikeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LikeServiceImplTests {

    @Mock
    LikeRepository mockLikeRepository;

    @Mock
    PostRepository mockPostRepository;

    @InjectMocks
    LikeServiceImpl likeService;

    @Test
    public void addLike_Should_CallRepository_When_UserHasNotLikedPostYet() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        Post mockPost = createMockPost();

        Mockito.when(mockPostRepository.get(9))
                .thenReturn(mockPost);

        Mockito.when(mockLikeRepository.findByPostAndUser(9, mockUser.getId()))
                .thenReturn(null);

        likeService.addLike(9, mockUser);

        Mockito.verify(mockLikeRepository, Mockito.times(1))
                .create(Mockito.any(Like.class));
    }

    @Test
    public void addLike_Should_NotCreateLike_When_UserAlreadyLikedPost() {
        User mockUser = createMockUser();
        mockUser.setId(5);
        Post mockPost = createMockPost();

        Like existingLike = new Like();
        existingLike.setId(1);

        Mockito.when(mockPostRepository.get(9))
                .thenReturn(mockPost);

        Mockito.when(mockLikeRepository.findByPostAndUser(9, mockUser.getId()))
                .thenReturn(existingLike);

        likeService.addLike(9, mockUser);

        Mockito.verify(mockLikeRepository, Mockito.never())
                .create(Mockito.any(Like.class));
    }

    @Test
    public void addLike_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setBlocked(true);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> likeService.addLike(1, mockUser)
        );

        Mockito.verifyNoInteractions(mockPostRepository);
        Mockito.verifyNoInteractions(mockLikeRepository);
    }

    @Test
    public void removeLike_Should_CallRepository_When_LikeExists() {
        User mockUser = createMockUser();
        mockUser.setId(5);

        Like existingLike = new Like();
        existingLike.setId(1);

        Mockito.when(mockLikeRepository.findByPostAndUser(9, mockUser.getId()))
                .thenReturn(existingLike);

        likeService.removeLike(9, mockUser);

        Mockito.verify(mockLikeRepository, Mockito.times(1))
                .delete(existingLike.getId());
    }

    @Test
    public void removeLike_Should_ThrowException_When_LikeDoesNotExist() {
        User mockUser = createMockUser();
        mockUser.setId(5);

        Mockito.when(mockLikeRepository.findByPostAndUser(9, mockUser.getId()))
                .thenReturn(null);

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> likeService.removeLike(9, mockUser)
        );

        Mockito.verify(mockLikeRepository, Mockito.never())
                .delete(Mockito.anyInt());
    }

    @Test
    public void removeLike_Should_ThrowException_When_UserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setBlocked(true);

        Assertions.assertThrows(
                BlockedUserException.class,
                () -> likeService.removeLike(1, mockUser)
        );

        Mockito.verifyNoInteractions(mockPostRepository);
        Mockito.verifyNoInteractions(mockLikeRepository);
    }

    @Test
    public void countByPost_Should_ReturnCount_When_PostExists() {
        Post mockPost = createMockPost();

        Mockito.when(mockPostRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockLikeRepository.countByPost(Mockito.anyInt()))
                .thenReturn(5L);

        long result = likeService.countByPost(1);

        Assertions.assertEquals(5L, result);
    }
}

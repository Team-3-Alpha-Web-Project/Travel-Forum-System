TBD

#TravelTipster

## File Structure

```text
travel-forum/
|   .gitattributes
|   .gitignore
|   build.gradle
|   gradlew
|   gradlew.bat
|   HELP.md
|   qodana.yaml
|   README.md
|   settings.gradle
|   
+---db
|       create.sql
|       insert-data.sql
|       
+---gradle
|   \---wrapper
|           gradle-wrapper.jar
|           gradle-wrapper.properties
|           
\---src
    +---main
    |   +---java
    |   |   \---com
    |   |       \---team_3
    |   |           \---travel_forum
    |   |               |   TravelForumApplication.java
    |   |               |   
    |   |               +---config
    |   |               |       HibernateConfig.java
    |   |               |       SecurityConfig.java
    |   |               |       
    |   |               +---controllers
    |   |               |   +---mvc
    |   |               |   |       CommentMvcController.java
    |   |               |   |       HomeMvcController.java
    |   |               |   |       TestMvcController.java
    |   |               |   |       
    |   |               |   \---rest
    |   |               |           AuthController.java
    |   |               |           CommentRestController.java
    |   |               |           LikeRestController.java
    |   |               |           PostRestController.java
    |   |               |           UserRestController.java
    |   |               |           
    |   |               +---exceptions
    |   |               |       BlockedUserException.java
    |   |               |       DuplicateEntityException.java
    |   |               |       EntityNotFoundException.java
    |   |               |       GlobalMvcExceptionsHandler.java
    |   |               |       GlobalRestExceptionsHandler.java
    |   |               |       UnauthorizedAccessException.java
    |   |               |       
    |   |               +---helpers
    |   |               |       CommentMapper.java
    |   |               |       PostMapper.java
    |   |               |       UserMapper.java
    |   |               |       
    |   |               +---models
    |   |               |   |   Comment.java
    |   |               |   |   Like.java
    |   |               |   |   Post.java
    |   |               |   |   Role.java
    |   |               |   |   User.java
    |   |               |   |       
    |   |               |   \---dtos
    |   |               |           ChangePasswordDto.java
    |   |               |           CommentRequestDto.java
    |   |               |           CommentResponseDto.java
    |   |               |           PostRequestDTO.java
    |   |               |           PostResponseDTO.java
    |   |               |           RegisterUserDto.java
    |   |               |           UpdateUserDto.java
    |   |               |           UserResponseDto.java
    |   |               |           
    |   |               +---repositories
    |   |               |       CommentRepository.java
    |   |               |       CommentRepositoryImpl.java
    |   |               |       LikeRepository.java
    |   |               |       LikeRepositoryImpl.java
    |   |               |       PostRepository.java
    |   |               |       PostRepositoryImpl.java
    |   |               |       UserRepository.java
    |   |               |       UserRepositoryImpl.java
    |   |               |       
    |   |               \---services
    |   |                       CommentService.java
    |   |                       CommentServiceImpl.java
    |   |                       LikeService.java
    |   |                       LikeServiceImpl.java
    |   |                       PostService.java
    |   |                       PostServiceImpl.java
    |   |                       SecurityUserDetailsService.java
    |   |                       UserService.java
    |   |                       UserServiceImpl.java
    |   |                       
    |   \---resources
    |       |   application.properties
    |       |   messages.properties
    |       |   
    |       +---static
    |       |   \---css
    |       |           styles.css
    |       |           
    |       \---templates
    |           |   comment-details.html
    |           |   create-comment.html
    |           |   error-page.html
    |           |   home-page.html
    |           |   home.html
    |           |   login.html
    |           |   
    |           \---fragments
    |                   page-layout.html
    |                   
    \---test
        \---java
            \---com
                \---team_3
                    \---travel_forum
                        |   HashGenerator.java
                        |   Helpers.java
                        |   TravelForumApplicationTests.java
                        |   
                        \---servicesTests
                                CommentServiceImplTests.java
                                LikeServiceImplTests.java
                                PostServiceImplTests.java
                                SecurityUserDetailsServiceTests.java
                                UserServiceImplTests.java
```
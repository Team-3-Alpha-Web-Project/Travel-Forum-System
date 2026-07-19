# TravelTalk (Travel Forum System)

TravelTalk is a Spring Boot travel forum where users share posts about their trips, comment on and like each other's posts, and manage their profiles. The application is built with Spring Boot, Spring Security, Hibernate/JPA, and MariaDB, and it exposes both a REST API and a small MVC front end.

Built by **Team 3** — [@AllyVA](https://github.com/AllyVA), [@stefanova-kalina](https://github.com/stefanova-kalina), [@spassimirag](https://github.com/spassimirag),

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Database Schema](#database-schema)
- [API Documentation](#api-documentation)
- [Postman Collection](#postman-collection)
- [Local Setup](#local-setup)
- [Project Structure](#project-structure)
- [Testing](#testing)

---

## Features

### Post Management (`/api/posts`)
- Browse all posts ordered by most recent — `GET /api/posts`
- Retrieve a single post by ID — `GET /api/posts/{id}`
- Retrieve all posts by a specific user — `GET /api/posts/user/{userId}`
- Search posts by keyword (title and content) with optional sorting — `GET /api/posts/search?keyword=...&sortBy=...` (`mostLiked`, `mostCommented`, or most recent by default)
- Create a post — `POST /api/posts` (authenticated, blocked users cannot post)
- Update a post — `PUT /api/posts/{id}` (owner or admin only)
- Delete a post — `DELETE /api/posts/{id}` (owner or admin only)
- Top 10 most commented posts — `GET /api/posts/top-commented`
- Top 10 most recent posts — `GET /api/posts/recent`

### Comment Management
- Get a comment by ID — `GET /api/comments/{id}`
- Get all comments by a user — `GET /api/users/{userId}/comments`
- Get all comments on a post — `GET /api/posts/{postId}/comments`
- Create a comment on a post — `POST /api/posts/{postId}/comments` (authenticated, blocked users cannot comment)
- Update a comment — `PUT /api/comments/{id}` (owner or admin only, blocked users cannot edit)
- Delete a comment — `DELETE /api/comments/{id}` (owner or admin only, blocked users cannot delete)

### Like Management
- Like a post — `POST /api/posts/{postId}/likes` (authenticated, blocked users cannot like)
- Remove a like — `DELETE /api/posts/{postId}/likes` (authenticated, blocked users cannot unlike)
- Get like count on a post — `GET /api/posts/{postId}/likes/count`
- A user can like a post only once; likes cascade-delete with their post

### User Authentication & Management
- Register — `POST /api/auth/register`
- Log in — Spring Security form login via `/login`
- Log out — `/logout` (invalidates session, deletes `JSESSIONID`)
- Get current user's profile — `GET /api/users/me`
- Update current user's profile — `PUT /api/users/me`
- Change current user's password — `PATCH /api/users/me/password`
- Admin only: list all users, get by ID/username, search by username/email/first name, update any user, block/unblock, promote to admin
- User deletion is not exposed via the API — users are blocked instead

### DTOs & Mapping
- `UserResponseDto` (never exposes passwords), `UpdateUserDto`, `RegisterUserDto`, `ChangePasswordDto`, and a `UserMapper` handle conversion between `User` entities and DTOs. Equivalent DTO/mapper pairs exist for posts and comments.

### Global Exception Handling
- **REST controllers** — a global handler converts custom exceptions (`BlockedUserException`, `UnauthorizedAccessException`, `DuplicateEntityException`, `EntityNotFoundException`) plus validation and general exceptions into structured JSON error responses.
- **MVC controllers** — a separate global handler intercepts the same exception types and routes users to a friendly HTML error page.

### Frontend
- Dynamic home page using a shared layout fragment (`page-layout.html`) with a header/footer that adapts to the user's authentication and role.
- Login page and error page (rendered from the MVC exception handler).

### Security
- Role-based access control via Spring Security (`ROLE_USER`, `ROLE_ADMIN`)
- Public endpoints: home page, registration, Swagger UI, and selected read-only post/stats endpoints
- Authenticated-only and admin-only endpoints enforced through `SecurityFilterChain`
- Passwords encoded via Spring Security's `PasswordEncoder`

### Testing
- Unit tests cover the service layer: successful operations, duplicate-entity validation, blocked-user restrictions, and owner/admin permission logic

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.0 |
| Persistence | Spring Data JPA / Hibernate |
| Security | Spring Security |
| Database | MariaDB |
| Views | Thymeleaf |
| API Docs | springdoc-openapi (Swagger UI) |
| Build Tool | Gradle (wrapper included) |
| Testing | JUnit 5, Spring Security Test |

---

## Database Schema

![Entity-Relationship Diagram](docs/images/er-diagram.png)

The schema centers on a `users` table, with `posts`, `comments`, `likes`, and `phone_numbers` each referencing `user_id`. `comments` and `likes` also reference `post_id`, and both cascade-delete when their parent post is removed.

---

## API Documentation

Interactive Swagger UI (available once the app is running locally):

**http://localhost:8080/swagger-ui.html**

---

## Postman Collection

A ready-to-use Postman collection and environment are included under [`/postman`](./postman), covering the full request flow: public/registration endpoints, authentication boundaries, posts, comments, likes, and admin/user management.

- Collection: [`Travel-Forum-API.postman_collection.json`](./postman/Travel-Forum-API.postman_collection.json)
- Environment: [`Travel-Forum-Local.postman_environment.json`](./postman/Travel-Forum-Local.postman_environment.json)

**To use it:**
1. In Postman, go to **Import** and select both files.
2. Select the **Travel Forum - Local** environment from the environment dropdown (top right).
3. Make sure the app is running locally and the database has been seeded with `db/insert-data.sql` — the environment's demo credentials (e.g. `sofiaabroad`, `spasimiragenova`) correspond to users from that seed data.

> The environment file contains demo credentials for local seed data only — not real user credentials.

---

## Local Setup

### Prerequisites
- Java 17 (JDK)
- MariaDB Server running locally (or accessible remotely)
- No need to install Gradle separately — the project includes the Gradle wrapper (`gradlew` / `gradlew.bat`)

### 1. Clone the repository
```bash
git clone https://github.com/Team-3-Alpha-Web-Project/Travel-Forum-System.git
cd Travel-Forum-System
```

### 2. Set up the database
Run the schema script, then the seed data script, against your MariaDB server:

```bash
mysql -u root -p < db/create.sql
mysql -u root -p < db/insert-data.sql
```

This creates the `forum` database and its tables, and populates it with sample users, posts, comments, and likes — required if you want to try out the app (or use the Postman collection above).

### 3. Configure environment variables
The app connects to the database using the following variables:

| Variable | Required | Default |
|---|---|---|
| `DATABASE_URL` | No | `jdbc:mariadb://localhost:3306/forum` |
| `DATABASE_USERNAME` | No | `root` |
| `DATABASE_PASSWORD` | **Yes** | *(none — app will not start without it)* |

Set them in your shell before running the app, for example:

**macOS/Linux (bash/zsh):**
```bash
export DATABASE_PASSWORD=your_mariadb_password
```

**Windows (PowerShell):**
```powershell
$env:DATABASE_PASSWORD="your_mariadb_password"
```

Alternatively, set them as run configuration environment variables in your IDE (e.g. IntelliJ IDEA's Run/Debug Configurations).

### 4. Run the application
```bash
./gradlew bootRun
```
(Windows: `gradlew.bat bootRun`)

The app will start on **http://localhost:8080**.

### 5. Explore
- Home page: `http://localhost:8080/home`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Log in with a seeded account (see `db/insert-data.sql` or the Postman environment for demo usernames)

---

## Project Structure

```
travel-forum/
├── .gitattributes
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── HELP.md
├── qodana.yaml
├── README.md
├── settings.gradle
├── db/
│   ├── create.sql
│   └── insert-data.sql
├── postman/
│   ├── Travel-Forum-API.postman_collection.json
│   └── Travel-Forum-Local.postman_environment.json
├── docs/
│   └── images/
│       └── er-diagram.png
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── src/
    ├── main/
    │   ├── java/com/team_3/travel_forum/
    │   │   ├── TravelForumApplication.java
    │   │   ├── config/
    │   │   │   ├── HibernateConfig.java
    │   │   │   └── SecurityConfig.java
    │   │   ├── controllers/
    │   │   │   ├── mvc/
    │   │   │   │   ├── CommentMvcController.java
    │   │   │   │   ├── HomeMvcController.java
    │   │   │   │   └── TestMvcController.java
    │   │   │   └── rest/
    │   │   │       ├── AuthController.java
    │   │   │       ├── CommentRestController.java
    │   │   │       ├── LikeRestController.java
    │   │   │       ├── PostRestController.java
    │   │   │       └── UserRestController.java
    │   │   ├── exceptions/
    │   │   │   ├── BlockedUserException.java
    │   │   │   ├── DuplicateEntityException.java
    │   │   │   ├── EntityNotFoundException.java
    │   │   │   ├── GlobalMvcExceptionsHandler.java
    │   │   │   ├── GlobalRestExceptionsHandler.java
    │   │   │   └── UnauthorizedAccessException.java
    │   │   ├── helpers/
    │   │   │   ├── CommentMapper.java
    │   │   │   ├── PostMapper.java
    │   │   │   └── UserMapper.java
    │   │   ├── models/
    │   │   │   ├── Comment.java
    │   │   │   ├── Like.java
    │   │   │   ├── Post.java
    │   │   │   ├── Role.java
    │   │   │   ├── User.java
    │   │   │   └── dtos/
    │   │   │       ├── ChangePasswordDto.java
    │   │   │       ├── CommentRequestDto.java
    │   │   │       ├── CommentResponseDto.java
    │   │   │       ├── PostRequestDTO.java
    │   │   │       ├── PostResponseDTO.java
    │   │   │       ├── RegisterUserDto.java
    │   │   │       ├── UpdateUserDto.java
    │   │   │       └── UserResponseDto.java
    │   │   ├── repositories/
    │   │   │   ├── CommentRepository.java
    │   │   │   ├── CommentRepositoryImpl.java
    │   │   │   ├── LikeRepository.java
    │   │   │   ├── LikeRepositoryImpl.java
    │   │   │   ├── PostRepository.java
    │   │   │   ├── PostRepositoryImpl.java
    │   │   │   ├── UserRepository.java
    │   │   │   └── UserRepositoryImpl.java
    │   │   └── services/
    │   │       ├── CommentService.java
    │   │       ├── CommentServiceImpl.java
    │   │       ├── LikeService.java
    │   │       ├── LikeServiceImpl.java
    │   │       ├── PostService.java
    │   │       ├── PostServiceImpl.java
    │   │       ├── SecurityUserDetailsService.java
    │   │       ├── UserService.java
    │   │       └── UserServiceImpl.java
    │   └── resources/
    │       ├── application.properties
    │       ├── messages.properties
    │       ├── static/
    │       │   └── css/
    │       │       └── styles.css
    │       └── templates/
    │           ├── comment-details.html
    │           ├── create-comment.html
    │           ├── error-page.html
    │           ├── home-page.html
    │           ├── home.html
    │           ├── login.html
    │           └── fragments/
    │               └── page-layout.html
    └── test/
        └── java/com/team_3/travel_forum/
            ├── HashGenerator.java
            ├── Helpers.java
            ├── TravelForumApplicationTests.java
            └── servicesTests/
                ├── CommentServiceImplTests.java
                ├── LikeServiceImplTests.java
                ├── PostServiceImplTests.java
                ├── SecurityUserDetailsServiceTests.java
                └── UserServiceImplTests.java
```

---

## Testing

Run the unit test suite with:

```bash
./gradlew test
```

Service-layer tests cover successful operations, duplicate-entity validation, blocked-user restrictions, and owner/admin permission logic.
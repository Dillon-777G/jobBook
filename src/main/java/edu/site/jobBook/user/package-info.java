/************************************************************************************************************
 * This package handles user-related functionalities, including user management,                            *
 * activity tracking, and user authentication within the Spring Boot application.                           *
 ************************************************************************************************************
 * Main classes and their responsibilities:                                                                 *
 *                                                                                                          *
 * AppUser:                                                                                                 *
 * <ul>                                                                                                     *
 * <li>Represents the user entity with fields such as id, username, password, and roles.</li>               *
 * <li>Implements UserDetails interface for Spring Security integration.</li>                               *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * AppUserController/AppUserAPIController:                                                                                       *
 * <ul>                                                                                                     *
 * <li>Handles user-related HTTP requests such as profile viewing, user registration, and deletion.</li>    *
 * <li>Uses UserActivityService to log user activities.</li>                                                *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * CustomUserDetailsService:                                                                                *
 * <ul>                                                                                                     *
 * <li>Implements UserDetailsService to load user-specific data during authentication.</li>                 *
 * </ul>                                                                                                    *
 *                                                                                                          *   
 * UserActivity:                                                                                            *
 * <ul>                                                                                                     *
 * <li>Represents a user's activity with fields such as userId, activity description, and timestamp.</li>   *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * UserActivityService:                                                                                     *
 * <ul>                                                                                                     *
 * <li>Handles the saving and retrieval of user activities using Redis.</li>                                *
 * <li>Logs activities such as viewing profile, registering a user, etc.</li>                               *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * UserRegistrationDto:                                                                                     *
 * <ul>                                                                                                     *
 * <li>Data transfer object used for user registration.</li>                                                *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * UserRepository:                                                                                          *
 * <ul>                                                                                                     *
 * <li>Extends JpaRepository to provide CRUD operations for AppUser entities.</li>                          *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * UserService/UserSessionService:                                                                                             *
 * <ul>                                                                                                     *
 * <li>Handles user-related business logic, such as creating and deleting users.</li>                       *
 * <li>Integrates with PostRepository to handle user-related posts.</li>                                    *
 * <li>Uses PasswordEncoder to encode passwords.</li>                                                       *
 * <li>Manages user sessions through UserSessionRepository.</li>                                            *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * UserSession:                                                                                             *
 * <ul>                                                                                                     *
 * <li>Represents a user's session with fields such as sessionId, userId, username, and createdAt.</li>     *
 * <li>Implements Serializable for Redis storage.</li>                                                      *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * UserSessionRepository:                                                                                   *
 * <ul>                                                                                                     *
 * <li>Handles the saving, retrieval, and deletion of user sessions using Redis.</li>                       *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * Annotations and Frameworks used:                                                                         *
 * <ul>                                                                                                     *
 * <li>Lombok: For boilerplate code reduction using annotations.</li>                                       *
 * <li>Jakarta Persistence (JPA): For ORM mapping and entity management.</li>                               *
 * <li>Spring Security: For handling user authentication and authorization.</li>                            *
 * <li>Spring MVC: For handling HTTP requests and responses.</li>                                           *
 * <li>Spring Data JPA: For data access and repository management.</li>                                     *
 * <li>Redis: For caching and storing user activities and sessions.</li>                                    *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * Author: Dillon                                                                                           *
 ***********************************************************************************************************/
package edu.site.jobBook.user;

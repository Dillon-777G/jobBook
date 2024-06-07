package edu.site.jobBook.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.site.jobBook.post.*;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSessionRepository userSessionRepository;

    public AppUser save(AppUser user) {
        logger.info("Saving user with username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser = userRepository.save(user);
        logger.info("User saved successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public AppUser findByUsername(String username) {
        logger.info("Finding user by username: {}", username);
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("User not found with username: {}", username);
        }
        return user;
    }

    public List<AppUser> findAllUsers() {
        logger.info("Retrieving all users");
        List<AppUser> users = userRepository.findAll();
        logger.info("Found {} users", users.size());
        return users;
    }

    public AppUser createUser(String username, String password, Set<String> roles) {
        logger.info("Creating user with username: {}", username);
        if (userRepository.findByUsername(username) != null) {
            logger.warn("Username already exists: {}", username);
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser newUser = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();
        AppUser createdUser = userRepository.save(newUser);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return createdUser;
    }

    public void createUserSession(AppUser user) {
        UserSession userSession = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        logger.info("Creating user session for userId: {}", user.getId());
        userSessionRepository.saveUserSession(userSession);
    }

    public void deleteUserByUsername(String username) {
        logger.info("Deleting user by username: {}", username);
        if ("admin".equals(username)) {
            logger.warn("Attempt to delete admin user: {}", username);
            throw new IllegalArgumentException("Cannot delete admin user");
        }

        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            // Find and delete all posts by the user
            List<Post> posts = postRepository.findByUser(user);
            for (Post post : posts) {
                postRepository.delete(post);
                logger.info("Deleted post with ID: {}", post.getId());
            }

            // Delete the user
            userRepository.delete(user);
            logger.info("User deleted successfully with username: {}", username);
        } else {
            logger.warn("User not found with username: {}", username);
            throw new IllegalArgumentException("User not found");
        }
    }

    public void saveUserSession(UserSession userSession) {
        logger.info("Saving user session for userId: {}", userSession.getUserId());
        userSessionRepository.saveUserSession(userSession);
        logger.info("User session saved successfully for sessionId: {}", userSession.getSessionId());
    }

    public UserSession getUserSession(String sessionId) {
        logger.info("Retrieving user session for sessionId: {}", sessionId);
        UserSession userSession = userSessionRepository.getUserSession(sessionId);
        if (userSession == null) {
            logger.warn("User session not found for sessionId: {}", sessionId);
        } else {
            logger.info("User session retrieved successfully for sessionId: {}", sessionId);
        }
        return userSession;
    }

    public void deleteUserSession(String sessionId) {
        logger.info("Deleting user session for sessionId: {}", sessionId);
        userSessionRepository.deleteUserSession(sessionId);
        logger.info("User session deleted successfully for sessionId: {}", sessionId);
    }

    public List<UserSession> getAllActiveSessions() {
        logger.info("Retrieving all active user sessions");
        List<UserSession> sessions = userSessionRepository.getAllUserSessions();
        logger.info("Found {} active sessions", sessions.size());
        return sessions;
    }
}
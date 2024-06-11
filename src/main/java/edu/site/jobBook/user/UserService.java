package edu.site.jobBook.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Optional<AppUser> findByUsernameIgnoreCase(String username) {
        logger.info("Finding user by username (ignore case): {}", username);
        Optional<AppUser> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            logger.warn("User not found with username (ignore case): {}", username);
        }
        return user;
    }

    public List<AppUser> findAllUsers() {
        logger.info("Retrieving all users");
        List<AppUser> users = userRepository.findAll();
        logger.info("Found {} users", users.size());
        return users;
    }

    public List<AppUser> findByRolesContaining(String role) {
        logger.info("Finding users with role: {}", role);
        List<AppUser> users = userRepository.findByRolesContaining(role);
        logger.info("Found {} users with role: {}", users.size(), role);
        return users;
    }

    public List<AppUser> findByUsernameContainingIgnoreCase(String username) {
        logger.info("Finding users by username containing (ignore case): {}", username);
        List<AppUser> users = userRepository.findByUsernameContainingIgnoreCase(username);
        logger.info("Found {} users with username containing: {}", users.size(), username);
        return users;
    }

    public List<AppUser> findByIdIn(List<Long> ids) {
        logger.info("Finding users by IDs: {}", ids);
        List<AppUser> users = userRepository.findByIdIn(ids);
        logger.info("Found {} users with specified IDs", users.size());
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
}

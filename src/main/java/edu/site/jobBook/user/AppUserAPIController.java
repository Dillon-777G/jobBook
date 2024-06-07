package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API for managing users")
public class AppUserAPIController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserAPIController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    @Operation(summary = "Get a user by username", description = "Returns a user with the specified username")
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        logger.info("Request to get user by username: {}", username);
        AppUser user = userService.findByUsername(username);
        if (user == null) {
            logger.warn("User not found with username: {}", username);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        logger.info("Request to get all users");
        List<AppUser> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user")
    public ResponseEntity<AppUser> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        logger.info("Request to create user: {}", userRegistrationDto.getUsername());
        AppUser createdUser = userService.createUser(
                userRegistrationDto.getUsername(),
                userRegistrationDto.getPassword(),
                userRegistrationDto.getRoles()
        );
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete a user by username", description = "Deletes the user with the specified username")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        logger.info("Request to delete user by username: {}", username);
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error deleting user with username: {}: {}", username, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

package edu.site.jobBook.user.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.site.jobBook.user.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API for managing users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/by-first-name/{firstName}")
    @Operation(summary = "Find users by first name", description = "Returns a list of users with the specified first name")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        List<User> users = userService.findByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-last-name/{lastName}")
    @Operation(summary = "Find users by last name", description = "Returns a list of users with the specified last name")
    public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
        List<User> users = userService.findByLastName(lastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-partial-last-name/{partialLastName}")
    @Operation(summary = "Find users by partial last name", description = "Returns a list of users whose last names contain the given substring")
    public ResponseEntity<List<User>> getUsersByPartialLastName(@PathVariable String partialLastName) {
        List<User> users = userService.findByPartialLastName(partialLastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Find users by email", description = "Returns a list of users with the specified email")
    public ResponseEntity<List<User>> getUsersByEmail(@PathVariable String email) {
        List<User> users = userService.findByEmail(email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-user-type/{userType}")
    @Operation(summary = "Find users by user type", description = "Returns a list of users with the specified user type")
    public ResponseEntity<List<User>> getUsersByUserType(@PathVariable String userType) {
        List<User> users = userService.findByUserType(userType);
        return ResponseEntity.ok(users);
    }

}
package edu.site.jobBook.user.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.site.jobBook.user.*;
import edu.site.jobBook.user.password.*;
import edu.site.jobBook.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API for user authentication")
public class AuthenticationController {

    private final UserPasswordService userPasswordService;

    @Autowired
    public AuthenticationController(UserPasswordService userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Registers a new user with a username and password")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO registrationDto) {
        User newUser = registrationDto.toUser();
        newUser = userPasswordService.createUserWithPassword(
            registrationDto.getUsername(), 
            registrationDto.getPassword(),
            newUser.getProfile(), 
            registrationDto.getUserType()
        );
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user using a username and password")
    public ResponseEntity<Boolean> authenticateUser(@RequestBody LoginDTO loginDto) {
        boolean isAuthenticated = userPasswordService.checkUserCredentials(
            loginDto.getUsername(), 
            loginDto.getPassword()
        );
        return ResponseEntity.ok(isAuthenticated);
    }
}

package edu.site.jobBook.user;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.site.jobBook.post.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser save(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }

    public AppUser createUser(String username, String password, Set<String> roles) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser newUser = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();
        return userRepository.save(newUser);
    }

    public void deleteUserByUsername(String username) {
        if ("admin".equals(username)) {
            throw new IllegalArgumentException("Cannot delete admin user");
        }

        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            // Find and delete all posts by the user
            List<Post> posts = postRepository.findByUser(user);
            for (Post post : posts) {
                postRepository.delete(post);
            }

            // Delete the user
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}

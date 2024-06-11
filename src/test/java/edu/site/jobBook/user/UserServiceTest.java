package edu.site.jobBook.user;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Rollback
@ComponentScan(basePackages = "edu.site.jobBook")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        AppUser user = AppUser.builder()
                .username("testuser")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser savedUser = userService.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(passwordEncoder.matches("password", savedUser.getPassword())).isTrue();
    }

    @Test
    public void testFindByUsername() {
        AppUser user = AppUser.builder()
                .username("testuser")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of("ROLE_USER"))
                .build();

        userRepository.save(user);

        AppUser foundUser = userService.findByUsername("testuser");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindByUsernameIgnoreCase() {
        AppUser user = AppUser.builder()
                .username("TestUser")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of("ROLE_USER"))
                .build();

        userRepository.save(user);

        Optional<AppUser> foundUser = userService.findByUsernameIgnoreCase("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("TestUser");
    }

    @Test
    public void testFindByRolesContaining() {
        AppUser user1 = AppUser.builder()
                .username("user1")
                .password(passwordEncoder.encode("password1"))
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser user2 = AppUser.builder()
                .username("adminuser")
                .password(passwordEncoder.encode("password2"))
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<AppUser> users = userService.findByRolesContaining("ROLE_ADMIN");

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("adminuser");
    }

    @Test
    public void testFindByUsernameContainingIgnoreCase() {
        AppUser user1 = AppUser.builder()
                .username("testuser1")
                .password(passwordEncoder.encode("password1"))
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser user2 = AppUser.builder()
                .username("testUser2")
                .password(passwordEncoder.encode("password2"))
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<AppUser> users = userService.findByUsernameContainingIgnoreCase("testuser");

        assertThat(users).hasSize(2);
        assertThat(users).extracting(AppUser::getUsername).containsExactlyInAnyOrder("testuser1", "testUser2");
    }

    @Test
    public void testFindByIdIn() {
        AppUser user1 = AppUser.builder()
                .username("testuser1")
                .password(passwordEncoder.encode("password1"))
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser user2 = AppUser.builder()
                .username("testuser2")
                .password(passwordEncoder.encode("password2"))
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        List<AppUser> users = userService.findByIdIn(List.of(user1.getId(), user2.getId()));

        assertThat(users).hasSize(2);
        assertThat(users).extracting(AppUser::getUsername).containsExactlyInAnyOrder("testuser1", "testuser2");
    }

    @Test
    public void testFindAllUsers() {
        AppUser user1 = AppUser.builder()
                .username("testuser1")
                .password(passwordEncoder.encode("password1"))
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser user2 = AppUser.builder()
                .username("testuser2")
                .password(passwordEncoder.encode("password2"))
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<AppUser> users = userService.findAllUsers();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(AppUser::getUsername).containsExactlyInAnyOrder("testuser1", "testuser2");
    }

    @Test
    public void testCreateUser() {
        AppUser createdUser = userService.createUser("newuser", "password", Set.of("ROLE_USER"));

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getUsername()).isEqualTo("newuser");
        assertThat(passwordEncoder.matches("password", createdUser.getPassword())).isTrue();
    }

    @Test
    public void testCreateUserThrowsExceptionWhenUsernameExists() {
        AppUser existingUser = AppUser.builder()
                .username("existinguser")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of("ROLE_USER"))
                .build();

        userRepository.save(existingUser);

        assertThatThrownBy(() -> userService.createUser("existinguser", "password", Set.of("ROLE_USER")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username already exists");
    }

    @Test
    public void testDeleteUserByUsername() {
        AppUser user = AppUser.builder()
                .username("testuser")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of("ROLE_USER"))
                .build();

        Post post = new Post();
        post.setUser(user);

        userRepository.save(user);
        postRepository.save(post);

        userService.deleteUserByUsername("testuser");

        assertThat(userRepository.findByUsername("testuser")).isNull();
        assertThat(postRepository.findByUser(user)).isEmpty();
    }

    @Test
    public void testDeleteUserByUsernameThrowsExceptionWhenUserNotFound() {
        assertThatThrownBy(() -> userService.deleteUserByUsername("nonexistentuser"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found");
    }

    @Test
    public void testDeleteUserByUsernameThrowsExceptionWhenAdmin() {
        // Ensure admin is present
        AppUser admin = userRepository.findByUsername("admin");
        if (admin == null) {
            admin = AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(new HashSet<>(Set.of("ROLE_ADMIN")))
                    .build();
            userRepository.save(admin);
        }

        // Attempt to delete the admin user and verify that an exception is thrown
        assertThatThrownBy(() -> userService.deleteUserByUsername("admin"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot delete admin user");
    }

}

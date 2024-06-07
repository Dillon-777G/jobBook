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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    @Autowired
    private UserSessionRepository userSessionRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        // Assuming user sessions are cleared after each test
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
        AppUser admin = AppUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        userRepository.save(admin);

        assertThatThrownBy(() -> userService.deleteUserByUsername("admin"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot delete admin user");
    }

    @Test
    public void testSaveUserSession() {
        UserSession userSession = UserSession.builder()
                .sessionId("session123")
                .userId(1L)
                .username("testuser")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        userService.saveUserSession(userSession);

        UserSession retrievedSession = userService.getUserSession("session123");

        assertThat(retrievedSession).isNotNull();
        assertThat(retrievedSession.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testGetUserSession() {
        UserSession userSession = UserSession.builder()
                .sessionId("session123")
                .userId(1L)
                .username("testuser")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        userSessionRepository.saveUserSession(userSession);

        UserSession retrievedSession = userService.getUserSession("session123");

        assertThat(retrievedSession).isNotNull();
        assertThat(retrievedSession.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testDeleteUserSession() {
        UserSession userSession = UserSession.builder()
                .sessionId("session123")
                .userId(1L)
                .username("testuser")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        userSessionRepository.saveUserSession(userSession);

        userService.deleteUserSession("session123");

        UserSession retrievedSession = userService.getUserSession("session123");

        assertThat(retrievedSession).isNull();
    }
}

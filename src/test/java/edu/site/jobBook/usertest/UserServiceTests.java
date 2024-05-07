package edu.site.jobBook.usertest;

import edu.site.jobBook.user.Profile;
import edu.site.jobBook.user.User;
import edu.site.jobBook.user.UserRepository;
import edu.site.jobBook.user.UserService;
import edu.site.jobBook.user.password.Password;
import edu.site.jobBook.user.password.PasswordRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
        passwordRepository.deleteAll(); 
    }

    @Test
    public void testCreateUserWithPassword() {
        Profile profile = new Profile("John", "Doe", "john@example.com", "johndoe");
        User user = new User();
        user.setProfile(profile);
        user.setUserType("Admin");

        User savedUser = userService.createUserWithPassword(user, "Secure123!");

        assertNotNull(savedUser.getPassword());
        assertTrue(savedUser.getPassword().getHash().equals(Password.hashPassword("Secure123!")));
    }

    @Test
    @Rollback
    public void testFindByFirstName() {
        Profile profile = new Profile("John", "Doe", "john.doe@example.com", "johndoe");
        User user = User.builder().profile(profile).userType("Admin").build();
        userRepository.save(user);

        List<User> foundUsers = userService.findByFirstName("John");

        assertThat(foundUsers).containsExactly(user);
    }

    @Test
    @Rollback
    public void testFindByLastName() {
        Profile profile = new Profile("John", "Smith", "john.smith@example.com", "johnsmith");
        User user = User.builder().profile(profile).userType("Member").build();
        userRepository.save(user);

        List<User> foundUsers = userService.findByLastName("Smith");

        assertThat(foundUsers).containsExactly(user);
    }

    @Test
    @Rollback
    public void testFindByPartialLastName() {
        Profile profile = new Profile("Jane", "Doe", "jane.doe@example.com", "janedoe");
        User user = User.builder().profile(profile).userType("Viewer").build();
        userRepository.save(user);

        List<User> foundUsers = userService.findByPartialLastName("%Do%");
        assertThat(foundUsers).hasSize(1);
        assertThat(foundUsers.get(0).getProfile().getLastName()).isEqualTo("Doe");
    }

    @Test
    @Rollback
    public void testFindByEmail() {
        Profile profile = new Profile("Mike", "Ross", "mike.ross@example.com", "mikeross");
        User user = User.builder().profile(profile).userType("Guest").build();
        userRepository.save(user);

        List<User> foundUsers = userService.findByEmail("mike.ross@example.com");

        assertThat(foundUsers).containsExactly(user);
    }

    @Test
    @Rollback
    public void testFindByUserType() {
        Profile profile = new Profile("Sarah", "Connor", "sarah.connor@example.com", "sarahconnor");
        User user = User.builder().profile(profile).userType("Admin").build();
        userRepository.save(user);

        List<User> foundUsers = userService.findByUserType("Admin");

        assertThat(foundUsers).containsExactly(user);
    }
}

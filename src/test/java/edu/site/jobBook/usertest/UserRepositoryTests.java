package edu.site.jobBook.usertest;

import edu.site.jobBook.user.User;
import edu.site.jobBook.user.Profile;
import edu.site.jobBook.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
    }

    @Test
    public void testFindByProfileFirstName() {
        Profile profileJohn = new Profile("John", "Doe", "john.doe@example.com", "johndoe");
        User userJohn = User.builder().profile(profileJohn).userType("Admin").build();
        entityManager.persist(userJohn);
        entityManager.flush();

        List<User> foundUsers = userRepository.findByProfileFirstName("John");
        assertThat(foundUsers).hasSize(1);
        assertThat(foundUsers.get(0).getProfile().getFirstName()).isEqualTo("John");
    }

    @Test
    @Rollback
    public void testFindByProfileLastName() {
        Profile profileSmith = new Profile("John", "Smith", "john.smith@example.com", "johnsmith");
        User userSmith = User.builder().profile(profileSmith).userType("Member").build();
        entityManager.persist(userSmith);
        entityManager.flush();

        List<User> foundUsers = userRepository.findByProfileLastName("Smith");
        assertThat(foundUsers).hasSize(1);
        assertThat(foundUsers.get(0).getProfile().getLastName()).isEqualTo("Smith");
    }

    @Test
    @Rollback
    public void testFindByProfileLastNameLike() {
        Profile profileDoe = new Profile("Jane", "Doe", "jane.doe@example.com", "janedoe");
        User userDoe = User.builder().profile(profileDoe).userType("Viewer").build();
        entityManager.persist(userDoe);
        entityManager.flush();

        List<User> foundUsers = userRepository.findByProfileLastNameLike("%Do%");
        assertThat(foundUsers).hasSize(1);
        assertThat(foundUsers.get(0).getProfile().getLastName()).isEqualTo("Doe");
    }

    @Test
    @Rollback
    public void testFindByProfileEmail() {
        Profile profileEmail = new Profile("Mike", "Ross", "mike.ross@example.com", "mikeross");
        User userEmail = User.builder().profile(profileEmail).userType("Guest").build();
        entityManager.persist(userEmail);
        entityManager.flush();

        List<User> foundUsers = userRepository.findByProfileEmail("mike.ross@example.com");
        assertThat(foundUsers).hasSize(1);
        assertThat(foundUsers.get(0).getProfile().getEmail()).isEqualTo("mike.ross@example.com");
    }

    @Test
    @Rollback
    public void testFindByUserType() {
        Profile profileType = new Profile("Sarah", "Connor", "sarah.connor@example.com", "sarahconnor");
        User userType = User.builder().profile(profileType).userType("Admin").build();
        entityManager.persist(userType);
        entityManager.flush();

        List<User> foundUsers = userRepository.findByUserType("Admin");
        assertThat(foundUsers).hasSize(1);
        assertThat(foundUsers.get(0).getUserType()).isEqualTo("Admin");
    }
}
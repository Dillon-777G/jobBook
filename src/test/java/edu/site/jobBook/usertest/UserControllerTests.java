package edu.site.jobBook.usertest;

import edu.site.jobBook.user.User;
import edu.site.jobBook.user.Profile;
import edu.site.jobBook.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup(){
        userService.deleteAll(); // Clear the database before each test
    }
    
    @Test
    public void testGetUsersByFirstName() throws Exception {
        Profile profile = new Profile("John", "Doe", "john.doe@example.com", "johndoe");
        User user = User.builder().profile(profile).userType("Admin").build();
        userService.save(user); // Save the user to the database

        mockMvc.perform(get("/api/users/by-first-name/John")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].profile.firstName").value("John"));
    }

    @Test
    public void testGetUsersByLastName() throws Exception {
        Profile profile = new Profile("Jane", "Smith", "jane.smith@example.com", "janesmith");
        User user = User.builder().profile(profile).userType("Viewer").build();
        userService.save(user);

        mockMvc.perform(get("/api/users/by-last-name/Smith")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].profile.lastName").value("Smith"));
    }

    @Test
    public void testGetUsersByPartialLastName() throws Exception {
        Profile profile = new Profile("Jane", "Doe", "jane.doe@example.com", "janedoe");
        User user = User.builder().profile(profile).userType("Admin").build();
        userService.save(user);

        mockMvc.perform(get("/api/users/by-partial-last-name/%Do%")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].profile.lastName").value("Doe"));
    }

    @Test
    public void testGetUsersByEmail() throws Exception {
        Profile profile = new Profile("Mike", "Ross", "mike.ross@example.com", "mikeross");
        User user = User.builder().profile(profile).userType("Guest").build();
        userService.save(user);

        mockMvc.perform(get("/api/users/by-email/mike.ross@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].profile.email").value("mike.ross@example.com"));
    }

    @Test
    public void testGetUsersByUserType() throws Exception {
        Profile profile = new Profile("Sarah", "Connor", "sarah.connor@example.com", "sarahconnor");
        User user = User.builder().profile(profile).userType("Admin").build();
        userService.save(user);

        mockMvc.perform(get("/api/users/by-user-type/Admin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userType").value("Admin"));
    }
}

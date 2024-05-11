package edu.site.jobBook.usertest.Controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import edu.site.jobBook.user.*;
import edu.site.jobBook.user.password.*;
import edu.site.jobBook.user.dto.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserPasswordService userPasswordService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDto;
    private LoginDTO loginDto;

    @BeforeEach
    public void setup() {
        userDto = new UserDTO("johndoe", "password123", "John", "Doe", "john.doe@example.com", "User");
        loginDto = new LoginDTO("johndoe", "password123");
    }

    @Test
    public void testRegisterUser() throws Exception {
        User newUser = userDto.toUser();
        given(userPasswordService.createUserWithPassword(userDto.getUsername(), userDto.getPassword(), newUser.getProfile(), userDto.getUserType()))
            .willReturn(newUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(newUser)));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        given(userPasswordService.checkUserCredentials(loginDto.getUsername(), loginDto.getPassword()))
            .willReturn(true);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}


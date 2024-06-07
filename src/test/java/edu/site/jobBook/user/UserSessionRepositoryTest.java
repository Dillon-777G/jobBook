package edu.site.jobBook.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UserSessionRepositoryTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private UserSessionRepository userSessionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void testSaveUserSession() {
        UserSession userSession = UserSession.builder()
                .sessionId("session123")
                .userId(1L)
                .username("testuser")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        userSessionRepository.saveUserSession(userSession);

        when(hashOperations.get(UserSessionRepository.KEY, "session123")).thenReturn(userSession);

        UserSession retrievedSession = userSessionRepository.getUserSession("session123");

        assertNotNull(retrievedSession);
        assertEquals("testuser", retrievedSession.getUsername());
    }

    @Test
    void testDeleteUserSession() {
        UserSession userSession = UserSession.builder()
                .sessionId("session123")
                .userId(1L)
                .username("testuser")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        userSessionRepository.saveUserSession(userSession);

        userSessionRepository.deleteUserSession("session123");

        when(hashOperations.get(UserSessionRepository.KEY, "session123")).thenReturn(null);

        UserSession retrievedSession = userSessionRepository.getUserSession("session123");

        assertNull(retrievedSession);
    }

    @Test
    void testGetAllUserSessions() {
        UserSession userSession1 = UserSession.builder()
                .sessionId("session123")
                .userId(1L)
                .username("testuser1")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        UserSession userSession2 = UserSession.builder()
                .sessionId("session124")
                .userId(2L)
                .username("testuser2")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        userSessionRepository.saveUserSession(userSession1);
        userSessionRepository.saveUserSession(userSession2);

        when(hashOperations.values(UserSessionRepository.KEY)).thenReturn(Arrays.asList(userSession1, userSession2));

        List<UserSession> allUserSessions = userSessionRepository.getAllUserSessions();

        assertNotNull(allUserSessions);
        assertEquals(2, allUserSessions.size());
    }
}

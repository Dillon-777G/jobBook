package edu.site.jobBook.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionServiceTest {

    @Mock
    private UserSessionRepository userSessionRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserSessionService userSessionService;

    private AppUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = AppUser.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .roles(new HashSet<>(Arrays.asList("ROLE_USER")))
                .build();
    }

    @Test
    void testCreateUserSession() {
        userSessionService.createUserSession(user);

        ArgumentCaptor<UserSession> captor = ArgumentCaptor.forClass(UserSession.class);
        verify(userSessionRepository).saveUserSession(captor.capture());
        UserSession capturedSession = captor.getValue();

        assertNotNull(capturedSession.getSessionId());
        assertEquals(user.getId(), capturedSession.getUserId());
        assertEquals(user.getUsername(), capturedSession.getUsername());
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), capturedSession.getCreatedAt());
        assertTrue(capturedSession.isActive());
    }

    @Test
    void testSaveUserSession() {
        UserSession session = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true)
                .build();

        userSessionService.saveUserSession(session);
        verify(userSessionRepository).saveUserSession(session);
    }

    @Test
    void testGetUserSession() {
        String sessionId = UUID.randomUUID().toString();
        UserSession session = UserSession.builder()
                .sessionId(sessionId)
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true)
                .build();

        when(userSessionRepository.getUserSession(sessionId)).thenReturn(session);

        UserSession retrievedSession = userSessionService.getUserSession(sessionId);
        assertNotNull(retrievedSession);
        assertEquals(sessionId, retrievedSession.getSessionId());
        assertEquals(user.getId(), retrievedSession.getUserId());
        assertEquals(user.getUsername(), retrievedSession.getUsername());
    }

    @Test
    void testDeleteUserSession() {
        String sessionId = UUID.randomUUID().toString();

        userSessionService.deleteUserSession(sessionId);
        verify(userSessionRepository).deleteUserSession(sessionId);
    }

    @Test
    void testMarkUserSessionInactive() {
        String sessionId = UUID.randomUUID().toString();
        UserSession session = UserSession.builder()
                .sessionId(sessionId)
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true)
                .build();

        when(userSessionRepository.getUserSession(sessionId)).thenReturn(session);

        userSessionService.markUserSessionInactive(sessionId);
        assertFalse(session.isActive());
        verify(userSessionRepository).saveUserSession(session);
    }

    @Test
    void testGetAllActiveSessions() {
        UserSession session1 = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true)
                .build();

        UserSession session2 = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(false)
                .build();

        when(userSessionRepository.getAllUserSessions()).thenReturn(Arrays.asList(session1, session2));

        List<UserSession> activeSessions = userSessionService.getAllActiveSessions();
        assertEquals(1, activeSessions.size());
        assertTrue(activeSessions.get(0).isActive());
    }

    @Test
    void testMarkUserSessionsInactiveByUsername() {
        UserSession session1 = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true)
                .build();

        UserSession session2 = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true)
                .build();

        when(userSessionRepository.getAllUserSessions()).thenReturn(Arrays.asList(session1, session2));

        userSessionService.markUserSessionsInactiveByUsername(user.getUsername());

        assertFalse(session1.isActive());
        assertFalse(session2.isActive());

        verify(userSessionRepository, times(2)).saveUserSession(any(UserSession.class));
    }
}

package edu.site.jobBook.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserSessionService {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionService.class);

    private final UserSessionRepository userSessionRepository;

    @Autowired
    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public void createUserSession(AppUser user) {
        UserSession userSession = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .active(true) // Set the session as active
                .build();
        logger.info("Creating user session for userId: {}", user.getId());
        userSessionRepository.saveUserSession(userSession);
    }

    public void saveUserSession(UserSession userSession) {
        logger.info("Saving user session for userId: {}", userSession.getUserId());
        userSessionRepository.saveUserSession(userSession);
        logger.info("User session saved successfully for sessionId: {}", userSession.getSessionId());
    }

    public UserSession getUserSession(String sessionId) {
        logger.info("Retrieving user session for sessionId: {}", sessionId);
        UserSession userSession = userSessionRepository.getUserSession(sessionId);
        if (userSession == null) {
            logger.warn("User session not found for sessionId: {}", sessionId);
        } else {
            logger.info("User session retrieved successfully for sessionId: {}", sessionId);
        }
        return userSession;
    }

    public void deleteUserSession(String sessionId) {
        logger.info("Deleting user session for sessionId: {}", sessionId);
        userSessionRepository.deleteUserSession(sessionId);
        logger.info("User session deleted successfully for sessionId: {}", sessionId);
    }

    public void markUserSessionInactive(String sessionId) {
        logger.info("Marking user session as inactive for sessionId: {}", sessionId);
        UserSession userSession = getUserSession(sessionId);
        if (userSession != null) {
            userSession.setActive(false);
            saveUserSession(userSession);
        }
    }

    public List<UserSession> getAllActiveSessions() {
        logger.info("Retrieving all active user sessions");
        List<UserSession> sessions = userSessionRepository.getAllUserSessions();
        sessions = sessions.stream().filter(UserSession::isActive).collect(Collectors.toList());
        logger.info("Found {} active sessions", sessions.size());
        return sessions;
    }

    public void markUserSessionsInactiveByUsername(String username) {
        logger.info("Marking user sessions as inactive for username: {}", username);
        List<UserSession> sessions = userSessionRepository.getAllUserSessions();
        sessions.stream()
                .filter(session -> session.getUsername().equals(username) && session.isActive())
                .forEach(session -> {
                    session.setActive(false);
                    saveUserSession(session);
                });
    }
}

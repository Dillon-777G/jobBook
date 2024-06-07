package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class UserSessionRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionRepository.class);

    static final String KEY = "USER_SESSION";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUserSession(UserSession userSession) {
        logger.info("Saving user session for userId: {}", userSession.getUserId());
        redisTemplate.opsForHash().put(KEY, userSession.getSessionId(), userSession);
        redisTemplate.expire(KEY + ":" + userSession.getSessionId(), 30, TimeUnit.MINUTES); // Set session expiration
        logger.info("User session saved successfully for sessionId: {}", userSession.getSessionId());
    }
    

    public UserSession getUserSession(String sessionId) {
        return (UserSession) redisTemplate.opsForHash().get(KEY, sessionId);
    }

    public void deleteUserSession(String sessionId) {
        redisTemplate.opsForHash().delete(KEY, sessionId);
    }

    public List<UserSession> getAllUserSessions() {
        return redisTemplate.opsForHash().values(KEY).stream()
                .map(obj -> (UserSession) obj)
                .collect(Collectors.toList());
    }
}

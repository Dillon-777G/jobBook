package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UserSessionRepository {

    public static final String KEY = "USER_SESSION";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUserSession(UserSession userSession) {
        redisTemplate.opsForHash().put(KEY, userSession.getSessionId(), userSession);
        redisTemplate.expire(KEY, 30, TimeUnit.MINUTES); // Set session expiration
    }

    public UserSession getUserSession(String sessionId) {
        return (UserSession) redisTemplate.opsForHash().get(KEY, sessionId);
    }

    public void deleteUserSession(String sessionId) {
        redisTemplate.opsForHash().delete(KEY, sessionId);
    }

    public List<Object> getAllUserSessions() {
        return redisTemplate.opsForHash().values(KEY);
    }
}

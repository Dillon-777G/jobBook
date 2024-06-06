package edu.site.jobBook.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserActivityService {

    private static final String KEY = "USER_ACTIVITY";
    private static final Logger logger = LogManager.getLogger(UserActivityService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUserActivity(UserActivity userActivity) {
        logger.info("Saving user activity: {}", userActivity);
        redisTemplate.opsForList().rightPush(KEY + ":" + userActivity.getUserId(), userActivity);
    }

    public List<UserActivity> getUserActivities(Long userId) {
        logger.info("Retrieving user activities for userId: {}", userId);
        List<Object> activities = redisTemplate.opsForList().range(KEY + ":" + userId, 0, -1);
        if (activities == null) {
            logger.warn("No activities found for userId: {}", userId);
            return Collections.emptyList();
        }
        logger.info("Found {} activities for userId: {}", activities.size(), userId);
        return activities.stream()
                .map(activity -> (UserActivity) activity)
                .sorted((a1, a2) -> LocalDateTime.parse(a2.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .compareTo(LocalDateTime.parse(a1.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .collect(Collectors.toList());
    }

    public List<UserActivity> getAllUserActivities() {
        logger.info("Retrieving all user activities");
        Set<String> keys = redisTemplate.keys(KEY + ":*");
        if (keys == null || keys.isEmpty()) {
            logger.warn("No user activities found");
            return Collections.emptyList();
        }
        return keys.stream()
                .flatMap(key -> {
                    List<Object> activities = redisTemplate.opsForList().range(key, 0, -1);
                    if (activities == null) {
                        logger.warn("No activities found for key: {}", key);
                        return Collections.emptyList().stream();
                    }
                    return activities.stream();
                })
                .map(activity -> (UserActivity) activity)
                .sorted((a1, a2) -> LocalDateTime.parse(a2.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .compareTo(LocalDateTime.parse(a1.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .collect(Collectors.toList());
    }

    public void deleteUserActivities(Long userId) {
        logger.info("Deleting user activities for userId: {}", userId);
        redisTemplate.delete(KEY + ":" + userId);
    }
}

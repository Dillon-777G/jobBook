package edu.site.jobBook.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
        return activities.stream().map(activity -> (UserActivity) activity).collect(Collectors.toList());
    }

    public void deleteUserActivities(Long userId) {
        logger.info("Deleting user activities for userId: {}", userId);
        redisTemplate.delete(KEY + ":" + userId);
    }
}

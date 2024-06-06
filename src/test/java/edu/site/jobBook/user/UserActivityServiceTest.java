package edu.site.jobBook.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ListOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserActivityServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ListOperations<String, Object> listOperations;

    @InjectMocks
    private UserActivityService userActivityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
    }

    @Test
    public void testSaveUserActivity() {
        UserActivity activity = UserActivity.builder()
                .userId(1L)
                .activity("Test activity")
                .timestamp("2023-12-01 12:00:00")
                .build();
        
        userActivityService.saveUserActivity(activity);

        verify(listOperations).rightPush(eq("USER_ACTIVITY:1"), eq(activity));
    }

    @Test
    public void testGetUserActivities() {
        UserActivity activity = UserActivity.builder()
                .userId(1L)
                .activity("Test activity")
                .timestamp("2023-12-01 12:00:00")
                .build();
        
        when(listOperations.range("USER_ACTIVITY:1", 0, -1)).thenReturn(Arrays.asList(activity));

        List<UserActivity> activities = userActivityService.getUserActivities(1L);

        assertThat(activities).isNotEmpty();
        assertThat(activities.get(0).getActivity()).isEqualTo("Test activity");
    }

    @Test
    public void testGetAllUserActivities() {
        UserActivity activity1 = UserActivity.builder()
                .userId(1L)
                .activity("Test activity 1")
                .timestamp("2023-12-01 12:00:00")
                .build();
        UserActivity activity2 = UserActivity.builder()
                .userId(2L)
                .activity("Test activity 2")
                .timestamp("2023-12-01 13:00:00")
                .build();
        
        when(redisTemplate.keys("USER_ACTIVITY:*")).thenReturn(Set.of("USER_ACTIVITY:1", "USER_ACTIVITY:2"));
        when(listOperations.range("USER_ACTIVITY:1", 0, -1)).thenReturn(Arrays.asList(activity1));
        when(listOperations.range("USER_ACTIVITY:2", 0, -1)).thenReturn(Arrays.asList(activity2));

        List<UserActivity> activities = userActivityService.getAllUserActivities();

        assertThat(activities).hasSize(2);
        assertThat(activities).contains(activity1, activity2);
    }

    @Test
    public void testDeleteUserActivities() {
        userActivityService.deleteUserActivities(1L);

        verify(redisTemplate).delete("USER_ACTIVITY:1");
    }
}

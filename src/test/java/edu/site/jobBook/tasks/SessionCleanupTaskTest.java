// package edu.site.jobBook.tasks;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.session.data.redis.RedisIndexedSessionRepository;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

// class SessionCleanupTaskTest {

//     private RedisIndexedSessionRepository sessionRepository;
//     private SessionCleanupTask sessionCleanupTask;

//     @BeforeEach
//     void setUp() {
//         sessionRepository = Mockito.mock(RedisIndexedSessionRepository.class);
//         sessionCleanupTask = new SessionCleanupTask(sessionRepository);
//     }

//     @Test
//     void cleanUpExpiredSessions_shouldCallCleanUpExpiredSessions() {
//         sessionCleanupTask.cleanUpExpiredSessions();
//         verify(sessionRepository, times(1)).cleanUpExpiredSessions();
//     }
// }
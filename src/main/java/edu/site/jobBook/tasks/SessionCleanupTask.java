// package edu.site.jobBook.tasks;

// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.session.data.redis.RedisIndexedSessionRepository;
// import org.springframework.stereotype.Component;

// @Component
// public class SessionCleanupTask {

//     private final RedisIndexedSessionRepository sessionRepository;

//     public SessionCleanupTask(RedisIndexedSessionRepository sessionRepository) {
//         this.sessionRepository = sessionRepository;
//     }

//     @Scheduled(fixedRate = 86400000) // Run once a day (86400000 milliseconds = 24 hours)
//     public void cleanUpExpiredSessions() {
//         sessionRepository.cleanUpExpiredSessions();
//     }
// }

// package edu.site.jobBook.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.session.data.redis.RedisIndexedSessionRepository;
// import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
// import org.springframework.session.web.http.CookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;

// @Configuration
// @EnableRedisHttpSession
// public class SessionConfig {

//     @Bean
//     public CookieSerializer cookieSerializer() {
//         DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//         serializer.setCookieName("SESSIONID");
//         serializer.setCookiePath("/");
//         serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
//         return serializer;
//     }

//     @Bean
//     public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//         RedisTemplate<String, Object> template = new RedisTemplate<>();
//         template.setConnectionFactory(redisConnectionFactory);
//         return template;
//     }

//     @Bean
//     public RedisIndexedSessionRepository redisIndexedSessionRepository(RedisTemplate<String, Object> redisTemplate) {
//         return new RedisIndexedSessionRepository(redisTemplate);
//     }
// }

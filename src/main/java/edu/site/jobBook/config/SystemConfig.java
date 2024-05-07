package edu.site.jobBook.config;

import edu.site.jobBook.user.Profile;
import edu.site.jobBook.user.User;
import edu.site.jobBook.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
    @Autowired
    private UserService userService;

    @Bean
    @Qualifier("defaultUser1")
    public User getDefaultUser1() {
        var user = User.builder()
                .userType("System User")
                .profile(Profile.builder()
                        .firstName("Default1")
                        .lastName("Default1")
                        .email("default1@gmail.com")
                        .username("default1")
                        .build())
                .build();
        return userService.save(user);
    }

    @Bean
    @Qualifier("defaultUser2")
    public User getDefaultUser2() {
        var user = User.builder()
                .userType("System User")
                .profile(Profile.builder()
                        .firstName("Default2")
                        .lastName("Default2")
                        .email("default2@gmail.com")
                        .username("default2")
                        .build())
                .build();
        return userService.save(user);
    }
}
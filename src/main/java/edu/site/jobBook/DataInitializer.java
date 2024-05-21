package edu.site.jobBook;

import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        if (userService.findByUsername("admin") == null) {
            AppUser admin = AppUser.builder()
                    .username("admin")
                    .password("admin")
                    .roles(Set.of("ROLE_ADMIN"))
                    .build();
            userService.save(admin);
        }
    }
}

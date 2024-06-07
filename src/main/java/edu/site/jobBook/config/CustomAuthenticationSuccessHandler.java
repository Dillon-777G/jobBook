package edu.site.jobBook.config;

import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserSession;
import edu.site.jobBook.user.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private UserSessionService userSessionService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AppUser user = (AppUser) authentication.getPrincipal();
        createUserSession(user);

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/");
        }
    }

    private void createUserSession(AppUser user) {
        logger.info("Creating user session for user: {}", user.getUsername());
        UserSession userSession = UserSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .username(user.getUsername())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        userSessionService.saveUserSession(userSession);
        logger.info("User session created and saved for user: {}", user.getUsername());
    }
}

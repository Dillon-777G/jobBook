package edu.site.jobBook.config;

import edu.site.jobBook.user.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final UserSessionService userSessionService;

    @Autowired
    public CustomLogoutSuccessHandler(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getPrincipal() != null) {
            String username = authentication.getName();
            userSessionService.markUserSessionsInactiveByUsername(username);
        }
        response.sendRedirect("/login?logout");
    }
}

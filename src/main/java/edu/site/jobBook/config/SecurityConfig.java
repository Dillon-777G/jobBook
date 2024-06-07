package edu.site.jobBook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import edu.site.jobBook.user.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**", "/login", "/register").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/jobs/new", "/jobs/edit/*").hasRole("ADMIN") // Only allow admins to create and edit jobs
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))  // Disable CSRF for H2 console  // Disable CSRF for H2 console
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))  // Disable frame options for H2 console
            .httpBasic(withDefaults())
            .formLogin(form -> form
                .permitAll()
                .successHandler(successHandler)
            )
            .logout(logout -> logout
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
            );    
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}

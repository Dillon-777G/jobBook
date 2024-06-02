package edu.site.jobBook.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private Set<String> roles;
}

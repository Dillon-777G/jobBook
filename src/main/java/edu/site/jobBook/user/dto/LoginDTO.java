package edu.site.jobBook.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class LoginDTO{
    private String username;
    private String password;
}
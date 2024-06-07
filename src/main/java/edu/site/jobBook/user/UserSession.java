package edu.site.jobBook.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private String sessionId;
    private Long userId;
    private String username;
    private String createdAt;
}

package edu.site.jobBook.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L; // Add this line to define a serialVersionUID
    private String sessionId;
    private Long userId;
    private String username;
    private String createdAt;
}

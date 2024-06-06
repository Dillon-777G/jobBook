package edu.site.jobBook.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {
    private Long userId;
    private String activity;
    private String timestamp;
}

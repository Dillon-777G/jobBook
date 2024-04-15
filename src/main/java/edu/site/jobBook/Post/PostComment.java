package edu.site.jobBook.Post;

import edu.site.jobBook.User.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostComment {
    private UUID id;
    private UUID postId;
    private Instant time;
    private String message;
    private Profile profile;
}

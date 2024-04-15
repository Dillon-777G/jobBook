package edu.site.jobBook.Post;

import edu.site.jobBook.User.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Profile profile;
    private UUID id;
    private String caption;
    private byte[] image;
    private Long likes;
    private Long shares;
    private List<PostComment> comments;
}


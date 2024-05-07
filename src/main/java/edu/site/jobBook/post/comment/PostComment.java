package edu.site.jobBook.post.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.site.jobBook.post.Post;
import edu.site.jobBook.user.User;
import jakarta.persistence.*;
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
@Entity
@Table(name = "POST_COMMENT")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private Instant time;
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sourceUser;
}

package edu.site.jobBook.post;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.post.comment.PostComment;
import edu.site.jobBook.user.User;
import jakarta.persistence.*;
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
@Entity
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String caption;
    private byte[] image;
    private Long likes;
    private Long shares;
    @OneToMany(mappedBy="post")
    private List<PostComment> comments;

    //Testing for handling company posts
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}


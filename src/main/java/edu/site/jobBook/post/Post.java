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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "POST")
@JsonIgnoreProperties({"company", "comments"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String caption;
    private byte[] image;
    private long likes;
    private long shares ;
    //troubleshooting
    // @OneToMany(mappedBy="post")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments;

    //Testing for handling company posts
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}


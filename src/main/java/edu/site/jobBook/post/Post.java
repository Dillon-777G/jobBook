package edu.site.jobBook.post;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.post.comment.PostComment;
import edu.site.jobBook.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "POST")
@JsonIgnoreProperties({"company", "comments"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    private String caption;
    private byte[] image;
    private long likes;
    private long shares;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PostComment> comments;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;
}
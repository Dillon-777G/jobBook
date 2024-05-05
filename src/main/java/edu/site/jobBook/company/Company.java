package edu.site.jobBook.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.post.Post;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;
    //waiting on other classes to be more developed
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Job> jobs;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Post> posts;
}

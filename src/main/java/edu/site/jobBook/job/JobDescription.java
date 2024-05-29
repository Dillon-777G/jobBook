package edu.site.jobBook.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "JOB_DESCRIPTION")
//https://www.reddit.com/r/SpringBoot/comments/xvkkij/tostring_equals_and_hashcode/
@ToString(exclude = "job") 
public class JobDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;

    @Column(name = "overview", nullable = false, length = 1000)
    private String overview;

    @Column(name = "responsibilities", nullable = false, length = 1000)
    private String responsibilities;

    @Column(name = "skills", nullable = false, length = 1000)
    private String skills;

    @Column(name = "qualification", nullable = false, length = 1000)
    private String qualification;

    @Column(name = "benefits", nullable = false, length = 1000)
    private String benefits;
}
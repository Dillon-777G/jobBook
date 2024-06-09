package edu.site.jobBook.job;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.site.jobBook.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // This annotation is required to specify the class as a JPA entity
@Table(name = "JOBS")
@JsonIgnoreProperties({"company", "jobDescription", "jobDetails", "company_id"})
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("job")
    private JobDescription jobDescription;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("job")
    private JobDetails jobDetails;
}
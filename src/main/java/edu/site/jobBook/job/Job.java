package edu.site.jobBook.job;

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
@JsonIgnoreProperties({"company", "jobDescription", "jobDetails"})
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore//added to fix an overflow issue
    private Company company;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    private JobDescription jobDescription;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    private JobDetails jobDetails;
}

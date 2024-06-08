package edu.site.jobBook.job.JobApplication;


import edu.site.jobBook.job.Job;
import edu.site.jobBook.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "JOB_APPLICATIONS")
public class JobApplication {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_date", nullable = false)
    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobApplicationStatus status;
}
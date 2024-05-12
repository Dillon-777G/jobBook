package edu.site.jobBook.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "JOB_DETAILS")
public class JobDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "posted_date", nullable = false)
    private String postedDate;

    @Column(name = "expiry_date", nullable = false)
    private String expiryDate;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;
}

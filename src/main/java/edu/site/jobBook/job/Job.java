package edu.site.jobBook.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private Long id;
    private String title;
    private String description;
    private String company;
    private Date postedDate;
    private String location;
    private List<JobApplication> applications;
}

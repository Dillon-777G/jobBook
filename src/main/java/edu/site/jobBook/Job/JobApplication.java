package edu.site.jobBook.Job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    private Long id;
    private Job job;  
    private Long applicantId;  
    private Date applicationDate;
    private String status;  
}

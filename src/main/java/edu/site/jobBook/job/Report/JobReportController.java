package edu.site.jobBook.job.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.site.jobBook.job.JobApplicationService;

import java.util.List;

@Controller
@RequestMapping("/admin/jobreports")
public class JobReportController {
      
    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping("/jobApplicationsByJobId")
    public String countJobApplicationsByJobId() {
        
        List<Object[]> countByJobId =  jobApplicationService.countJobApplicationsByJobId();
        return "jobReport";
    }

}

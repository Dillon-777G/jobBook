package edu.site.jobBook.job.Dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobApplication;
import edu.site.jobBook.job.JobApplicationService;
import edu.site.jobBook.job.JobService;
import edu.site.jobBook.job.JobStatus;
import edu.site.jobBook.job.JobView.JobView;
import edu.site.jobBook.job.JobView.JobViewService;
import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostService;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserService;
import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;

@Controller
@RequestMapping("/admin/dashboard")
public class adminDashboardController {

    private final JobService jobService;
    private final PostService postService;
    private final UserService userService;
    private final JobViewService jobViewService;


    public adminDashboardController(JobService jobService, 
                                    PostService postService,
                                    UserService userService,
                                    JobViewService jobViewService) {
        this.jobService = jobService;
        this.postService = postService;
        this.userService = userService;
        this.jobViewService = jobViewService;
    }


    @GetMapping("/jobs")
    public String countJobApplicationsByJobId(Model model,@RequestParam(name = "page", defaultValue = "0") int page) {

    Map<String, Long> jobCountByStatus = new HashMap<>();
    jobCountByStatus.put(JobStatus.ACTIVE.toString(), jobService.getJobCountByStatus(JobStatus.ACTIVE));
    jobCountByStatus.put(JobStatus.EXPIRED.toString(), jobService.getJobCountByStatus(JobStatus.EXPIRED));
    jobCountByStatus.put(JobStatus.CLOSED.toString(), jobService.getJobCountByStatus(JobStatus.CLOSED));
    jobCountByStatus.put(JobStatus.ON_HOLD.toString(), jobService.getJobCountByStatus(JobStatus.ON_HOLD));
    
    List<JobView> allJobViews =  jobViewService.getAllJobViews();

    int pageSize = 10; // Number of jobs per page
    Pageable pageable = PageRequest.of(page, pageSize);
    Page<JobView> jobViewsPage = jobViewService.getAllJobViewsOrderByDescViewCOunt(pageable);
        
    model.addAttribute("totalJobCount", jobService.getAllJobs().size());
    model.addAttribute("totalCompanyCount", jobService.getAllCompanies().size());
    model.addAttribute("totalPostCount", postService.fetchAllPosts().size());
    model.addAttribute("totalUserCount", userService.findAllUsers().size());
    
    model.addAttribute("jobCountByStatus", jobCountByStatus);

    model.addAttribute("jobViewsPage", jobViewsPage);
    
        return "adminDashboard";
    }
}

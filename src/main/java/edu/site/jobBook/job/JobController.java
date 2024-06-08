package edu.site.jobBook.job;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.job.JobApplication.JobApplication;
import edu.site.jobBook.job.JobApplication.JobApplicationService;
import edu.site.jobBook.job.JobView.JobViewService;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserActivity;
import edu.site.jobBook.user.UserActivityService;
import edu.site.jobBook.user.UserRepository;

// pagination imports
import org.springframework.data.domain.Page;    
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private final UserRepository userRepository;
    private final UserActivityService userActivityService;
    private final JobViewService jobViewService;

    @Autowired
    public JobController(JobService jobService, JobApplicationService jobApplicationService,
                         UserRepository userRepository, UserActivityService userActivityService,
                         JobViewService jobViewService) {
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
        this.userRepository = userRepository;
        this.userActivityService = userActivityService;
        this.jobViewService = jobViewService;
    }

    // Get all jobs
    @GetMapping
    public String getAllJobs(Model model,@RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        
        int pageSize = 5; // Number of jobs per page
        Pageable pageable = PageRequest.of(page, pageSize);
        
        Page<Job> jobs = jobService.getAllJobs(pageable);

        model.addAttribute("jobs", jobs);
        // if (!jobs.isEmpty()) {
        //     model.addAttribute("selectedJob", jobs.get(0));
        // }

        // Log user activity for viewing the jobs page
        userActivityService.saveUserActivity(UserActivity.builder()
                .userId(user.getId())
                .activity("Viewed jobs page")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());
        return "jobsList";
    }
    
    // Get job details for a job with the provided ID
    @GetMapping("/{id}")
    public String getJobDetails(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        Job job = jobService.getJobById(id);
        boolean jobAlreadyApplied = jobApplicationService.hasUserAppliedForJob(user, job);
        model.addAttribute("job", job);
        model.addAttribute("jobAlreadyApplied", jobAlreadyApplied);
        
        // Store the job view count in the database
        jobViewService.incrementJobViewCount(id.toString(), job.getTitle());
        long jobViewCount = jobViewService.getJobViewCount(id.toString(),job.getTitle());
        model.addAttribute("jobViewCount", jobViewCount);
        model.addAttribute("userName", user.getUsername());
        return "jobDetails";
    }

    // Get all applied jobs for the current user
    @GetMapping("/myjobs")
    public String myJobs(Model model, Principal principal) {
        // Get current user's username
        String username = principal.getName();

        // Get job applications for the current user
        List<JobApplication> jobApplications = jobApplicationService.getApplicationsByUser(userRepository.findByUsername(username));

        // Add job applications to the model
        model.addAttribute("totalApplications", jobApplications.size());
        model.addAttribute("jobApplications", jobApplications);

        return "myJobsPage";
    }

    // Filter job applications by status
    @GetMapping("/myjobs/filter/{filter}")
    public String filterJobApplications(Model model, @PathVariable("filter") String filter, Principal principal) {
        AppUser user = userRepository.findByUsername(principal.getName());
        List<JobApplication> filteredApplications = jobApplicationService.getFilteredApplications(user, filter);
        model.addAttribute("jobApplications", filteredApplications);
        model.addAttribute("filterText", filter);
        return "myJobsWithFilter";
    }

    // Apply for a job by submitting a resume
    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(@RequestParam("jobId") Long jobId, 
                                                @RequestParam("resume") MultipartFile resume,
                                                Principal principal) {
        if (jobId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid job ID");
        }
        
        AppUser user = userRepository.findByUsername(principal.getName());
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found");
        }

        boolean applied = jobApplicationService.applyForJob(user, job, resume);
        if (applied) {
            return ResponseEntity.ok("Application submitted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have already applied for this job!");
        }
    }      

    // Create a new job
    @GetMapping("/new")
    public String showJobForm(Model model) {
        List<Company> companies = jobService.getAllCompanies(); // Assuming you have a method to fetch all companies
        model.addAttribute("companies", companies);
        Job job = new Job();
        job.setJobDescription(new JobDescription());
        job.setJobDetails(new JobDetails());

        model.addAttribute("job", job);
        return "jobCreate";
    }

    // Save a new job
    @PostMapping("/save-job")
    public String saveJob(@ModelAttribute Job job,  Model model,RedirectAttributes redirectAttributes,@RequestParam("companyId") Long companyId) {
        jobService.saveJob(job, companyId);
        redirectAttributes.addFlashAttribute("message", "Job created successfully!");
        return "redirect:/jobs";
    }

    // Edit a job
    @GetMapping("/edit/{id}")
    public String showEditJobForm(@PathVariable("id") long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);
        model.addAttribute("companies", jobService.getAllCompanies());
        return "jobEdit";
    }

    // Update a job
    @PostMapping("/update-job")
    public String updateJob(Job job, @RequestParam("companyId") Long companyId, Model model, RedirectAttributes redirectAttributes) {
        jobService.updateJob(job, companyId);
        redirectAttributes.addFlashAttribute("message", "Job updated " + job.getId() + " successfully!");
        return "redirect:/jobs";
    }

    // Get job details for a job 
    @GetMapping("/details/{jobId}")
    public String getJobFullDetails(@PathVariable Long jobId, Model model) {
        
        // Fetch job details from the database based on the provided job ID
        Job job = jobService.getJobById(jobId);

        // Add the fetched job details to the model to be displayed in the view
        model.addAttribute("job", job);

        jobViewService.incrementJobViewCount(jobId.toString(),job.getTitle());

        // Return the name of the Thymeleaf template to be rendered
        return "jobDetailsRightPanel";
    }
}

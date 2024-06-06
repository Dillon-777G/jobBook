package edu.site.jobBook.job;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PrinterInfo;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.ui.Model;


//trouble shooting
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Controller
@RequestMapping("/api/jobs")
public class JobControllerAPI {

    private final JobService jobService;

    @Autowired
    public JobControllerAPI(JobService jobService, 
    JobApplicationService jobApplicationService, 
    UserRepository userRepository) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long id) {
        Job job = jobService.getJobById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<Job> saveJob(@RequestBody Job job, @RequestParam Long companyId) {
        Job savedJob = jobService.saveJob(job, companyId);
        return ResponseEntity.ok(savedJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") Long id, @RequestBody Job updatedJob, @RequestParam("companyId") Long companyId) {
        Job job = jobService.getJobById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        updatedJob.setId(id);
        Job updated = jobService.updateJob(updatedJob, companyId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable("id") Long id) {
        // Logic to delete job
        return ResponseEntity.ok("Job deleted successfully!");
    }


}
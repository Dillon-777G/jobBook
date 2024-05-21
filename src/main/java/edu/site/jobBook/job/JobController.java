package edu.site.jobBook.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//trouble shooting
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody JobRequest jobRequest) {
        Job job = new Job();
        job.setTitle(jobRequest.getTitle());
        Job savedJob = jobService.saveJob(job, jobRequest.getCompanyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody JobRequest jobRequest) {
        Job existingJob = jobService.getJobById(id);
        if (existingJob != null) {
            existingJob.setTitle(jobRequest.getTitle());
            Job updatedJob = jobService.updateJob(existingJob);
            return ResponseEntity.ok(updatedJob);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // @PostMapping
    // public ResponseEntity<Job> createJob(@RequestBody Job job) {
    //     Job savedJob = jobService.saveJob(job);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
    //     Job existingJob = jobService.getJobById(id);
    //     if (existingJob != null) {
    //         Job updatedJob = jobService.updateJob(job);
    //         return ResponseEntity.ok(updatedJob);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        Job existingJob = jobService.getJobById(id);
        if (existingJob != null) {
            jobService.deleteJobById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class JobRequest {
        private String title;
        private Long companyId;
    }
}
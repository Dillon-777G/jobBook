package edu.site.jobBook.job.JobView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.site.jobBook.company.HiringStatus.CompanyHiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusRepository;

@Component
public class JobViewInitializer implements CommandLineRunner{
    
    @Autowired
    private JobViewRepository jobViewRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // Initialize Redis data
        jobViewRepository.save(new JobView("1","Software Engineer", 10));
        jobViewRepository.save(new JobView("2","Data Analyst", 5));
        jobViewRepository.save(new JobView("3","Marketing Manager", 2));
        jobViewRepository.save(new JobView("4","Frontend Developer", 6));
        jobViewRepository.save(new JobView("5","Frontend Developer", 9));
    }
}


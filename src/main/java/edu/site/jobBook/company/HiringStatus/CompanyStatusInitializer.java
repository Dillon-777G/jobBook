package edu.site.jobBook.company.HiringStatus;

import java.util.ArrayList;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import edu.site.jobBook.company.HiringStatus.CompanyHiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CompanyStatusInitializer implements CommandLineRunner{
     @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private HiringStatusRepository hiringStatusRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // Initialize Redis data
        hiringStatusRepository.save(new CompanyHiringStatus(1L, HiringStatus.HIRING));
        hiringStatusRepository.save(new CompanyHiringStatus(10L, HiringStatus.NOT_HIRING));
    }
}

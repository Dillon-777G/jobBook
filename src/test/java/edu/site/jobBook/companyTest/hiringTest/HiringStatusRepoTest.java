package edu.site.jobBook.companyTest.hiringTest;

import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.site.jobBook.company.HiringStatus.CompanyHiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusRepository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class HiringStatusRepoTest {

    @Autowired
    private HiringStatusRepository repository;

    //have to be running the doccker for this to pass.
    @Test
    public void TestAdd() {
        CompanyHiringStatus hiringStatus1 = new CompanyHiringStatus(1, HiringStatus.UNKNOWN);
        CompanyHiringStatus hiringStatus2 = new CompanyHiringStatus(3, HiringStatus.NOT_HIRING);

        repository.save(hiringStatus1);
        repository.save(hiringStatus2);

        CompanyHiringStatus test = repository.findById(hiringStatus1.getCompanyId()).get();

        assertEquals(hiringStatus1, test);

    }
    
}

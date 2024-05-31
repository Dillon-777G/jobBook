package edu.site.jobBook.companyTest.hiringTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.site.jobBook.company.HiringStatus.CompanyHiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusRepository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootTest
public class HiringStatusRepoTest {

    @Autowired
    private HiringStatusRepository repository;



    //have to be running the doccker for this to pass.

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(new CompanyHiringStatus(3L, HiringStatus.HIRING));
        repository.save(new CompanyHiringStatus(4L, HiringStatus.NOT_HIRING));
        repository.save(new CompanyHiringStatus(5L, HiringStatus.UNKNOWN));
   
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }


    @Test
    void testSaveAndFindById() {
        
        CompanyHiringStatus found = repository.findById(3L).orElse(null);
        assertNotNull(found);
        assertEquals(found.getHiringStatus(), HiringStatus.HIRING);


        found = repository.findById(4L).orElse(null);
        assertNotNull(found);
        assertEquals(found.getHiringStatus(), HiringStatus.NOT_HIRING);

        found = repository.findById(5L).orElse(null);
        assertNotNull(found);
        assertEquals(found.getHiringStatus(), HiringStatus.UNKNOWN);

    }


    @Test
    void testFindByHiringStatus() {
        
        //https://stackoverflow.com/questions/56499565/how-to-use-findall-on-crudrepository-returning-a-list-instead-of-iterable
        Iterable<CompanyHiringStatus> iterable = repository.findAll();
        List<CompanyHiringStatus> hiring = StreamSupport.stream(iterable.spliterator(), false)
                                                    .filter(status -> status.getHiringStatus() == HiringStatus.HIRING)
                                                    .collect(Collectors.toList());

        
        assertEquals(1, hiring.size());

        List<CompanyHiringStatus> notHiring = StreamSupport.stream(iterable.spliterator(), false)
        .filter(status -> status.getHiringStatus() == HiringStatus.NOT_HIRING)
        .collect(Collectors.toList());


        assertEquals(1, notHiring.size());

        List<CompanyHiringStatus> unknown = StreamSupport.stream(iterable.spliterator(), false)
        .filter(status -> status.getHiringStatus() == HiringStatus.UNKNOWN)
        .collect(Collectors.toList());


        assertEquals(1, unknown.size());

            

        
    }

    @Test
    public void testAdd() {
        CompanyHiringStatus newStatus = new CompanyHiringStatus(6L, HiringStatus.HIRING);

        repository.save(newStatus);

        CompanyHiringStatus found = repository.findById(6L).orElse(null);

        assertNotNull(found);
        assertEquals(newStatus.getCompanyId(), found.getCompanyId());
        assertEquals(newStatus.getHiringStatus(), found.getHiringStatus());

    }
    
}

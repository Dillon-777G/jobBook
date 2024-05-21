package edu.site.jobBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.site.jobBook")
public class JobBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobBookApplication.class, args);
    }
}

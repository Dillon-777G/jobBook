package edu.site.jobBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "edu.site.jobBook")
@EnableScheduling
public class JobBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobBookApplication.class, args);
    }
}

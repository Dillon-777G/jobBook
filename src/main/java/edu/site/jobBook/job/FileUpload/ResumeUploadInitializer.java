package edu.site.jobBook.job.FileUpload;
// package edu.site.jobBook.job.ResumeUpload;

// import java.time.LocalDateTime;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.Ordered;
// import org.springframework.stereotype.Component;
// import org.springframework.core.annotation.Order;


// @Component
// @Order(Ordered.LOWEST_PRECEDENCE - 1) 
// public class ResumeUploadInitializer implements CommandLineRunner {

//     @Autowired
//     private FileMetadataRepository fileMetadataRepository;

//     @Override
//     public void run(String... args) throws Exception {
//         // Initialize data in Redis here
//         // Example:
//         fileMetadataRepository.save(new FileMetadata("1", "resume1.pdf", "application/pdf", LocalDateTime.now()));
//         fileMetadataRepository.save(new FileMetadata("2", "resume2.pdf", "application/pdf", LocalDateTime.now()));

//     }
// }
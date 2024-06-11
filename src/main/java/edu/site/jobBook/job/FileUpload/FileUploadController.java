package edu.site.jobBook.job.FileUpload;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.site.jobBook.user.UserActivityService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;

import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserActivity;

@Controller
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserActivityService userActivityService;

    // Upload file to the server
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("jobApplicationId") Long jobApplicationId,
                                   Model model) {
        fileUploadService.store(file, jobApplicationId);
        model.addAttribute("message", "File uploaded successfully!");
        // Log user activity for file upload
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        userActivityService.saveUserActivity(UserActivity.builder()
                .userId(user.getId())
                .activity("Uploaded file for job application ID: " + jobApplicationId)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());

        return "jobApplicationDetail"; // Redirect to a view to show job application details
    }

    // Download file from the server
    @GetMapping("/download/{jobApplicationId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long jobApplicationId) {
        try {
            String fileName = fileUploadService.getFileName(jobApplicationId.toString());
            Path filePath = fileUploadService.load(fileName);
            Resource resource = fileUploadService.loadFileAsResource(filePath);

            // Log user activity for file download
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser user = (AppUser) authentication.getPrincipal();
            userActivityService.saveUserActivity(UserActivity.builder()
                    .userId(user.getId())
                    .activity("Downloaded file for job application ID: " + jobApplicationId)
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }
}

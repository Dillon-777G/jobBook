package edu.site.jobBook.job.FileUpload;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;

@Controller
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("jobApplicationId") Long jobApplicationId,
                                   Model model) {
        fileUploadService.store(file, jobApplicationId);
        model.addAttribute("message", "File uploaded successfully!");
        return "jobApplicationDetail"; // Redirect to a view to show job application details
    }

    @GetMapping("/download/{jobApplicationId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long jobApplicationId) {
        try {
            String fileName = fileUploadService.getFileName(jobApplicationId.toString());
            Path filePath = fileUploadService.load(fileName);
            Resource resource = fileUploadService.loadFileAsResource(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }
}

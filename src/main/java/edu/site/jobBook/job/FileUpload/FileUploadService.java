package edu.site.jobBook.job.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

@Service
public class FileUploadService {
    private final Path rootLocation;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private JobApplicationFileRepository jobApplicationFileRepository;

    @Autowired
    public FileUploadService(FileMetadataRepository fileMetadataRepository, JobApplicationFileRepository jobApplicationFileRepository) {
        this.rootLocation = Paths.get("src/main/resources/static/upload"); // Set your fixed upload directory here
        this.fileMetadataRepository = fileMetadataRepository;
        this.jobApplicationFileRepository = jobApplicationFileRepository;
        init();
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public void store(MultipartFile file, Long jobApplicationId) {
        try {
            String fileMetaDataId = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = jobApplicationId+ "_" +fileMetaDataId +fileExtension;

            Path destinationFilePath = this.rootLocation.resolve(jobApplicationId+ "_" +fileMetaDataId +fileExtension);

            String filePath = destinationFilePath.toString();
            String contentType = file.getContentType();
            LocalDateTime uploadTimestamp = LocalDateTime.now();
        
            // Store file on the server
            Files.copy(file.getInputStream(), destinationFilePath);

            // Store metadata in the database
            JobApplicationFile jobApplicationFile = new JobApplicationFile(jobApplicationId.toString(),fileMetaDataId);
            jobApplicationFileRepository.save(jobApplicationFile);

            // Store metadata in Redis
            FileMetadata fileMetadata = new FileMetadata(fileMetaDataId, fileName,originalFileName, fileExtension, filePath, contentType, uploadTimestamp);
            fileMetadataRepository.save(fileMetadata);

            
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }

    // public FileMetadata getFileDetails(String jobApplicationID){
    //     String fileMetaDataId = jobApplicationFileRepository.findById(jobApplicationID).orElseThrow(() -> new RuntimeException("File not found for the given job application ID")).getFileMetaDataId();
    //     return fileMetadataRepository.findById(fileMetaDataId).orElseThrow(() -> new RuntimeException("File not found for the given job application ID"));
    // }

    public String getFileName(String jobApplicationID){
        String fileMetaDataId = jobApplicationFileRepository.findById(jobApplicationID).orElseThrow(() -> new RuntimeException("File not found for the given job application ID")).getFileMetaDataId();
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileMetaDataId).orElseThrow(() -> new RuntimeException("File not found for the given job application ID"));
        return fileMetadata.getFileName();
    }

    // public String getFilePathFromJobApplicationId(Long jobApplicationId) {
    //     JobApplicationFile jobApplicationFile = jobApplicationFileRepository.findById(jobApplicationId.toString())
    //         .orElseThrow(() -> new RuntimeException("File not found for the given job application ID"));
    //     return jobApplicationFile.getFilePath();
    // }

     public Resource loadFileAsResource(Path filePath) {
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file as resource", e);
        }
    }
}
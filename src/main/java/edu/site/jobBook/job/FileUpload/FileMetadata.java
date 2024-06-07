package edu.site.jobBook.job.FileUpload;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("FileMetadata")
public class FileMetadata {
    @Id
    private String fileId;
    private String fileName; 
    private String originalFileName;
    private String fileExtension;
    private String filePath;
    private String contentType;
    private LocalDateTime uploadTimestamp;
}

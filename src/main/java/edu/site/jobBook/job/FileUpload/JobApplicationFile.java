package edu.site.jobBook.job.FileUpload;

import org.springframework.data.redis.core.RedisHash;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("JobApplicationFile")
public class JobApplicationFile {
    @Id
    private String jobApplicationId;
    private String fileMetaDataId;
}

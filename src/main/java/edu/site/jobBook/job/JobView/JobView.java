package edu.site.jobBook.job.JobView;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("JobView")
public class JobView {
    @Id
    private String jobId;
    private String jobTitle;
    private long viewCount;
}

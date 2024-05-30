package edu.site.jobBook.company.HiringStatus;
 
import org.springframework.data.redis.core.RedisHash;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("CompanyHiringStatus")
public class CompanyHiringStatus {
    @Id
    private long companyId;
    private HiringStatus hiringStatus;
}
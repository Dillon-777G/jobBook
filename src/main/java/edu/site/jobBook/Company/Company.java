package edu.site.jobBook.Company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    private long id;
    private String Name;
    private String description;
    // private List<Post> posts;
    //private List<Job> jobs;
}

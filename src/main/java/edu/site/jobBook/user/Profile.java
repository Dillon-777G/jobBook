package edu.site.jobBook.user;

import lombok.Data;
import jakarta.persistence.Embeddable;

@Data
@Embeddable
public class Profile {
    private String firstName;
    private String lastName;
    private String email; 
    private String username;
 
}
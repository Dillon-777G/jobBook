package edu.site.jobBook.user;

import lombok.Data;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private String firstName;
    private String lastName;
    private String email; 
    private String username;
 
}

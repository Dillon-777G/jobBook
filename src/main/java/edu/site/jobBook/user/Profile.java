package edu.site.jobBook.user;

import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    private String firstName;
    private String lastName;
    private String email; 
    private String username;
 
}

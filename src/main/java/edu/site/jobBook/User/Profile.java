package edu.site.jobBook.User;

import lombok.Data;

@Data
public class Profile {
    private String firstName;
    private String lastName;
    private String email; // Assuming we want to include email in the profile
}

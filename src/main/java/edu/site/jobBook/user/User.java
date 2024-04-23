package edu.site.jobBook.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Utilizes Lombok to simplify Java code for entity modeling
 * Features demonstrated:
 * @Data - Automatically generates getters, setters, toString, equals, and hashCode methods
 * @NoArgsConstructor - Generates a no-argument constructor
 * @AllArgsConstructor - Generates a constructor initializing all fields
 * @Builder - Implements the Builder pattern for object creation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Profile profile;
    private String userType; // e.g., Candidate, Employer

    /**
     * Validates the user's password.
     * @param password The password to validate.
     * @return true if the password is correct, false otherwise.
     */
    public boolean validatePassword(String password) {
        return this.profile.checkPassword(password);
    }
}

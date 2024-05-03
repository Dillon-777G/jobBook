package edu.site.jobBook.user;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
 * @Entity - This annotation is required to specify the class as a JPA entity
 * @Table - This maps to the specific column needed in the database
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity 
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Profile profile;
    private String userType; 

}
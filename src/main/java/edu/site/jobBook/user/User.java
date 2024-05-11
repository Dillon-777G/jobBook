package edu.site.jobBook.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import edu.site.jobBook.user.password.Password;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonManagedReference
    private Password password;
    
    @Embedded
    private Profile profile;
    private String userType; 

    public void createPassword(String rawPassword) {
        if (this.password == null) {
            this.password = new Password();
            this.password.setUser(this);
        }
        this.password.setPassword(rawPassword);
    }
}
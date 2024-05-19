package edu.site.jobBook.user.dto;

import edu.site.jobBook.user.User;
import edu.site.jobBook.user.Profile;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String userType;

    public User toUser() {
        Profile profile = Profile.builder()
                                 .firstName(this.firstName)
                                 .lastName(this.lastName)
                                 .email(this.email)
                                 .username(this.username)
                                 .build();
        User user = User.builder()
                        .profile(profile)
                        .userType(this.userType)
                        .build();
        user.createPassword(this.password); // Assuming createPassword takes care of setting and hashing the password
        return user;
    }
}
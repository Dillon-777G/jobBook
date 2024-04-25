package edu.site.jobBook.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find users by their first name (from the embedded Profile)
    List<User> findByProfileFirstName(String firstName);

    // Find users by their last name (from the embedded Profile)
    List<User> findByProfileLastName(String lastName);

    // Find users by their last name containing a substring (from the embedded Profile)
    List<User> findByProfileLastNameLike(String partialLastName);

    // Find users by their email (from the embedded Profile)
    List<User> findByProfileEmail(String email);

    // Find users by their user type (e.g., Candidate, Employer)
    List<User> findByUserType(String userType);
}

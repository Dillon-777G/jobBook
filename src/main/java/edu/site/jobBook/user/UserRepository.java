package edu.site.jobBook.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByProfileFirstName(String firstName);

    List<User> findByProfileLastName(String lastName);

    List<User> findByProfileLastNameLike(String partialLastName);

    List<User> findByProfileEmail(String email);

    List<User> findByUserType(String userType);

    Optional<User> findByProfileUsername(String username);
}
package edu.site.jobBook.user.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    Optional<Password> findByUser_Profile_Username(String username);
}
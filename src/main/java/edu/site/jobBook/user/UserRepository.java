package edu.site.jobBook.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    
    AppUser findByUsername(String username);

    @SuppressWarnings("null")
    List<AppUser> findAll();
}

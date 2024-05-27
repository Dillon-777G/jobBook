package edu.site.jobBook.post;

import edu.site.jobBook.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(AppUser user);

    //added for functionality of interaction with company
    List<Post> findByCompanyId(Long companyId);
}
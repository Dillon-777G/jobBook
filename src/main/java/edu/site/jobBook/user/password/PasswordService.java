package edu.site.jobBook.user.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.site.jobBook.user.User;

@Service
public class PasswordService {

    private final PasswordRepository passwordRepository;

    @Autowired
    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public Password createPassword(User user, String rawPassword) {
        Password password = new Password();
        password.setUser(user);
        password.setPassword(rawPassword);
        return passwordRepository.save(password);
    }
}

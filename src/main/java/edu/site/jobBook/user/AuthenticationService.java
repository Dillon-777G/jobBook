package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.site.jobBook.user.password.PasswordRepository;
import edu.site.jobBook.user.password.Password;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordRepository passwordRepository;

    public boolean authenticate(String username, String password) {
        return passwordRepository.findByUser_Profile_Username(username)
                .map(storedPassword -> verifyPassword(password, storedPassword.getHash()))
                .orElse(false);
    }

    private boolean verifyPassword(String rawPassword, String hashedPassword) {
        return Password.hashPassword(rawPassword).equals(hashedPassword);
    }
}

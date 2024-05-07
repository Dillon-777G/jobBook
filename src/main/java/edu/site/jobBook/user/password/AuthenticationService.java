package edu.site.jobBook.user.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

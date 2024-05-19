package edu.site.jobBook.user.password;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import edu.site.jobBook.user.*;

@Service
@Transactional
public class UserPasswordService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    @Autowired
    public UserPasswordService(UserRepository userRepository, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    public User createUserWithPassword(String username, String rawPassword, Profile profile, String userType) {
        User user = new User();
        user.setProfile(profile);
        user.setUserType(userType);
        user.createPassword(rawPassword); 
        
        User savedUser = userRepository.save(user);
        passwordRepository.save(savedUser.getPassword()); 
        
        return savedUser;
    }

    public boolean checkUserCredentials(String username, String rawPassword) {
        return userRepository.findByProfileUsername(username)
                .map(user -> user.getPassword().getHash())
                .map(storedHash -> Password.hashPassword(rawPassword).equals(storedHash))
                .orElse(false);
    }
}
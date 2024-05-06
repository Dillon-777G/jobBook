package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.site.jobBook.user.password.Password;
import edu.site.jobBook.user.password.PasswordRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    @Transactional
    public User createUserWithPassword(User user, String rawPassword) {
        User savedUser = userRepository.save(user);
        Password password = new Password();
        password.setUser(savedUser);
        password.setPassword(rawPassword);
        passwordRepository.save(password);
        savedUser.setPassword(password);    
        return savedUser;
    }


    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public List<User> findByFirstName(String firstName) {
        return userRepository.findByProfileFirstName(firstName);
    }

    public List<User> findByLastName(String lastName) {
        return userRepository.findByProfileLastName(lastName);
    }

    public List<User> findByPartialLastName(String partialLastName) {
        return userRepository.findByProfileLastNameLike(partialLastName);
    }

    public List<User> findByEmail(String email) {
        return userRepository.findByProfileEmail(email);
    }

    public List<User> findByUserType(String userType) {
        return userRepository.findByUserType(userType);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}

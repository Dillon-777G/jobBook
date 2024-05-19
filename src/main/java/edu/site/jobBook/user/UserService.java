package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import edu.site.jobBook.user.password.PasswordRepository;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
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

    public Optional<User> findByProfileUsername(String username){
        return userRepository.findByProfileUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
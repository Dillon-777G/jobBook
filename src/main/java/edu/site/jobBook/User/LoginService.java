package edu.site.jobBook.User;

public class LoginService {

    /**
     * Attempts to login a user with the provided username and password.
     * @param username The username of the user trying to log in.
     * @param password The password of the user.
     * @return The User object if login is successful, null otherwise.
     */

    public User loginUser(String username, String password){
        User user = fetchUser(username); 
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        return null;
    }


    /**
     * Fetches a user from the data store based on the username. Currently, this method
     * returns a hardcoded user profile for demonstration purposes. Here the use of setters
     * through lombok is also demonstrated.
     *
     * @param username The username of the user to fetch.
     * @return A User object corresponding to the username provided, or null if not found.
     */

    private User fetchUser(String username) {
        Profile profile = new Profile();
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setEmail("john.doe@example.com");
        profile.setUsername(username);
        profile.setPassword("securepassword123"); 

        return new User(profile, "Candidate");
    }
}